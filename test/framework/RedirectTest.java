package framework;

import controllers.MockController;
import controllers.subpackage.AnotherMockController;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RedirectTest {

  @Test
  public void pathFromClassName() throws Exception {
    assertEquals("/mock-controller", new Redirect(MockController.class).getPath());
    assertEquals("/subpackage/another-mock-controller", new Redirect(AnotherMockController.class).getPath());
  }
}