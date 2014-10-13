package framework;

import controllers.MockController;
import org.eclipse.jetty.server.Request;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
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
  public void handleWithUnknownUrl() throws Exception {
    handler.handle("/foo/bar", baseRequest, request, response);
    assertThat(baseRequest.isHandled(), is(false));
  }

  @Test
  public void templateName() throws Exception {
    assertThat(handler.getTemplateName("/foo"), is("foo.ftl"));
    assertThat(handler.getTemplateName("/foo-bar-baz"), is("foo-bar-baz.ftl"));
    assertThat(handler.getTemplateName("/foo/bar/baz"), is("foo/bar/baz.ftl"));
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
  public void handleExceptionWithRedirect() throws Exception {
    handler.handleException(new InvocationTargetException(new Redirect("/another/url")), response);

    verify(response).sendRedirect("/another/url");
    verifyNoMoreInteractions(response);
  }

  @Test
  public void handleException() throws Exception {
    handler.handleException(new InvocationTargetException(new RuntimeException()), response);

    verify(response).sendError(eq(SC_INTERNAL_SERVER_ERROR), anyString());
    verifyNoMoreInteractions(response);
  }

  @Test
  public void invokeController() throws Exception {
    class Controller { public void get() {} }
    Controller controller = spy(new Controller());
    when(baseRequest.getMethod()).thenReturn("GET");
    handler.invokeController(controller, baseRequest);
    verify(controller).get();
  }

  @Test
  public void createController() throws Exception {
    Object controller = handler.createController("/mock-controller");
    assertThat(controller, instanceOf(MockController.class));
  }
}