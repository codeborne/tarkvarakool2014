package ui;

import model.Goal;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;

public class GoalModifyingTest extends UITest {

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

    hibernate.save(new Goal("Sisestatud eesmark", 100));

  }

  @After
  public void after() {
    $("#logout-button").click();
  }

  @Test
  public void adminClicksOnModifyButton () {

    open("/admin/goals/home");

    $("#modifyButton").click();

    $(By.name("name")).shouldHave(value("Sisestatud eesmark"));
    $(By.name("budget")).shouldHave(value("100"));
  }

  @Test
  public void adminModifiesGoal() throws Exception {

    open("/admin/goals/home");

    $("#modifyButton").click();

    $(By.name("name")).setValue("eesmark");
    $(By.name("budget")).setValue("10");

    $("#goalAddOrModifyButton").click();

    $("#goalNameInTable").shouldHave(text("eesmark"));
    $("#goalBudgetInTable").shouldHave(text("10"));

  }

  @Test
  public void adminFailsModifyingGoal() throws Exception {

    open("/admin/goals/home");

    $("#modifyButton").click();

    $(By.name("name")).setValue("");
    $(By.name("budget")).setValue("15");

    $("#goalAddOrModifyButton").click();

    $(".alert-danger").shouldHave(text("Sisestage eesmärk."));
    $(By.name("budget")).shouldHave(value("15"));

  }
}

