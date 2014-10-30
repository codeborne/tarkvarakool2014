package ui;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import model.Goal;
import model.Metric;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertEquals;

public class HomeViewValuesTest extends UITest {

  @Before
  public void setUp() throws Exception {
    open("/home");
  }

  @After
  public void afterSetUp() throws Exception {
    open("/home");
  }

  @Test
  public void userShouldSeeGoalsAndMetrics() throws Exception {
    Goal goal1 = new Goal("Eesmark1","", 145, 1);
    Goal goal2 = new Goal("Eesmark2","", 120, 2);

    hibernate.save(goal1);
    hibernate.save(goal2);

    hibernate.save(new Metric(goal1, "Mood1", "", "", "", 0, "", 0, "", "", "", 1.0));
    hibernate.save(new Metric(goal1, "Mood2", "$", "", "", 0, "", 0, "", "", "", 2.0));
    hibernate.save(new Metric(goal2, "Mood3", "", "", "", 0, "", 0, "", "", "", 1.0));
    hibernate.save(new Metric(goal2, "Mood4", "", "", "", 0, "", 0, "", "", "", 2.0));

    $(".switch-button.active").click();

    ElementsCollection goals = $$(".goal");

    $$(".goal").shouldHaveSize(2);

    SelenideElement goalBlock1 = goals.get(0);
    goalBlock1.$("h4.name").shouldHave(text("Eesmark1"));

    ElementsCollection goal1Metrics = goalBlock1.$$(".metric");

    assertEquals("Mood1", goal1Metrics.get(0).$(".name").getText());
    assertEquals("Mood2 ($)", goal1Metrics.get(1).$(".name").getText());

    SelenideElement goalBlock2 = goals.get(1);
    goalBlock2.$("h4.name").shouldHave(text("Eesmark2"));

    ElementsCollection goal2Metrics = goalBlock2.$$(".metric");
    assertEquals("Mood3", goal2Metrics.get(0).$(".name").getText());
    assertEquals("Mood4", goal2Metrics.get(1).$(".name").getText());
  }

  @Test
  public void userShouldSeeMetricValuesPerYear() throws Exception {
    Goal goal = new Goal("Eesmark", "", 10, 1);
    hibernate.save(goal);
    Metric metric = new Metric(goal, "Moodik", "", "", "", 0, "", 0, "", "", "", 1.0);
    metric.getValues().put(2014, new BigDecimal(12));
    metric.getValues().put(2015, new BigDecimal(13));
    metric.getValues().put(2016, new BigDecimal(14));
    metric.getValues().put(2017, new BigDecimal(15));
    metric.getValues().put(2018, new BigDecimal(16));
    metric.getValues().put(2019, new BigDecimal(17));
    metric.getValues().put(2020, new BigDecimal(18));
    hibernate.save(metric);
    hibernate.flush();

    $$(".switch-button").get(1).click();

    $$(".goal").shouldHaveSize(2);

    SelenideElement goalBlock = $$(".goal").get(0);
    $(goalBlock, "h4.name").shouldHave(text("Eesmark"));
    $$(goalBlock, ".metric").shouldHaveSize(1);

    SelenideElement metricRow = $$(goalBlock, ".metric").get(0);
    $(metricRow, ".name").shouldHave(text("Moodik"));

    ElementsCollection metricValues = $$(metricRow, ".value");
    metricValues.shouldHaveSize(7);
    assertEquals("12", metricValues.get(0).text());
    assertEquals("13", metricValues.get(1).text());
    assertEquals("14", metricValues.get(2).text());
    assertEquals("15", metricValues.get(3).text());
    assertEquals("16", metricValues.get(4).text());
    assertEquals("17", metricValues.get(5).text());
    assertEquals("18", metricValues.get(6).text());


  }

  @Test
  public void goalsAreDisplayedInCorrectOrder() throws Exception {
    hibernate.save(new Goal("Eesmark3", "", 103, 3));
    hibernate.save(new Goal("Eesmark1", "", 101, 1));
    hibernate.save(new Goal("Eesmark5", "", 102, 5));
    hibernate.save(new Goal("Eesmark4", "", 104, 4));
    hibernate.save(new Goal("Eesmark2", "", 105, 2));
    $(".switch-button.active").click();

    $$(".goal").get(0).$("h4").shouldHave(text("Eesmark1"));
    $$(".goal").get(1).$("h4").shouldHave(text("Eesmark2"));
    $$(".goal").get(2).$("h4").shouldHave(text("Eesmark3"));
    $$(".goal").get(3).$("h4").shouldHave(text("Eesmark4"));
    $$(".goal").get(4).$("h4").shouldHave(text("Eesmark5"));

  }
}


