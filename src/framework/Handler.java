package framework;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static freemarker.template.TemplateExceptionHandler.HTML_DEBUG_HANDLER;
import static freemarker.template.TemplateExceptionHandler.RETHROW_HANDLER;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class Handler extends AbstractHandler {
  static final String THE_ENCODING = "UTF-8";
  private final static Logger LOG = LogManager.getLogger();

  private Configuration freemarker = new Configuration();
  private Binder binder = new Binder("dd.MM.yyyy");
  private SessionFactory hibernateSessionFactory;

  private boolean devMode = isRunningInDebugMode();

  public Handler() throws IOException {
    initializeFreemarker();
    initializeHibernate();
  }

  @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    long t = -System.currentTimeMillis();
    try {
      request.setCharacterEncoding(THE_ENCODING);
      Object controller = createController(target);
      bindFrameworkFields(controller, request, response);
      binder.bindRequestParameters(controller, request.getParameterMap());
      bindHibernate(controller);
      invokeController(controller, baseRequest);

      Template template = freemarker.getTemplate(getTemplateName(target));
      response.setContentType("text/html");
      response.setCharacterEncoding(THE_ENCODING);
      template.process(controller, new OutputStreamWriter(response.getOutputStream(), "utf-8"));
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
      t += System.currentTimeMillis();
      LOG.info(request.getMethod() + " " + target + " " + t + " ms");
    }
  }

  private void bindHibernate(Object controller) {
    if (!(controller instanceof Controller)) return;
    ((Controller) controller).hibernate = hibernateSessionFactory.openSession();
  }

  void invokeController(Object controller, Request baseRequest) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method method = controller.getClass().getMethod(baseRequest.getMethod().toLowerCase());
    baseRequest.setHandled(true);
    method.invoke(controller);
  }

  Object createController(String target) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
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

  void bindFrameworkFields(Object controller, HttpServletRequest request, HttpServletResponse response) {
    if (controller instanceof Controller) {
      Controller con = (Controller) controller;
      con.request = request;
      con.response = response;
    }
  }

  String getTemplateName(String path) {
    return path.substring(1) + ".ftl";
  }

  String getClassName(String path) {
    path = path.substring(1);
    int i = path.lastIndexOf('/');
    String packagePrefix = (i == -1) ? "" : path.substring(0, i).replace('/', '.') + ".";
    path = path.substring(i + 1);
    return "controllers." + packagePrefix + asList(path.split("-")).stream().map(StringUtils::capitalize).collect(joining());
  }

  private boolean isRunningInDebugMode() {
    return ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("jdwp");
  }

  private void initializeFreemarker() throws IOException {
    DefaultObjectWrapper wrapper = (DefaultObjectWrapper) freemarker.getObjectWrapper();
    wrapper.setExposeFields(true);
    freemarker.setDefaultEncoding(THE_ENCODING);
    freemarker.setTemplateExceptionHandler(devMode ? HTML_DEBUG_HANDLER : RETHROW_HANDLER);
    freemarker.setIncompatibleImprovements(new Version(2, 3, 20));
    freemarker.setTemplateLoader(new FileTemplateLoader(new File("views")) {
      @Override public Reader getReader(Object templateSource, String encoding) throws IOException {
        Reader reader = super.getReader(templateSource, encoding);
        String template = IOUtils.toString(reader);
        reader.close();
        return new StringReader("<#escape value as value?html>" + template + "</#escape>");
      }
    });
    freemarker.addAutoInclude("decorator.ftl");
  }

  private void initializeHibernate() {
    //noinspection deprecation
    hibernateSessionFactory = new org.hibernate.cfg.Configuration().buildSessionFactory();
  }
}

