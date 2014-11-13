package ui;

import model.Goal;
import model.Metric;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertEquals;

public class MetricsModifyingTest extends UITest {

  Goal goal = new Goal("Sisestatud eesmark","", 100,1);

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

    hibernate.save(goal);
    hibernate.save(new Metric(goal, "Some metric", "%", "abc", "def", 10.0, "ghi", 10.0, "jkl", "http://", "pqr", -5.5, true));
    hibernate.save(new Metric(goal, "Some metric1", "", "", "", 0.0, "", 0.0, "", "", "", 5.0, true));

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
    $$(".metricContent").get(0).$(By.name("name")).shouldHave(value("Some metric"));
    $$(".metricContent").get(0).$(By.name("publicDescription")).shouldHave(value("abc"));
  }

  @Test
  public void adminModifiesMetric(){
    $$(".metricContent").get(0).$(By.name("name")).shouldHave(value("Some metric"));
    $$(".metricContent").get(0).$(By.name("unit")).shouldHave(value("%"));
    $$(".metricContent").get(0).$(By.name("publicDescription")).shouldHave(value("abc"));
    $$(".metricContent").get(0).$(By.name("privateDescription")).shouldHave(value("def"));
    $$(".metricContent").get(0).$(By.name("startLevel")).shouldHave(value("10"));
    $$(".metricContent").get(0).$(By.name("commentOnStartLevel")).shouldHave(value("ghi"));
    $$(".metricContent").get(0).$(By.name("targetLevel")).shouldHave(value("10"));
    $$(".metricContent").get(0).$(By.name("commentOnTargetLevel")).shouldHave(value("jkl"));
    $$(".metricContent").get(0).$(By.name("infoSource")).shouldHave(value("http://"));
    $$(".metricContent").get(0).$(By.name("institutionToReport")).shouldHave(value("pqr"));


    $$(".metricContent").get(0).$(By.name("name")).setValue("Metric");
    $$(".metricContent").get(0).$(By.name("unit")).setValue("EUR");

    $(".saveGoalButton").click();

    assertEquals("Metric", $$("tr.metric").get(0).$(".name").getText());
    assertEquals("EUR", $$("tr.metric").get(0).$(".unit").getText());
  }
}


