package ui;

import com.codeborne.selenide.SelenideElement;
import framework.Messages;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class GoalAddingTest extends UITest {

  @Before
  public void setUp() throws Exception {
    messages = new Messages(false).getResolverFor("et");

    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

    open("/admin/goals/home");

  }

  @After
  public void after() {
    SelenideElement logoutButton = $("#logout-button");
    System.out.println("exists: " + logoutButton.exists());
    System.out.println("display: " + logoutButton.isDisplayed());
    System.out.println("toString: " + logoutButton);
    logoutButton.click();
  }


  @Test
  public void adminCanSuccessfullyInsertGoal() throws Exception {


    $(By.name("name")).setValue("Sisestatud eesmark");
    $(By.name("budget")).setValue("100");

    $(".saveGoalButton").click();

    SelenideElement row = $$(".goal").get(0);

    row.$(".nameInTable").shouldHave(text("Sisestatud eesmark"));
    row.$(".budgetInTable").shouldHave(text("100"));
  }

  @Test
  public void adminInsertsInvalidGoal() throws Exception {


    $(By.name("name")).setValue("");
    $(By.name("budget")).setValue("10");

    $(".saveGoalButton").click();

    $(".alert-danger").shouldHave(text(messages.get("errorInsertGoal")));
    $(By.name("budget")).shouldHave(value("10"));
  }

}

