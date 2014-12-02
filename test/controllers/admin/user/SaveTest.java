package controllers.admin.user;

import controllers.ControllerTest;
import model.User;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

import static helpers.Password.validatePassword;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SaveTest extends ControllerTest<Save>{
  @Before
  public void setUp() throws Exception {
    when(request.getPathInfo()).thenReturn("admin/");

  }

  @Test
  public void postIfErrorsContainsUsername() throws Exception {
    controller.errors.put("username",new RuntimeException());

    assertRender(controller.post());

    assertEquals(2, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    assertTrue(controller.errorsList.contains(messages.get("errorAllFieldsMustBeFilledIn")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postIfErrorsContainsPasswordFirst() throws Exception {
    controller.errors.put("passwordFirst",new RuntimeException());

    assertRender(controller.post());

    assertEquals(2, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    assertTrue(controller.errorsList.contains(messages.get("errorAllFieldsMustBeFilledIn")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postIfErrorsContainsPasswordSecond() throws Exception {
    controller.errors.put("passwordSecond",new RuntimeException());

    assertRender(controller.post());

    assertEquals(2, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    assertTrue(controller.errorsList.contains(messages.get("errorAllFieldsMustBeFilledIn")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postIfOldPasswordIsBlank() throws Exception {
    controller.username = "";
    controller.passwordFirst = "password";
    controller.passwordSecond = "password";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorAllFieldsMustBeFilledIn")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postIfNewPasswordFirstIsBlank() throws Exception {
    controller.username = "user";
    controller.passwordFirst = "";
    controller.passwordSecond = "password";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorAllFieldsMustBeFilledIn")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postIfNewPasswordSecondIsBlank() throws Exception {
    controller.username = "user";
    controller.passwordFirst = "password";
    controller.passwordSecond = "";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorAllFieldsMustBeFilledIn")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postWithDifferentPasswords() throws Exception {
    controller.username = "user";
    controller.passwordFirst = "newPassword1";
    controller.passwordSecond = "newPassword2";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorPasswordsDiffer")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postWithTooShortPassword() throws Exception {
    controller.username = "user";
    controller.passwordFirst = "pass";
    controller.passwordSecond = "pass";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("passwordTooShort")));
    verify(hibernate, never()).update(any(User.class));
  }

  @Test
  public void postIfSaveFailsWithConstraintViolation() throws Exception {
    controller.username = "user";
    controller.passwordFirst = "password";
    controller.passwordSecond = "password";
    doThrow(mock(ConstraintViolationException.class)).when(hibernate).save(any(User.class));

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorUserExists")));
    verify(transaction).rollback();
  }

  @Test
  public void postIfSaveSucceeds() throws Exception {
    controller.username = "user";
    controller.passwordFirst = "password";
    controller.passwordSecond = "password";

    assertRedirect(Delete.class, controller.post());

    User savedUser = getSavedEntity();

    assertEquals("user", savedUser.getUsername());
    assertTrue(validatePassword("password", savedUser.getPassword()));
  }
}