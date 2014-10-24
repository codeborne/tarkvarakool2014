package ui;

import model.Goal;
import model.Metric;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class MetricDeletingTest extends UITest {

  Goal goal = new Goal("Sisestatud eesmark", 100);

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();


    hibernate.save(goal);
    hibernate.save(new Metric(goal, "Some metric", "", "", "", 0, "", 0, "", "", ""));

  }

  @After
  public void after() {
    $("#logout-button").click();
  }

  @Test
  public void testSuccessfullyDeleteMetricWhenThereIsOneMetric() {
    open("/admin/goals/home");
    $$(".metricsButton").get(0).click();
    $$("tr.metric").shouldHaveSize(1);
    $$(".deleteButton").get(0).click();
    confirm("Kas oled kustutamises kindel?");
    $$("tr.metric").shouldHaveSize(0);
  }

  @Test
  public void testSuccessfullyDeleteMetricWhenThereAreSeveralMetrics() throws Exception {
    hibernate.save(new Metric(goal, "zzz", "", "", "xdfghj", 70, "",40, "", "", ""));
    hibernate.save(new Metric(goal, "another metric", "", "", "", 7, "",4, "", "", ""));
    hibernate.save(new Metric(goal, "bbb", "", "", "xdfghj", 70, "",40, "", "", ""));
    open("/admin/goals/home");

    $$(".metricsButton").get(0).click();
    $$("tr.metric").shouldHaveSize(4);
    $$(".deleteButton").get(1).click();

    confirm("Kas oled kustutamises kindel?");
    $$("tr.metric").shouldHaveSize(3);

    $$("tr.metric").get(0).$(".name").shouldHave(text("another metric"));
    $$("tr.metric").get(1).$(".name").shouldHave(text("Some metric"));
    $$("tr.metric").get(2).$(".name").shouldHave(text("zzz"));
  }


    @Test
    public void adminCancelsMetricDelete() throws Exception {
      open("/admin/goals/home");
      $$(".metricsButton").get(0).click();
      $$("tr.metric").shouldHaveSize(1);
      $$(".deleteButton").get(0).click();
      dismiss("Kas oled kustutamises kindel?");
      $$("tr.metric").get(0).$(".name").shouldHave(text("Some metric"));

    }
}