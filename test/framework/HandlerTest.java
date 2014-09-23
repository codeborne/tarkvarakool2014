package framework;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HandlerTest {

  @Test
  public void className() throws Exception {
    Handler handler = new Handler();
    assertThat(handler.getClassName("/foo"), is("controllers.Foo"));
    assertThat(handler.getClassName("/foo-bar-baz"), is("controllers.FooBarBaz"));
  }

  @Test
  public void classNameWithPackage() throws Exception {
    Handler handler = new Handler();
    assertThat(handler.getClassName("/foo/bar"), is("controllers.foo.Bar"));
    assertThat(handler.getClassName("/foo/bar/baz-baz"), is("controllers.foo.bar.BazBaz"));
  }
}