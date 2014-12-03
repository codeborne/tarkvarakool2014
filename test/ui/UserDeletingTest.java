package ui;

import framework.Messages;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class UserDeletingTest extends UITest {

  @Before
  public void setUp() throws Exception {
    User user = new User("Delia", "password");
    user.setAdmin(true);
    hibernate.save(user);
    hibernate.save(new User("Johny", "password"));
    open("/admin/login");
    messages = new Messages(false).getResolverFor("et");
    $(By.name("username")).setValue("Delia");
    $(By.name("password")).setValue("password");
    $("#submit").click();
    $(".glyphicon-user").click();
    $("#settingsDeleteUser").$("a").click();
  }

  @After
  public void tearDown() throws Exception {
    $("#logout-button").click();
  }

  @Test
  public void adminSeesAllUsers() throws Exception {
    $$("tr").get(3).$$("td").get(0).shouldHave(text("Johny"));
    $$("tr").get(2).$$("td").get(0).shouldHave(text("Delia"));
  }

  @Test
  public void adminDeletesUser() throws Exception {
    $$("tr").get(3).$$("td").get(1).$(".glyphicon-trash").click();
    confirm(messages.get("errorDeletingConfirmation"));
    $(".user-table").shouldNotHave(text("Johny"));
  }

  @Test
  public void adminCancelsDelete() throws Exception {
    $$("tr").get(3).$$("td").get(1).$(".glyphicon-trash").click();
    dismiss(messages.get("errorDeletingConfirmation"));
    $$("tr").get(3).$$("td").get(0).shouldHave(text("Johny"));
  }

}
