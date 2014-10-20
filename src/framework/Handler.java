package framework;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static framework.FreemarkerHelper.initializeFreemarker;
import static framework.HibernateHelper.buildSessionFactory;
import static framework.HibernateHelper.initDatabase;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.apache.commons.lang3.text.WordUtils.capitalize;

public class Handler extends AbstractHandler {
  private final static Logger LOG = LogManager.getLogger();

  public static final String THE_ENCODING = "UTF-8";

  static final File ROUTES_FILE = new File("conf/routes");

  private boolean devMode = isRunningInDebugMode();

  private SessionFactory hibernateSessionFactory;

  private Messages messages = new Messages();

  private Binder binder = new Binder("dd.MM.yyyy");

  Map<String, Map<String, Route>> routes = new HashMap<>();

  public void initialize() throws IOException, NoSuchMethodException, ClassNotFoundException {
    Render.freemarker = initializeFreemarker(devMode);
    hibernateSessionFactory = buildSessionFactory();
    initDatabase(hibernateSessionFactory);
    this.routes.putAll(getRoutes(FileUtils.readLines(ROUTES_FILE)));
  }

  @Override
  public void handle(String path, Request request, HttpServletRequest ignoredParameter, HttpServletResponse response) throws IOException, ServletException {
    long t = -System.currentTimeMillis();
    RequestState requestState = RequestState.threadLocal.get();
    Session hibernate = null;
    try {
      request.setCharacterEncoding(THE_ENCODING);
      bindRequestState(requestState, request, response);
      hibernate = openHibernateSession(requestState);
      invokeControllerAction(request).handle(request, response);
    } catch (ClassNotFoundException | NoSuchMethodException ignored) {
      redirectIfPossible(path, request, response);
    } catch (Exception e) {
      LOG.warn("Request handling failed", e);
      response.sendError(SC_INTERNAL_SERVER_ERROR, devMode ? e.toString() : null);
      request.setHandled(true);
    } finally {
      closeHibernateSession(hibernate);
      t += System.currentTimeMillis();
      LOG.info(request.getMethod() + " " + path + " " + t + " ms");
    }
  }

  Result invokeControllerAction(Request request) throws Exception {
    Map<String, Route> routesForPath = routes.get(request.getPathInfo());
    String httpMethod = request.getMethod().toUpperCase();

    if (routesForPath != null && routesForPath.get(httpMethod) != null) {
      Route route = routesForPath.get(httpMethod);
      Controller controller = route.getController();
      binder.bindRequestParameters(controller, request.getParameterMap());
      return invokeController(controller, route.getControllerAction(), request);
    } else {
      // magic routes
      Controller controller = createController(request.getPathInfo());
      binder.bindRequestParameters(controller, request.getParameterMap());
      return invokeController(controller, request);
    }
  }

  Map<String, Map<String, Route>> getRoutes(List<String> routeDefinitions) throws IOException, NoSuchMethodException, ClassNotFoundException {
    Map<String, Map<String, Route>> routes = new HashMap<>();

    for (String routeDefinition : routeDefinitions) {
      String[] routeParts = routeDefinition.trim().split("\\s+");
      String method = routeParts[0];
      String path = routeParts[1];
      String controllerClassWithMethod = routeParts[2];

      Map<String, Route> routesForPath = routes.getOrDefault(path, new HashMap<>());
      routesForPath.put(method.toUpperCase(), new Route(controllerClassWithMethod));
      routes.put(path, routesForPath);
    }

    return routes;
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

  Result invokeController(Controller controller, Request request) throws Exception {
    return invokeController(controller, controller.getClass().getMethod(request.getMethod().toLowerCase()), request);
  }

  Result invokeController(Controller controller, Method method, Request request) throws Exception {
    try {
      request.setHandled(true);
      return (Result) method.invoke(controller);
    }
    catch (InvocationTargetException e) {
      throw (Exception) e.getCause(); // todo fix for throwable
    }
  }

  Controller createController(String target) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    if (!target.toLowerCase().equals(target)) throw new ClassNotFoundException("all URLs must be lowercase");
    String className = getClassName(target);
    Class controllerClass = Class.forName(className);
    return (Controller)controllerClass.newInstance();
  }

  void redirectIfPossible(String target, Request request, HttpServletResponse response) throws IOException {
    if (target.endsWith("/")) {
      if (!redirectIfExists(target + "home", request, response))
        redirectIfExists(target.substring(0, target.length() - 1), request, response);
    }
    else
      redirectIfExists(target + "/home", request, response);
  }

  private boolean redirectIfExists(String withHomeSuffix, Request request, HttpServletResponse response) throws IOException {
    try {
      Class.forName(getClassName(withHomeSuffix));
      response.sendRedirect(withHomeSuffix);
      request.setHandled(true);
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

  private boolean isRunningInDebugMode() {
    return ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("jdwp");
  }

  static class Route {
    private final Class<Controller> controllerClass;
    private final Method controllerMethod;

    Route(String controllerClassWithMethod) throws ClassNotFoundException, NoSuchMethodException {
      int methodSeparatorPosition = controllerClassWithMethod.lastIndexOf(".");
      //noinspection unchecked
      this.controllerClass = (Class<Controller>) Class.forName(controllerClassWithMethod.substring(0, methodSeparatorPosition));
      this.controllerMethod = this.controllerClass.getMethod(controllerClassWithMethod.substring(methodSeparatorPosition + 1, controllerClassWithMethod.length()));
    }

    public Controller getController() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
      return controllerClass.newInstance();
    }

    public Method getControllerAction() {
      return controllerMethod;
    }

    @Override
    public boolean equals(Object other) {
      if (this == other) return true;
      if (other == null || getClass() != other.getClass()) return false;

      Route route = (Route) other;

      if (!controllerClass.equals(route.controllerClass)) return false;
      if (!controllerMethod.equals(route.controllerMethod)) return false;

      return true;
    }

    @Override
    public int hashCode() {
      int result = controllerClass.hashCode();
      result = 31 * result + controllerMethod.hashCode();
      return result;
    }
  }
}

