package ui;

import model.Goal;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class GoalDeletingTest extends UITest {

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
  public void adminClicksOnDeleteButton() {

    open("/admin/goals/home");

    $(".deleteButton").click();

    confirm("Kas oled kustutamises kindel?");

    $("#noGoals").shouldHave(text("Andmebaasis eesmärke ei ole."));

  }

  @Test
  public void adminDeletesOneGoalFromMultipleGoals() throws Exception {

    hibernate.save(new Goal("eesmark", 10));
    open("/admin/goals/home");

    $$(".deleteButton").get(0).click();

    confirm("Kas oled kustutamises kindel?");

    $(".goalNameInTable").shouldNotHave(text("Sisestatud eesmark"));
    $(".goalNameInTable").shouldHave(text("eesmark"));

  }

  @Test
  public void adminCancelsDelete() throws Exception {
    open("/admin/goals/home");

    $(".deleteButton").click();

    dismiss("Kas oled kustutamises kindel?");

    $(".goalNameInTable").shouldHave(text("Sisestatud eesmark"));


  }
}