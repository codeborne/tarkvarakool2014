package ui;

import model.Goal;
import model.Metric;
import model.User;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static junit.framework.Assert.assertEquals;

public class MetricsOrderTest extends UITest {

  Goal goal = new Goal("Sisestatud eesmark", "comment", 100, 1);

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

    hibernate.save(goal);
    hibernate.save(new Metric(goal, "Some metric1", "%", "abc", "def", 10.0, "ghi", 10.0, "jkl", "mno", "pqr", -5.5, true));
    hibernate.save(new Metric(goal, "Some metric4", "", "", "", 0.0, "", 0.0, "", "", "", 5.0, true));
    hibernate.save(new Metric(goal, "Some metric3", "", "", "", 0.0, "", 0.0, "", "", "", 0.0, true));
    hibernate.save(new Metric(goal, "Some metric2", "%", "abc", "def", 10.0, "ghi", 10.0, "jkl", "mno", "pqr", -1.5, true));
  }

  @After
  public void after() {
    $("#logout-button").click();
  }

  @Test @Ignore
  public void testOrderInAdminMetrics() {
    open("/admin/goals/home");
    $$(".metricsButton").get(0).click();

    $$("td.name").get(0).shouldHave(text("Some metric1"));
    $$("td.name").get(1).shouldHave(text("Some metric2"));
    $$("td.name").get(2).shouldHave(text("Some metric3"));
    $$("td.name").get(3).shouldHave(text("Some metric4"));
  }

  @Test @Ignore
  public void testOrderInAdminValues() {
    open("/admin/values/value");

    $$("td.name").get(0).shouldHave(text("Some metric1 (%)"));
    $$("td.name").get(1).shouldHave(text("Some metric2 (%)"));
    $$("td.name").get(2).shouldHave(text("Some metric3"));
    $$("td.name").get(3).shouldHave(text("Some metric4"));
  }

  @Test @Ignore
  public void testOrderInAnonymousHome() {
    open("/home");

    $$("td.name").get(0).shouldHave(text("Some metric1 (%)"));
    $$("td.name").get(1).shouldHave(text("Some metric2 (%)"));
    $$("td.name").get(2).shouldHave(text("Some metric3"));
    $$("td.name").get(3).shouldHave(text("Some metric4"));
  }

  @Test @Ignore
  public void testOrderInAnonymousValues() {
    open("/values");

    $$("td.name").get(0).shouldHave(text("Some metric1 (%)"));
    $$("td.name").get(1).shouldHave(text("Some metric2 (%)"));
    $$("td.name").get(2).shouldHave(text("Some metric3"));
    $$("td.name").get(3).shouldHave(text("Some metric4"));
  }

  @Test
  @Ignore
  public void testAdminChangeOrderInTheMiddle() {
    open("/admin/goals/home");
    $(".metricsButton").click();

    Metric originalMetricObject = (Metric) hibernate.createCriteria(Metric.class).add(Restrictions.eq("name", "Some metric1")).list().get(0);

    $(".metric").$(".glyphicon-sort").dragAndDropTo("#sortable tr:nth-child(4)");

    Metric updatedMetricObject = (Metric) hibernate.createCriteria(Metric.class).add(Restrictions.eq("name", "Some metric1")).list().get(0);

    originalMetricObject.setOrderNumber(2.5);
    assertEquals(originalMetricObject,updatedMetricObject);

    $$("td.name").shouldHave(texts(
      "Some metric2 (%)",
      "Some metric3",
      "Some metric1 (%)",
      "Some metric4"));

    open("/admin/goals/home");
    $(".metricsButton").click();

    $$("td.name").shouldHave(texts(
      "Some metric2 (%)",
      "Some metric3",
      "Some metric1 (%)",
      "Some metric4"));
  }

  @Test @Ignore
  public void testAdminMoveElementToFirstPosition() {
    open("/admin/goals/home");
    $$(".metricsButton").get(0).click();

    Metric originalMetricObject = (Metric) hibernate.createCriteria(Metric.class).add(Restrictions.eq("name", "Some metric2")).list().get(0);

    $$(".metric").get(1).$(".glyphicon-sort").dragAndDropTo("#sortable tr:first-child");

    Metric updatedMetricObject = (Metric) hibernate.createCriteria(Metric.class).add(Restrictions.eq("name", "Some metric2")).list().get(0);

    originalMetricObject.setOrderNumber(-7.0);
    assertEquals(originalMetricObject,updatedMetricObject);

    $$("td.name").get(0).shouldHave(text("Some metric2 (%)"));
    $$("td.name").get(1).shouldHave(text("Some metric1 (%)"));
    $$("td.name").get(2).shouldHave(text("Some metric3"));
    $$("td.name").get(3).shouldHave(text("Some metric4"));

    open("/admin/goals/home");
    $$(".metricsButton").get(0).click();

    $$("td.name").get(0).shouldHave(text("Some metric2 (%)"));
    $$("td.name").get(1).shouldHave(text("Some metric1 (%)"));
    $$("td.name").get(2).shouldHave(text("Some metric3"));
    $$("td.name").get(3).shouldHave(text("Some metric4"));
  }

}
