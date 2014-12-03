package controllers.admin;

import controllers.ControllerTest;
import controllers.Home;
import org.junit.Test;

import static org.mockito.Mockito.verify;

public class LogoutTest extends ControllerTest<Logout> {
  @Test
  public void successfulLogoutInvalidatesSession() throws Exception {
    assertRedirect(Home.class, controller.get());
    verify(controller.session).removeAttribute("username");
    verify(controller.session).invalidate();
  }
}