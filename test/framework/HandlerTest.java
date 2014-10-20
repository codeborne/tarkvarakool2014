package framework;

import controllers.MockController;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Request;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static framework.Handler.ROUTES_FILE;
import static framework.Handler.Route;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class HandlerTest {
  private Handler handler;
  Request baseRequest = mock(Request.class);
  private HttpServletRequest request = mock(HttpServletRequest.class);
  private HttpServletResponse response = mock(HttpServletResponse.class);
  private HttpSession session = mock(HttpSession.class);

  @Before
  public void setUp() throws Exception {
    when(request.getSession()).thenReturn(session);
    handler = spy(new Handler());
  }

  @Test
  public void className() throws Exception {
    assertThat(handler.getClassName("/foo"), is("controllers.Foo"));
    assertThat(handler.getClassName("/foo-bar-baz"), is("controllers.FooBarBaz"));
  }

  @Test
  public void classNameWithPackage() throws Exception {
    assertThat(handler.getClassName("/foo/bar"), is("controllers.foo.Bar"));
    assertThat(handler.getClassName("/foo/bar/baz-baz"), is("controllers.foo.bar.BazBaz"));
  }

  @Test
  public void classNameWithEmptyValue() {
    assertThat(handler.getClassName(""), is("controllers."));
  }

  @Test
  public void handleWithUnknownUrl() throws Exception {
    handler.handle("/foo/bar", baseRequest, request, response);
    assertThat(baseRequest.isHandled(), is(false));
  }

  @Test
  public void bindRequestToController() throws Exception {
    RequestState state = new RequestState() {};
    handler.bindRequestState(state, request, response);
    assertThat(state.request, sameInstance(request));
  }

  @Test
  public void redirectIfPossibleTriesHomeFromRoot() throws Exception {
    doReturn("java.lang.Object").when(handler).getClassName("/home");

    handler.redirectIfPossible("/", baseRequest, response);

    verify(response).sendRedirect("/home");
  }

  @Test
  public void redirectIfPossibleTriesHomeOnTrailingSlash() throws Exception {
    doReturn("java.lang.Object").when(handler).getClassName("/bar/home");

    handler.redirectIfPossible("/bar/", baseRequest, response);
    verify(response).sendRedirect("/bar/home");
  }

  @Test
  public void redirectIfPossibleTriesWithoutTrailingSlash() throws Exception {
    doReturn("java.lang.Object").when(handler).getClassName("/foo");

    handler.redirectIfPossible("/foo/", baseRequest, response);

    verify(response).sendRedirect("/foo");
  }

  @Test
  public void redirectIfPossibleTriesWithTrailingSlashAndHome() throws Exception {
    doReturn("java.lang.Object").when(handler).getClassName("/foo/home");

    handler.redirectIfPossible("/foo", baseRequest, response);

    verify(response).sendRedirect("/foo/home");
  }

  @Test
  public void redirectIfPossible() throws Exception {

    handler.redirectIfPossible("/foo", baseRequest, response);

    verifyNoMoreInteractions(response);
  }

  @Test
  public void invokeController() throws Exception {
    Controller controller = mock(Controller.class);
    when(baseRequest.getMethod()).thenReturn("GET");
    handler.invokeController(controller, baseRequest);
    verify(controller).get();
  }

  @Test
  public void createController() throws Exception {
    Object controller = handler.createController("/mock-controller");
    assertThat(controller, instanceOf(MockController.class));
  }

  @Test(expected = ClassNotFoundException.class)
  public void createControllerDoesNotAllowUppercaseInURL() throws Exception {
    handler.createController("/MockController");
  }

  @Test
  public void initializeLoadsRoutes() throws NoSuchMethodException, IOException, ClassNotFoundException {
    HashMap<String, Map<String, Route>> routes = new HashMap<>();
    routes.put("/foo", new HashMap<String, Route>() {{ put("POST", mock(Route.class)); }});
    doReturn(routes).when(handler).getRoutes(anyListOf(String.class));

    handler.initialize();
    assertThat(handler.routes, is(routes));
    verify(handler).getRoutes(FileUtils.readLines(ROUTES_FILE));
  }

  @Test
  public void invokeControllerActionWithoutExistingRoute() throws Exception {
    Result result = mock(Result.class);
    doReturn(result).when(handler).invokeController(any(Controller.class), any(Method.class), any(Request.class));
    doReturn(result).when(handler).invokeController(any(Controller.class), any(Request.class));

    Request request = mockRequest("GET", "/mock-controller", new HashMap<>());
    assertThat(handler.invokeControllerAction(request), is(result));
    verify(handler).invokeController(any(MockController.class), eq(request));
    verify(handler, never()).invokeController(any(Controller.class), any(Method.class), any(Request.class));
  }

  @Test
  public void invokeControllerActionWithoutExistingHttpMethodRoute() throws Exception {
    Result result = mock(Result.class);
    doReturn(result).when(handler).invokeController(any(Controller.class), any(Method.class), any(Request.class));
    doReturn(result).when(handler).invokeController(any(Controller.class), any(Request.class));

    handler.routes = mockRoute("POST");

    Request request = mockRequest("GET", "/mock-controller", new HashMap<>());
    assertThat(handler.invokeControllerAction(request), is(result));
    verify(handler).invokeController(any(MockController.class), eq(request));
    verify(handler, never()).invokeController(any(Controller.class), any(Method.class), any(Request.class));
  }

  @Test
  public void invokeControllerActionWithExistingRoute() throws Exception {
    Result result = mock(Result.class);
    doReturn(result).when(handler).invokeController(any(Controller.class), any(Method.class), any(Request.class));
    doReturn(result).when(handler).invokeController(any(Controller.class), any(Request.class));

    handler.routes = mockRoute("POST");

    Request request = mockRequest("POST", "/mock-controller", new HashMap<>());
    assertThat(handler.invokeControllerAction(request), is(result));
    verify(handler, never()).invokeController(any(MockController.class), any(Request.class));
    verify(handler).invokeController(any(MockController.class), eq(MockController.class.getMethod("action")), eq(request));
  }

  @Test
  public void getRoutesSingleRoute() throws NoSuchMethodException, IOException, ClassNotFoundException {
    Map<String, Map<String, Route>> expectedRoutes = new HashMap<String, Map<String, Route>>() {{
      put("/foo/bar", new HashMap<String, Route>() {{
        put("GET", new Route("controllers.MockController.action"));
      }});
    }};

    assertThat(handler.getRoutes(asList("GET /foo/bar controllers.MockController.action")), is(expectedRoutes));
  }

  @Test
  public void getRoutesSingleRouteWithDifferentHttpMethods() throws NoSuchMethodException, IOException, ClassNotFoundException {
    Map<String, Map<String, Route>> expectedRoutes = new HashMap<String, Map<String, Route>>() {{
      put("/foo/bar", new HashMap<String, Route>() {{
        put("GET", new Route("controllers.MockController.action"));
        put("POST", new Route("controllers.MockController.action"));
      }});
    }};

    List<String> routeDefinitions = asList(
      "GET /foo/bar controllers.MockController.action",
      "POST /foo/bar controllers.MockController.action"
    );
    assertThat(handler.getRoutes(routeDefinitions), is(expectedRoutes));
  }

  @Test
  public void getRoutesSingleRouteWithSameHttpMethods() throws NoSuchMethodException, IOException, ClassNotFoundException {
    Map<String, Map<String, Route>> expectedRoutes = new HashMap<String, Map<String, Route>>() {{
      put("/foo/bar", new HashMap<String, Route>() {{
        put("GET", new Route("controllers.MockController.secondAction"));
      }});
    }};

    List<String> routeDefinitions = asList(
      "GET /foo/bar controllers.MockController.action",
      "GET /foo/bar controllers.MockController.secondAction"
    );
    assertThat(handler.getRoutes(routeDefinitions), is(expectedRoutes));
  }

  @Test
  public void getRoutesMultipleRoutes() throws NoSuchMethodException, IOException, ClassNotFoundException {
    Map<String, Map<String, Route>> expectedRoutes = new HashMap<String, Map<String, Route>>() {{
      put("/foo/bar", new HashMap<String, Route>() {{
        put("GET", new Route("controllers.MockController.action"));
      }});

      put("/bar/baz", new HashMap<String, Route>() {{
        put("POST", new Route("controllers.MockController.secondAction"));
      }});
    }};

    List<String> routeDefinitions = asList(
      "GET /foo/bar controllers.MockController.action",
      "POST /bar/baz controllers.MockController.secondAction"
    );
    assertThat(handler.getRoutes(routeDefinitions), is(expectedRoutes));
  }

  private HashMap<String, Map<String, Route>> mockRoute(final String httpMethod) throws NoSuchMethodException, ClassNotFoundException {
    return new HashMap<String, Map<String, Route>>() {{
      put("/mock-controller", new HashMap<String, Route>() {{
        put(httpMethod, new Route("controllers.MockController.action"));
      }});
    }};
  }

  private Request mockRequest(String httpMethod, String path, HashMap<String, String[]> parameters) {
    Request request = mock(Request.class);
    doReturn(path).when(request).getPathInfo();
    doReturn(httpMethod).when(request).getMethod();
    doReturn(parameters).when(request).getParameterMap();
    return request;
  }
}