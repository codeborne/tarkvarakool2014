package framework;

import org.eclipse.jetty.server.Request;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class HandlerTest {

  private Handler handler;
  private HttpServletResponse response = mock(HttpServletResponse.class);

  @Before
  public void setUp() throws Exception {
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
    Request baseRequest = new Request(null, null);
    handler.handle("/foo/bar", baseRequest, null, null);
    assertThat(baseRequest.isHandled(), is(false));
  }

  @Test
  public void templateName() throws Exception {
    assertThat(handler.getTemplateName("/foo"), is("foo.ftl"));
    assertThat(handler.getTemplateName("/foo-bar-baz"), is("foo-bar-baz.ftl"));
    assertThat(handler.getTemplateName("/foo/bar/baz"), is("foo/bar/baz.ftl"));
  }

  @Test
  public void bindRequestWithoutRequestField() throws Exception {
    HttpServletRequest request = mock(HttpServletRequest.class);
    handler.bindRequest(new Object(), request);
  }

  @Test
  public void bindRequestWithRequestField() throws Exception {
    HttpServletRequest request = mock(HttpServletRequest.class);
    class Foo {
      public HttpServletRequest request;
    }
    Foo foo = new Foo();
    handler.bindRequest(foo, request);
    assertThat(foo.request, sameInstance(request));
  }

  @Test
  public void bindRequestWithRequestFieldWithDifferentName() throws Exception {
    HttpServletRequest request = mock(HttpServletRequest.class);
    class Foo {
      public HttpServletRequest httpRequest;
    }
    Foo foo = new Foo();
    handler.bindRequest(foo, request);
    assertThat(foo.httpRequest, sameInstance(request));
  }

  @Test
  public void redirectIfPossibleTriesHomeFromRoot() throws Exception {
    doReturn("java.lang.Object").when(handler).getClassName("/home");

    handler.redirectIfPossible("/", response);

    verify(response).sendRedirect("/home");
  }

  @Test
  public void redirectIfPossibleTriesHomeOnTrailingSlash() throws Exception {
    doReturn("java.lang.Object").when(handler).getClassName("/bar/home");

    handler.redirectIfPossible("/bar/", response);
    verify(response).sendRedirect("/bar/home");
  }

  @Test
  public void redirectIfPossibleTriesWithoutTrailingSlash() throws Exception {
    doReturn("java.lang.Object").when(handler).getClassName("/foo");

    handler.redirectIfPossible("/foo/", response);

    verify(response).sendRedirect("/foo");
  }

  @Test
  public void redirectIfPossibleTriesWithTrailingSlashAndHome() throws Exception {
    doReturn("java.lang.Object").when(handler).getClassName("/foo/home");

    handler.redirectIfPossible("/foo", response);

    verify(response).sendRedirect("/foo/home");
  }

  @Test
  public void redirectIfPossible() throws Exception {

    handler.redirectIfPossible("/foo", response);

    verifyNoMoreInteractions(response);
  }
}