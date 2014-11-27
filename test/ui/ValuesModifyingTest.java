package ui;

import com.codeborne.selenide.SelenideElement;
import model.Goal;
import model.Metric;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import java.math.BigDecimal;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ValuesModifyingTest extends UITest {

  Goal goal = new Goal("Sisestatud eesmark","", 100,1);
  Metric metric = new Metric(goal, "Another metric", "", "erarara", "", 34363.0, "", 100000.0, "", "", "", 1.0, true);

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();


    hibernate.save(goal);

    hibernate.save(metric);
    hibernate.save(new Metric(goal, "Some metric", "", "", "", 0.0, "", 20.0, "", "", "", 2.0, true));

  }

  @After
  public void after() {
  $("#logout-button").click();
  }


  @Test
  public void successModifyingEmptyMeasuredValue () throws Exception {
    open("/admin/values/value");

    SelenideElement tableCell = $$(".goal").get(0).$$(".metric").get(0).$$("td").get(3);
    tableCell.$$("div.measured").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.measured").get(0).$$("input.modify-value").get(0).setValue("4564.656").pressEnter();

    tableCell.$$("div.measured").get(0).$$("span.value").get(0).shouldHave(text("4564.7"));
  }

  @Test
  public void successModifyingExistingMeasuredValue() throws Exception {
    open("/admin/values/value");
    SelenideElement tableCell = $$(".goal").get(0).$$(".metric").get(0).$$("td").get(5);
    tableCell.$$("div.measured").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.measured").get(0).$$("input.modify-value").get(0).setValue("4345356498989898989898999118.65689898989555").pressEnter();

    tableCell.$$("div.measured").get(0).$$("span.value").get(0).shouldHave(text("4345356498989898989898999118.7"));

    tableCell.$$("div.measured").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.measured").get(0).$$("input.modify-value").get(0).shouldHave(value("4345356498989898989898999118.7"));
    tableCell.$$("div.measured").get(0).$$("input.modify-value").get(0).setValue("-4345356498989898989898999118.63").pressEnter();
    tableCell.$$("div.measured").get(0).$$("span.value").get(0).shouldHave(text("4345356498989898989898999118.6"));
  }

  @Test
  public void modifyingEmptyForecastValue() throws Exception {
    open("/admin/values/value");
    SelenideElement tableCell = $$(".goal").get(0).$$(".metric").get(0).$$("td").get(2);
    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.forecasted").get(0).$$("input.modify-value").get(0).setValue("56").pressEnter();
    tableCell.$$("div.forecasted").get(0).$$("input.modify-value").get(0).shouldNotBe(visible);

    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).shouldHave(text("56"));
    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).shouldNotHave(text("56.0"));
  }

  @Test
  public void modifyingExistingForecastValue() throws Exception {
    open("/admin/values/value");
    SelenideElement tableCell = $$(".goal").get(0).$$(".metric").get(0).$$("td").get(8);
    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.forecasted").get(0).$$("input.modify-value").get(0).setValue("53453").pressEnter();

    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).shouldHave(text("53453"));
    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.forecasted").get(0).$$("input.modify-value").get(0).setValue("-4").pressEnter();
    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).shouldHave(text("-4"));
  }

  @Test
  public void deleteExistingMeasuredValue() throws Exception {
    open("/admin/values/value");
    SelenideElement tableCell = $$(".goal").get(0).$$(".metric").get(0).$$("td").get(8);
    tableCell.$$("div.measured").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.measured").get(0).$$("input.modify-value").get(0).setValue("53453.87").pressEnter();

    tableCell.$$("div.measured").get(0).$$("span.value").get(0).shouldHave(text("53453.9"));
    tableCell.$$("div.measured").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.measured").get(0).$$("input.modify-value").get(0).setValue("").pressEnter();
    tableCell.$$("div.measured").get(0).$$("span.value").get(0).shouldHave(text(""));
    tableCell.$$("div.measured").get(0).$$("input.modify-value").get(0).shouldNotBe(visible);

  }

  @Test
  public void deleteExistingForecastValue() throws Exception {
    open("/admin/values/value");
    SelenideElement tableCell = $$(".goal").get(0).$$(".metric").get(0).$$("td").get(8);
    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.forecasted").get(0).$$("input.modify-value").get(0).setValue("53453").pressEnter();
    tableCell.$$("div.forecasted").get(0).$$("input.modify-value").get(0).shouldNotBe(visible);

    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).shouldHave(text("53453"));
    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.forecasted").get(0).$$("input.modify-value").get(0).setValue("").pressEnter();
    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).shouldHave(text(""));
    tableCell.$$("div.forecasted").get(0).$$("input.modify-value").get(0).shouldNotBe(visible);
  }

  @Test
  public void clickingOnPencilClosesPreviousInputFieldAndSaves() throws Exception {
    open("/admin/values/value");
    $$(".goal").get(0).$$(".metric").get(0).$$(".glyphicon-pencil").get(3).click();
    SelenideElement input = $$(".goal").get(0).$$(".metric").get(0).$$(".modify-value").get(3);
    input.setValue("432");
    $$(".goal").get(0).$$(".metric").get(1).$$(".glyphicon-pencil").get(2).click();

    input.shouldNotBe(visible);
    $$(".goal").get(0).$$(".metric").get(0).$$(".value").get(3).shouldHave(text("432"));
    $$(".goal").get(0).$$(".metric").get(1).$$(".modify-value").get(2).shouldBe(visible);
  }

  @Test
  public void startLevelAndTargetLevelShownInValuesTable() throws Exception {
    open("/admin/values/value");

    $$(".goal").get(0).$$(".metric").get(1).$$(".startLevel").get(0).shouldHave(text("0"));
    $$(".goal").get(0).$$(".metric").get(0).$$(".targetLevel").get(0).shouldHave(text("100 000"));
    $$(".goal").get(0).$$(".metric").get(0).$$(".startLevel").get(0).shouldHave(text("34 363"));
    $$(".goal").get(0).$$(".metric").get(1).$$(".targetLevel").get(0).shouldHave(text("20"));
  }

  @Test
  public void measuredValueChangesColor() throws Exception {
    open("/admin/values/value");

    SelenideElement tableCell = $$(".goal").get(0).$$(".metric").get(0).$$("td").get(3);
    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.forecasted").get(0).$$("input.modify-value").get(0).setValue("45").pressEnter();
    tableCell.$$("div.measured").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.measured").get(0).$$("input.modify-value").get(0).setValue("50").pressEnter();
    tableCell.$$("div.measured").get(0).$$("span.value").get(0).shouldHave(cssClass("greenValue"));

    tableCell.$$("div.measured").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.measured").get(0).$$("input.modify-value").get(0).setValue("10").pressEnter();
    tableCell.$$("div.measured").get(0).$$("span.value").get(0).shouldHave(cssClass("redValue"));

    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.forecasted").get(0).$$("input.modify-value").get(0).setValue("").pressEnter();
    tableCell.$$("div.measured").get(0).$$("span.value").get(0).shouldNotHave(cssClass("redValue"));
    tableCell.$$("div.measured").get(0).$$("span.value").get(0).shouldNotHave(cssClass("greenValue"));

    tableCell.$$("div.forecasted").get(0).$$("span.value").get(0).click();
    tableCell.$$("div.forecasted").get(0).$$("input.modify-value").get(0).setValue("5").pressEnter();
    tableCell.$$("div.measured").get(0).$$("span.value").get(0).shouldHave(cssClass("greenValue"));
  }

  @Test
  public void measuredValueHaveCorrectColor() throws Exception {
    metric.getValues().put(2014, new BigDecimal(12));
    metric.getForecasts().put(2014, new BigDecimal(30));
    metric.getValues().put(2015, new BigDecimal(80));
    metric.getForecasts().put(2015, new BigDecimal(10));
    metric.getValues().put(2018, new BigDecimal(75));
    metric.getForecasts().put(2018, new BigDecimal(75));
    metric.getValues().put(2016, new BigDecimal(75));

    hibernate.update(metric);
    hibernate.flush();

    open("/admin/values/value");

    $$(".goal").get(0).$$(".metric").get(0).$$("td").get(2).$$("div.measured").get(0).$$("span.value").get(0).shouldHave(cssClass("redValue"));
    $$(".goal").get(0).$$(".metric").get(0).$$("td").get(3).$$("div.measured").get(0).$$("span.value").get(0).shouldHave(cssClass("greenValue"));
    $$(".goal").get(0).$$(".metric").get(0).$$("td").get(6).$$("div.measured").get(0).$$("span.value").get(0).shouldHave(cssClass("greenValue"));
    $$(".goal").get(0).$$(".metric").get(0).$$("td").get(4).$$("div.measured").get(0).$$("span.value").get(0).shouldNotHave(cssClass("greenValue"));
    $$(".goal").get(0).$$(".metric").get(0).$$("td").get(4).$$("div.measured").get(0).$$("span.value").get(0).shouldNotHave(cssClass("redValue"));
  }
}
