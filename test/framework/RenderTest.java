package framework;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RenderTest {

  @Test
  public void templateName() throws Exception {
    Render render = new Render(null);
    assertThat(render.getTemplateName("/foo"), is("foo.ftl"));
    assertThat(render.getTemplateName("/foo-bar-baz"), is("foo-bar-baz.ftl"));
    assertThat(render.getTemplateName("/foo/bar/baz"), is("foo/bar/baz.ftl"));
  }
}