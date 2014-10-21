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

    $(".alert-danger").shouldHave(text("Vale kasutajanimi või parool"));
  }

  @Test
  public void userCanSuccessfullyLogIn() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

    $(".greetings").shouldHave(text("Tere, johny"));
    $("#logout-button").click();
  }
}