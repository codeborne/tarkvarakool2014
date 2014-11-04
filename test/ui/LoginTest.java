package ui;

import model.User;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;


public class LoginTest extends UITest {

  @Test
  public void loginFailsWithIncorrectPassword() throws Exception {
    hibernate.save(new User("johny", "foo"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

    $(".alert-danger").shouldHave(text(messages.get("errorUsernameAndPassword")));
  }

  @Test
  public void userCanSuccessfullyLogIn() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

    $(".greetings").shouldHave(text("Hello, johny"));
    $("#logout-button").click();
  }

  @Test
  public void loginFailsWithIncorrectName() throws Exception {
    hibernate.save(new User("mari", "pass"));

    open("/admin/login");

    $(By.name("username")).setValue("meelis");
    $(By.name("password")).setValue("pass");

    $("#submit").click();

    $(".alert-danger").shouldHave(text(messages.get("errorUsernameAndPassword")));

  }

  @Test
  public void loginFailsWithNoUsername() throws Exception {
    hibernate.save(new User("tarmo", "tass"));

    open("/admin/login");

    $(By.name("username")).setValue(null);
    $(By.name("password")).setValue("tass");

    $("#submit").click();

    $(".alert-danger").shouldHave(text(messages.get("errorUsernameAndPassword")));

  }

  @Test
  public void logoutSuccess() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();
    $(".greetings").shouldHave(text("Hello, johny"));

    $("#logout-button").click();
    $("#login-button").exists();
  }
}
