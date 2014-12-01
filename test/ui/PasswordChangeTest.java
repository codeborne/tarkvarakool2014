package ui;

import framework.Messages;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class PasswordChangeTest extends UITest {

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "password"));
    open("/admin/login");
    messages = new Messages(false).getResolverFor("et");
    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("password");
    $("#submit").click();
    $(".glyphicon-user").click();
  }

  @After
  public void tearDown() throws Exception {
    $("#logout-button").click();
  }

  @Test
  public void userLeavesInputFieldsEmpty() throws Exception {
    $("#saveNewPassword").click();
    $(".alert-danger").shouldHave(text(messages.get("errorAllFieldsMustBeFilledIn")));
  }

  @Test
  public void userEntersWrongPassword() throws Exception {
    $(By.name("oldPassword")).setValue("passw");
    $(By.name("newPasswordFirst")).setValue("password1");
    $(By.name("newPasswordSecond")).setValue("password1");
    $("#saveNewPassword").click();
    $(".alert-danger").shouldHave(text(messages.get("errorWrongOldPassword")));
  }

  @Test
  public void userChangesPassword() throws Exception {
    $(By.name("oldPassword")).setValue("password");
    $(By.name("newPasswordFirst")).setValue("password1");
    $(By.name("newPasswordSecond")).setValue("password1");
    $("#saveNewPassword").click();
    $(".alert-success").shouldHave(text(messages.get("passwordChangeSuccess")));
  }

}
