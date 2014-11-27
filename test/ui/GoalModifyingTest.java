package ui;

import com.codeborne.selenide.SelenideElement;
import framework.Messages;
import model.Goal;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class GoalModifyingTest extends UITest {

  @Before
  public void setUp() throws Exception {
    messages = new Messages(false).getResolverFor("et");
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");
    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

    hibernate.save(new Goal("Sisestatud eesmark","", 100, 1));
    open("/admin/goals/home");
  }

  @After
  public void after() {
    $("#logout-button").click();
  }

  @Test
  public void adminClicksOnModifyButton () {

    $(".modifyButton").click();

    $(By.name("name")).shouldHave(value("Sisestatud eesmark"));
    $(By.name("budget")).shouldHave(value("100"));
  }

  @Test
  public void adminModifiesGoal() throws Exception {

    $(".modifyButton").click();

    $(By.name("name")).setValue("eesmark");
    $(By.name("budget")).setValue("10");

    $(".saveGoalButton").click();

    SelenideElement row = $$("tr.goal").get(0);

    row.$(".nameInTable").shouldHave(text("eesmark"));
    row.$(".budgetInTable").shouldHave(text("10"));

  }

  @Test
  public void adminFailsModifyingGoal() throws Exception {

    $(".modifyButton").click();

    $(By.name("name")).setValue("");
    $(By.name("budget")).setValue("15");

    $(".saveGoalButton").click();

    $(".alert-danger").shouldHave(text(messages.get("errorInsertGoal")));
    $(By.name("budget")).shouldHave(value("15"));

  }
}

