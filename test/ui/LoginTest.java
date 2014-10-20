package ui;

import model.User;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;


public class LoginTest extends UITest {

  @Test
  public void loginFailsWithIncorrectPassword() throws Exception {

    session.save(new User("johny", "foo"));
    session.flush();

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#login-button").click();

    $(".alert-danger").shouldHave(text("Vale kasutajanimi voi parool"));
  }

  @Test
  public void userCanSuccessfullyLogIn() throws Exception {
    session.save(new User("johny", "p2s3w04d"));
    session.flush();

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#login-button").click();

    $(".greetings").shouldHave(text("Tere, johny"));
    $("#logout-button").click();
  }
}
