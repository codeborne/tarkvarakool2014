package ui;

import model.Goal;
import model.Metric;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MetricsModifyingTest extends UITest {

  Goal goal = new Goal("Sisestatud eesmark", 100);

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();


    hibernate.save(goal);
    hibernate.save(new Metric(goal, "Some metric", "", "", 0, "", 0, "", "", ""));
    hibernate.save(new Metric(goal, "Some metric1", "", "", 0, "", 0, "", "", ""));

    open("/admin/goals/home");

    $$(".metricsButton").get(0).click();
    $$(".modifyButton").get(0).click();


  }

  @After
  public void after() {
    $("#logout-button").click();
  }

  @Test
  public void adminClicksOnModifyButton() {

    $(By.name("name")).shouldHave(value("Some metric"));
    $(By.name("publicDescription")).shouldHave(value(""));

  }

  @Test
  public void adminModifiesMetric(){

    $(By.name("name")).setValue("Metric");

    $(".submitButton").click();

    $$("tr.metric").get(0).$(".name").shouldHave(text("Metric"));


  }

  @Test
  public void adminFailsModifyingMetric(){


    $(By.name("name")).setValue("Some metric1");

    $(".submitButton").click();

    $(".alert-danger").shouldHave(text("See mõõdik on juba sisestatud."));




  }
}


