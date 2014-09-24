package framework;

import org.eclipse.jetty.server.Request;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class HandlerTest {

  private Handler handler;

  @Before
  public void setUp() throws Exception {
    handler = new Handler();
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
  public void classNameWithEmptyPath() throws Exception {
    assertThat(handler.getClassName("/"), is("controllers.Index"));
    assertThat(handler.getClassName("/foo/bar/"), is("controllers.foo.bar.Index"));
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
    assertThat(handler.getTemplateName("/"), is("index.ftl"));
    assertThat(handler.getTemplateName("/bar/"), is("bar/index.ftl"));
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
}