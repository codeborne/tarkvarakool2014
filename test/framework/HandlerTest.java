package framework;

import org.eclipse.jetty.server.Request;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

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
}