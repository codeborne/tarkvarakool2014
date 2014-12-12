package framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static framework.FreemarkerHelper.initializeFreemarker;
import static framework.HibernateHelper.createSessionFactory;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.apache.commons.lang3.text.WordUtils.capitalize;

public class Handler extends AbstractHandler {
  private final static Logger LOG = LogManager.getLogger();

  public static final String THE_ENCODING = "UTF-8";
  static {
    System.setProperty("file.encoding", THE_ENCODING);
  }

  private SessionFactory hibernateSessionFactory;

  private Messages messages;

  private Binder binder = new Binder("dd.MM.yyyy");

  private boolean isDevMode;

  public Handler(boolean isDevMode) {
    super();
    this.isDevMode = isDevMode;
    messages = new Messages(isDevMode);
  }

  public void initialize() throws IOException {
    Render.freemarker = initializeFreemarker(isDevMode);
    hibernateSessionFactory = createSessionFactory(isDevMode);
  }

  @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    long t = -System.currentTimeMillis();
    RequestState requestState = RequestState.threadLocal.get();
    Session hibernate = null;
    Transaction transaction = null;
    try {
      request.setCharacterEncoding(THE_ENCODING);
      bindRequestState(requestState, request, response);
      hibernate = openHibernateSession(requestState);
      transaction = hibernate.beginTransaction();
      Controller controller = createController(target);
      binder.bindRequestParameters(controller, request.getParameterMap());
      Result result = invokeController(controller, baseRequest, response);
      if (transaction.isActive()) transaction.commit();
      result.handle(request, response);
    }
    catch (ClassNotFoundException | NoSuchMethodException ignored) {
      redirectIfPossible(target, baseRequest, response);
    }
    catch (NotAuthorizedException e) {
      LOG.error(e.getMessage());
      //response.sendError(SC_FORBIDDEN, devMode ? e.getMessage() : null);
      if(isAjax(baseRequest)) {
        response.sendError(SC_FORBIDDEN, "Not authorized");
      }
      else {
        response.sendRedirect("/");
      }
      setHandled(baseRequest, response);
    }
    catch (Exception e) {
      LOG.warn("Request handling failed", e);
      response.sendError(SC_INTERNAL_SERVER_ERROR, isDevMode ? e.toString() : null);
      setHandled(baseRequest, response);
    }
    finally {
      closeHibernateSession(hibernate, transaction);
      t += System.currentTimeMillis();
      if (baseRequest.isHandled()) LOG.info(request.getMethod() + " " + target + " " + t + " ms");
    }
  }

  private void setHandled(Request baseRequest, HttpServletResponse response) {
    response.addHeader("Cache-Control","max-age=1, must-revalidate, no-store");
    baseRequest.setHandled(true);
  }

  private boolean isAjax(Request request) {
    return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
  }

  private Session openHibernateSession(RequestState state) {
    return state.hibernate = hibernateSessionFactory.openSession();
  }

  private void closeHibernateSession(Session hibernate, Transaction transaction) {
    if (transaction != null && transaction.isActive()) transaction.rollback();
    if (hibernate != null) hibernate.close();
  }

  Result invokeController(Controller controller, Request baseRequest, HttpServletResponse response) throws Exception {
    try {
      String httpMethod = baseRequest.getMethod().toLowerCase();
      if ("post".equals(httpMethod) && !checkCSRFToken(baseRequest)) {
        throw new NotAuthorizedException("Most likely CSRF attempt");
      }
      Method method = controller.getClass().getMethod(httpMethod);
      Role roleAnnotation = method.getAnnotation(Role.class);
      if (roleAnnotation == null) throw new RoleMissingException(method);
      if (!controller.getRoles().contains(roleAnnotation.value())) throw new NotAuthorizedException(method + " requires role '" + roleAnnotation.value() + "'");
      setHandled(baseRequest, response);
      return (Result) method.invoke(controller);
    }
    catch (InvocationTargetException e) {
      throw (Exception) e.getCause(); // todo fix for throwable
    }
  }

  private boolean checkCSRFToken(Request baseRequest) {
    String csrfToken = baseRequest.getParameter("csrfToken");
    String sessionToken = (String) baseRequest.getSession().getAttribute("csrfToken");
    return sessionToken == null || sessionToken.equals(csrfToken);
  }

  Controller createController(String target) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    if (!target.toLowerCase().equals(target)) throw new ClassNotFoundException("all URLs must be lowercase");
    String className = getClassName(target);
    Class controllerClass = Class.forName(className);
    return (Controller) controllerClass.newInstance();
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
      setHandled(baseRequest, response);
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

  String getClassName(String path) {
    path = path.length() > 0 ? path.substring(1) : path;
    int i = path.lastIndexOf('/');
    String packagePrefix = (i == -1) ? "" : path.substring(0, i).replace('/', '.') + ".";
    path = path.substring(i + 1);
    return "controllers." + packagePrefix + capitalize(path, '-').replace("-", "");
  }
}

