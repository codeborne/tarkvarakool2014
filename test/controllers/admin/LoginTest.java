package controllers.admin;

import controllers.ControllerTest;
import controllers.admin.goals.Home;
import model.User;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class LoginTest extends ControllerTest<Login>{

  @Test
  public void successfulLoginCreatesAuthenticationToken() throws Exception {
    controller.password = "password";
    controller.username = "Delia";
    controller=spy(controller);
    doReturn(asList(new User("Delia","password"))).when(controller).loadUsersFromDatabase();
    doReturn("gdfgdfgrtert454sdfgd").when(controller).generateCSRFToken();
    assertRedirect(Home.class, controller.post());
    verify(controller.session).setAttribute("username", "Delia");
    verify(controller.session).setAttribute("csrfToken", "gdfgdfgrtert454sdfgd");
  }
}