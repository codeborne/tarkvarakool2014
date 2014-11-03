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
    hibernate.save(new Metric(goal, "Some metric", "", "", "", 0, "", 0, "", "", "", 1.0, true));

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
    hibernate.save(new Metric(goal, "zzz", "USD", "", "xdfghj", 70, "",40, "", "", "", 2.0, true));
    hibernate.save(new Metric(goal, "another metric", "", "", "", 7, "",4, "", "", "", -5.0, true));
    hibernate.save(new Metric(goal, "bbb", "", "", "xdfghj", 70, "",40, "", "", "", 0.0, true));
    open("/admin/goals/home");

    $$(".metricsButton").get(0).click();
    $$("tr.metric").shouldHaveSize(4);
    $$(".deleteButton").get(1).click();

    confirm("Kas oled kustutamises kindel?");
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
      dismiss("Kas oled kustutamises kindel?");
      $$("tr.metric").get(0).$(".name").shouldHave(text("Some metric"));

    }
}