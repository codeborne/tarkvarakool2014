package controllers.admin.user;

import controllers.ControllerTest;
import controllers.admin.goals.Home;
import model.User;
import org.hibernate.criterion.Criterion;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import static helpers.Password.validatePassword;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ChangepasswordTest extends ControllerTest<Changepassword>{

  @Before
  public void setUp() throws Exception {
    when(request.getPathInfo()).thenReturn("admin/");
    when(session.getAttribute("username")).thenReturn("Username");
    User user = new User("Username", "password");
    when(hibernate.createCriteria(User.class).add(any(Criterion.class)).uniqueResult()).thenReturn(user);

  }

  @Test
  public void postIfErrorsContainsOldPassword() throws Exception {
    controller.errors.put("oldPassword",new RuntimeException());

    assertRender(controller.post());

    assertEquals(2, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    assertTrue(controller.errorsList.contains(messages.get("errorAllFieldsMustBeFilledIn")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postIfErrorsContainsNewPasswordFirst() throws Exception {
    controller.errors.put("newPasswordFirst",new RuntimeException());

    assertRender(controller.post());

    assertEquals(2, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    assertTrue(controller.errorsList.contains(messages.get("errorAllFieldsMustBeFilledIn")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postIfErrorsContainsNewPasswordSecond() throws Exception {
    controller.errors.put("newPasswordSecond",new RuntimeException());

    assertRender(controller.post());

    assertEquals(2, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    assertTrue(controller.errorsList.contains(messages.get("errorAllFieldsMustBeFilledIn")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postIfOldPasswordIsBlank() throws Exception {
    controller.oldPassword = "";
    controller.newPasswordFirst = "newPassword";
    controller.newPasswordSecond = "newPassword";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorAllFieldsMustBeFilledIn")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postIfNewPasswordFirstIsBlank() throws Exception {
    controller.oldPassword = "oldPassword";
    controller.newPasswordFirst = "";
    controller.newPasswordSecond = "newPassword";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorAllFieldsMustBeFilledIn")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postIfNewPasswordSecondIsBlank() throws Exception {
    controller.oldPassword = "oldPassword";
    controller.newPasswordFirst = "newPassword";
    controller.newPasswordSecond = "";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorAllFieldsMustBeFilledIn")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postWithWrongOldPassword() throws Exception {
    controller.oldPassword = "wrongPassword";
    controller.newPasswordFirst = "newPassword";
    controller.newPasswordSecond = "newPassword";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorWrongOldPassword")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postWithDifferentNewPasswords() throws Exception {
    controller.oldPassword = "password";
    controller.newPasswordFirst = "newPassword1";
    controller.newPasswordSecond = "newPassword2";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorNewPasswordsDiffer")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postWithEqualOldAndNewPasswords() throws Exception {
    controller.oldPassword = "password";
    controller.newPasswordFirst = "password";
    controller.newPasswordSecond = "password";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorNewAndOldPasswordEqual")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postWithTooShortPassword() throws Exception {
    controller.oldPassword = "password";
    controller.newPasswordFirst = "pass";
    controller.newPasswordSecond = "pass";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("passwordTooShort")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postIfUpdateFailsWithConstraintViolation() throws Exception {
    controller.oldPassword = "password";
    controller.newPasswordFirst = "newPassword";
    controller.newPasswordSecond = "newPassword";
    doThrow(mock(ConstraintViolationException.class)).when(hibernate).update(any(User.class));

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorFailedToChangePassword")));
    verify(transaction).rollback();
  }

  @Test
  public void postWithPasswordUpdateSuccess() throws Exception {
    controller.oldPassword = "password";
    controller.newPasswordFirst = "newPassword";
    controller.newPasswordSecond = "newPassword";

    assertRedirect(Home.class, controller.post());

    User updatedUser = getUpdatedEntity();
    assertTrue(validatePassword("newPassword", updatedUser.getPassword()));
    assertEquals("Username", updatedUser.getUsername());
  }
}