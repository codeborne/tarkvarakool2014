package ui;

import framework.Messages;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class UserAddingTest extends UITest {

  @Before
  public void setUp() throws Exception {
    User user = new User("Delia", "password");
    user.setAdmin(true);
    hibernate.save(user);
    open("/admin/login");
    messages = new Messages(false).getResolverFor("et");
    $(By.name("username")).setValue("Delia");
    $(By.name("password")).setValue("password");
    $("#submit").click();
    $(".glyphicon-user").click();
    $("#settingsAddUser").$("a").click();
  }

  @After
  public void tearDown() throws Exception {
    $("#logout-button").click();
  }

  @Test
  public void adminLeavesInputFieldsEmpty() throws Exception {
    $("#saveNewUser").click();
    $(".alert-danger").shouldHave(text(messages.get("errorAllFieldsMustBeFilledIn")));
  }

  @Test
  public void adminInsertsDifferentPasswords() throws Exception {
    $(By.name("username")).setValue("user");
    $(By.name("passwordFirst")).setValue("password1");
    $(By.name("passwordSecond")).setValue("password2");

    $("#saveNewUser").click();
    $(".alert-danger").shouldHave(text(messages.get("errorPasswordsDiffer")));
  }

  @Test
  public void adminTriesToAddExistingUser() throws Exception {
    $(By.name("username")).setValue("Delia");
    $(By.name("passwordFirst")).setValue("password");
    $(By.name("passwordSecond")).setValue("password");

    $("#saveNewUser").click();
    $(".alert-danger").shouldHave(text(messages.get("errorUserExists")));
  }

  @Test
  public void adminSuccessfullyAddsANewUser() throws Exception {
    $(By.name("username")).setValue("newUser");
    $(By.name("passwordFirst")).setValue("password");
    $(By.name("passwordSecond")).setValue("password");

    $("#saveNewUser").click();
    $$("tr").get(3).$$("td").get(0).shouldHave(text("newUser"));

  }
}
