package ui;

import model.Goal;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.confirm;

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

    $("#deleteButton").click();

    confirm("Kas oled kustutamises kindel?");

    $("#noGoals").shouldHave(text("Andmebaasis eesmärke ei ole."));

  }



}
