package ui;

import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;

public class GoalAddingTest extends UITest {

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

  }

  @After
  public void after() {
    $("#logout-button").click();
  }


  @Test
  public void adminCanSuccessfullyInsertGoal() throws Exception {

    open("/admin/goals/add");

    $(By.name("name")).setValue("Sisestatud eesmark");
    $(By.name("budget")).setValue("100");

    $("#goalAddOrModifyButton").click();

    $(".goalNameInTable").shouldHave(text("Sisestatud eesmark"));
    $(".goalBudgetInTable").shouldHave(text("100"));
  }

  @Test
  public void adminInsertsInvalidGoal() throws Exception {

    open("/admin/goals/add");

    $(By.name("name")).setValue("");
    $(By.name("budget")).setValue("10");

    $("#goalAddOrModifyButton").click();

    $(".alert-danger").shouldHave(text("Sisestage eesmärk."));
    $(By.name("budget")).shouldHave(value("10"));
  }

  @Test
  public void adminCancelsAddingGoal() throws Exception {

    open("/admin/goals/add");

    $(By.name("name")).setValue("Eesmark");

    $("#goBack").click();

    $("#noGoals").shouldHave(text("Andmebaasis eesmärke ei ole."));
  }


}

