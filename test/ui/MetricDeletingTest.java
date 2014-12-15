package ui;

import com.codeborne.selenide.ElementsCollection;
import model.Goal;
import model.Metric;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertEquals;

public class MetricDeletingTest extends UITest {

  Goal goal = new Goal("Sisestatud eesmark","", 100, 1);

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();


    hibernate.save(goal);
    Metric metric = new Metric(goal, "Some metric", "", "", "", 0.0, "", 0.0, "", "", "", 1.0, true);
    metric.setIsDecreasing(false);
    hibernate.save(metric);

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
    confirm(messages.get("errorDeletingConfirmation"));
    $$("tr.metric").shouldHaveSize(0);
  }

  @Test
  public void testSuccessfullyDeleteMetricWhenThereAreSeveralMetrics() throws Exception {
    Metric metric1 = new Metric(goal, "zzz", "USD", "", "xdfghj", 70.0, "", 40.0, "", "", "", 2.0, true);
    metric1.setIsDecreasing(false);
    hibernate.save(metric1);
    Metric metric2 = new Metric(goal, "another metric", "", "", "", 7.0, "", 4.0, "", "", "", -5.0, true);
    metric2.setIsDecreasing(false);
    hibernate.save(metric2);
    Metric metric3 = new Metric(goal, "bbb", "", "", "xdfghj", 70.0, "", 40.0, "", "", "", 0.0, true);
    metric3.setIsDecreasing(false);
    hibernate.save(metric3);
    open("/admin/goals/home");


    $$(".metricsButton").get(0).click();
    $$("tr.metric").shouldHaveSize(4);
    $$(".deleteButton").get(1).click();

    confirm(messages.get("errorDeletingConfirmation"));
    ElementsCollection metrics = $$("tr.metric");
    metrics.shouldHaveSize(3);

    assertEquals("another metric", metrics.get(0).$(".name").getText());
    assertEquals("Some metric", metrics.get(1).$(".name").getText());
    assertEquals("zzz", metrics.get(2).$(".name").getText());
  }


    @Test
    public void adminCancelsMetricDelete() throws Exception {
      open("/admin/goals/home");
      $$(".metricsButton").get(0).click();
      $$("tr.metric").shouldHaveSize(1);
      $$(".deleteButton").get(0).click();
      dismiss(messages.get("errorDeletingConfirmation"));
      $$("tr.metric").get(0).$(".name").shouldHave(text("Some metric"));

    }
}