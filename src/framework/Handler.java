package framework;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static framework.FreemarkerHelper.initializeFreemarker;
import static framework.HibernateHelper.buildSessionFactory;
import static framework.HibernateHelper.initDatabase;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.apache.commons.lang3.text.WordUtils.capitalize;

public class Handler extends AbstractHandler {
  private final static Logger LOG = LogManager.getLogger();

  public static final String THE_ENCODING = "UTF-8";

  private boolean devMode = isRunningInDebugMode();

  private Configuration freemarker;
  private SessionFactory hibernateSessionFactory;

  private Messages messages = new Messages();

  private Binder binder = new Binder("dd.MM.yyyy");

  public Handler() throws IOException {
    freemarker = initializeFreemarker(devMode);
    hibernateSessionFactory = buildSessionFactory();
    initDatabase(hibernateSessionFactory);
  }

  @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    long t = -System.currentTimeMillis();
    RequestState requestState = RequestState.threadLocal.get();
    Session hibernate = null;
    try {
      request.setCharacterEncoding(THE_ENCODING);
      bindRequestState(requestState, request, response);
      hibernate = openHibernateSession(requestState);
      Object controller = createController(target);
      binder.bindRequestParameters(controller, request.getParameterMap());
      invokeController(controller, baseRequest);

      Template template = freemarker.getTemplate(getTemplateName(target));
      response.setContentType("text/html");
      response.setCharacterEncoding(THE_ENCODING);
      template.process(controller, new OutputStreamWriter(response.getOutputStream(), THE_ENCODING));
    }
    catch (ClassNotFoundException|NoSuchMethodException ignored) {
      redirectIfPossible(target, baseRequest, response);
    }
    catch (InstantiationException|IllegalAccessException e) {
      LOG.warn("Failed to create controller: " + e);
      response.sendError(SC_INTERNAL_SERVER_ERROR, devMode ? e.toString() : null);
      baseRequest.setHandled(true);
    }
    catch (InvocationTargetException e) {
      handleException(e, response);
    }
    catch (FileNotFoundException e) {
      LOG.warn(e.toString());
      response.sendError(SC_INTERNAL_SERVER_ERROR, devMode ? e.toString() : null);
    }
    catch (TemplateException e) {
      LOG.warn("Template failure: " + e);
      response.sendError(SC_INTERNAL_SERVER_ERROR, devMode ? e.toString() : null);
    }
    finally {
      closeHibernateSession(hibernate);
      t += System.currentTimeMillis();
      LOG.info(request.getMethod() + " " + target + " " + t + " ms");
    }
  }

  private Session openHibernateSession(RequestState state) {
    return state.hibernate = hibernateSessionFactory.openSession();
  }

  private void closeHibernateSession(Session hibernate) {
    if (hibernate != null) {
      hibernate.flush();
      hibernate.close();
    }
  }

  void invokeController(Object controller, Request baseRequest) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method method = controller.getClass().getMethod(baseRequest.getMethod().toLowerCase());
    baseRequest.setHandled(true);
    method.invoke(controller);
  }

  Object createController(String target) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    if (!target.toLowerCase().equals(target)) throw new ClassNotFoundException("all URLs must be lowercase");
    String className = getClassName(target);
    Class controllerClass = Class.forName(className);
    return controllerClass.newInstance();
  }

  void handleException(InvocationTargetException exception, HttpServletResponse response) throws IOException {
    Throwable cause = exception.getCause();
    if (cause instanceof Redirect) {
      response.sendRedirect(cause.getMessage());
    }
    else {
      LOG.warn("Controller failure: " + cause);
      response.sendError(SC_INTERNAL_SERVER_ERROR, devMode ? cause.toString() : null);
    }
  }

  void redirectIfPossible(String target, Request baseRequest, HttpServletResponse response) throws IOException {
    if (target.endsWith("/")) {
      if (!redirectIfExists(target + "home", baseRequest, response))
        redirectIfExists(target.substring(0, target.length() - 1), baseRequest, response);
    }
    else
      redirectIfExists(target + "/home", baseRequest, response);
  }

  private boolean redirectIfExists(String withHomeSuffix, Request baseRequest, HttpServletResponse response) throws IOException {
    try {
      Class.forName(getClassName(withHomeSuffix));
      response.sendRedirect(withHomeSuffix);
      baseRequest.setHandled(true);
      return true;
    }
    catch (ClassNotFoundException e) {
      return false;
    }
  }

  void bindRequestState(RequestState state, HttpServletRequest request, HttpServletResponse response) {
    state.request = request;
    state.response = response;
    state.session = request.getSession();
    state.messages = messages.getResolverFor(request);
  }

  String getTemplateName(String path) {
    return path.substring(1) + ".ftl";
  }

  String getClassName(String path) {
    path = path.substring(1);
    int i = path.lastIndexOf('/');
    String packagePrefix = (i == -1) ? "" : path.substring(0, i).replace('/', '.') + ".";
    path = path.substring(i + 1);
    return "controllers." + packagePrefix + capitalize(path, '-').replace("-", "");
  }

  private boolean isRunningInDebugMode() {
    return ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("jdwp");
  }
}

