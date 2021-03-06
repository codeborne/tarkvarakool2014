package ui;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import model.Goal;
import model.Metric;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertEquals;

public class HomeViewValuesTest extends UITest {

  @Before
  public void setUp() throws Exception {
    open("/home");
  }

  @Test
  public void userShouldSeeGoalsMetricsStartLevelsAndTargetLevels() throws Exception {
    Goal goal1 = new Goal("Eesmark1","", 145, 1);
    Goal goal2 = new Goal("Eesmark2","", 120, 2);

    hibernate.save(goal1);
    hibernate.save(goal2);

    hibernate.save(new Metric(goal1, "Mood1", "", "", "", 100.0, "", 150.0, "", "", "", 1.0, true));
    hibernate.save(new Metric(goal1, "Mood2", "$", "", "", 200.0, "", 220.0, "2014", "", "", 2.0, true));
    hibernate.save(new Metric(goal2, "Mood3", "", "", "", 300.0, "", 340.0, "", "", "", 1.0, true));
    hibernate.save(new Metric(goal2, "Mood4", "", "", "", 400.0, "", 500.0, "", "", "", 2.0, true));
    hibernate.save(new Metric(goal2, "Mood5", "", "", "", 400.0, "", 500.0, "", "", "", 2.1, false));

    $(".switch-button.active").click();

    ElementsCollection goals = $$(".goal");
    $$(".goal").shouldHaveSize(2);

    SelenideElement goalBlock1 = goals.get(0);
    goalBlock1.$("h4.name").shouldHave(text("Eesmark1"));
    ElementsCollection goal1Metrics = goalBlock1.$$(".metric");
    assertEquals("Mood1", goal1Metrics.get(0).$(".name").getText());
    assertEquals("Mood2", goal1Metrics.get(1).$(".name").getText());
    goal1Metrics.get(0).$(".startLevel").shouldHave(text("100"));
    goal1Metrics.get(0).$(".targetLevel").shouldHave(text("150"));
    goal1Metrics.get(1).$(".startLevel").shouldHave(text("200 $"));
    goal1Metrics.get(1).$(".targetLevel").shouldHave(text("220 $ (2014)"));

    SelenideElement goalBlock2 = goals.get(1);
    goalBlock2.$("h4.name").shouldHave(text("Eesmark2"));
    ElementsCollection goal2Metrics = goalBlock2.$$(".metric");
    assertEquals("Mood3", goal2Metrics.get(0).$(".name").getText());
    assertEquals("Mood4", goal2Metrics.get(1).$(".name").getText());
    goal2Metrics.get(0).$(".startLevel").shouldHave(text("300"));
    goal2Metrics.get(0).$(".targetLevel").shouldHave(text("340"));
    goal2Metrics.get(1).$(".startLevel").shouldHave(text("400"));
    goal2Metrics.get(1).$(".targetLevel").shouldHave(text("500"));
    goalBlock2.$(".name").shouldNotHave(text("Mood5"));
  }

  @Test
  public void userShouldSeeMetricValuesPerYear() throws Exception {
    Goal goal = new Goal("Eesmark", "", 10, 1);
    hibernate.save(goal);
    Metric metric = new Metric(goal, "Moodik", "", "", "", 0.0, "", 0.0, "", "", "", 1.0, true);
    metric.getValues().put(2014, new BigDecimal(12));
    metric.getValues().put(2015, new BigDecimal(13));
    metric.getValues().put(2016, new BigDecimal(14));
    metric.getValues().put(2017, new BigDecimal(15));
    metric.getValues().put(2018, new BigDecimal(16));
    metric.getValues().put(2019, new BigDecimal(17));
    metric.getValues().put(2020, new BigDecimal(18));
    metric.getForecasts().put(2014, new BigDecimal(10));
    metric.getForecasts().put(2015, new BigDecimal(15));
    metric.getForecasts().put(2016, new BigDecimal(14));
    metric.getForecasts().put(2017, new BigDecimal(11));
    metric.getForecasts().put(2018, new BigDecimal(18));

    metric.getForecasts().put(2020, new BigDecimal(15));
    metric.setIsDecreasing(false);

    hibernate.save(metric);
    hibernate.flush();

    $$(".switch-button").get(1).click();

    $$(".goal").shouldHaveSize(1);

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

    metricValues.get(0).shouldHave(cssClass("greenValue"));
    metricValues.get(1).shouldHave(cssClass("redValue"));
    metricValues.get(2).shouldHave(cssClass("greenValue"));
    metricValues.get(3).shouldHave(cssClass("greenValue"));
    metricValues.get(4).shouldHave(cssClass("redValue"));
    metricValues.get(5).shouldNotHave(cssClass("redValue"));
    metricValues.get(5).shouldNotHave(cssClass("greenValue"));
    metricValues.get(6).shouldHave(cssClass("greenValue"));
  }

  @Test
  public void goalsAreDisplayedInCorrectOrder() throws Exception {
    hibernate.save(new Goal("Eesmark3", "", 103, 3));
    hibernate.save(new Goal("Eesmark1", "", 101, 1));
    hibernate.save(new Goal("Eesmark5", "", 105, 5));
    hibernate.save(new Goal("Eesmark4", "", 104, 4));
    hibernate.save(new Goal("Eesmark2", "", 102, 2));
    $(".switch-button.active").click();

    $$(".goal").get(0).$("h4").shouldHave(text("Eesmark1"));
    $$(".goal").get(1).$("h4").shouldHave(text("Eesmark2"));
    $$(".goal").get(2).$("h4").shouldHave(text("Eesmark3"));
    $$(".goal").get(3).$("h4").shouldHave(text("Eesmark4"));
    $$(".goal").get(4).$("h4").shouldHave(text("Eesmark5"));

  }

  @Test
  public void userShouldSeeYearlyBudgets() throws Exception {
    Goal goal = new Goal("Eesmark", "", 10, 1);
    goal.getYearlyBudgets().put(2014, 100000L);
    goal.getYearlyBudgets().put(2015, 200000L);
    goal.getYearlyBudgets().put(2016, 300000L);
    goal.getYearlyBudgets().put(2017, 400000L);
    goal.getYearlyBudgets().put(2018, 500000L);
    goal.getYearlyBudgets().put(2019, 600000L);
    goal.getYearlyBudgets().put(2020, 700000L);
    hibernate.save(goal);
    hibernate.flush();
    open("/home");
    $("#MetricsValue").click();

    $$(".goal").get(0).$(".moneySpentRow").$$("td").get(2).shouldHave(text("100 000"));
    $$(".goal").get(0).$(".moneySpentRow").$$("td").get(3).shouldHave(text("200 000"));
    $$(".goal").get(0).$(".moneySpentRow").$$("td").get(4).shouldHave(text("300 000"));
    $$(".goal").get(0).$(".moneySpentRow").$$("td").get(5).shouldHave(text("400 000"));
    $$(".goal").get(0).$(".moneySpentRow").$$("td").get(6).shouldHave(text("500 000"));
    $$(".goal").get(0).$(".moneySpentRow").$$("td").get(7).shouldHave(text("600 000"));
    $$(".goal").get(0).$(".moneySpentRow").$$("td").get(8).shouldHave(text("700 000"));

  }
}


