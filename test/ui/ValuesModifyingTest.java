package ui;

import com.codeborne.selenide.SelenideElement;
import model.Goal;
import model.Metric;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ValuesModifyingTest extends UITest {

  Goal goal = new Goal("Sisestatud eesmark","", 100,1);

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();


    hibernate.save(goal);
    hibernate.save(new Metric(goal, "Another metric", "", "erarara", "", 34363, "", 100000, "", "", "", 1.0, true));
    hibernate.save(new Metric(goal, "Some metric", "", "", "", 0, "", 20, "", "", "", 2.0, true));

  }

  @After
  public void after() {
  $("#logout-button").click();
  }


  @Test
  public void successModifyingEmptyMeasuredValue () throws Exception {
    open("/admin/values/value");

    $$(".glyphicon-pencil").get(0).click();
    SelenideElement input = $$(".modify-value").get(0);
    input.setValue("4564.656").pressEnter();
    input.shouldNotBe(visible);

    $$(".value").get(0).shouldHave(text("4564.7"));
  }

  @Test
  public void successModifyingExistingMeasuredValue() throws Exception {
    open("/admin/values/value");

    $$(".glyphicon-pencil").get(0).click();
    SelenideElement input = $$(".modify-value").get(0);
    input.setValue("4345356498989898989898999118.65689898989555").pressEnter();
    input.shouldNotBe(visible);

    $$(".value").get(0).shouldHave(text("4345356498989898989898999118.7"));

    $$(".glyphicon-pencil").get(0).click();
    input = $$(".modify-value").get(0);
    input.shouldHave(value("4345356498989898989898999118.7"));

    input.setValue("-4345356498989898989898999118.65").pressEnter();
    input.shouldNotBe(visible);

    $$(".value").get(0).shouldHave(text("-4345356498989898989898999118.7"));

  }

  @Test
  public void modifyingEmptyForecastValue() throws Exception {
    open("/admin/values/value");

    $$(".glyphicon-pencil").get(1).click();
    SelenideElement input = $$(".modify-value").get(1);
    input.setValue("0").pressEnter();
    input.shouldNotBe(visible);
    $$(".value").get(1).shouldHave(text("0"));
  }

  @Test
  public void modifyingExistingForecastValue() throws Exception {
    open("/admin/values/value");

    $$(".glyphicon-pencil").get(1).click();
    SelenideElement input = $$(".modify-value").get(1);
    input.setValue("53453").pressEnter();
    input.shouldNotBe(visible);
    $$(".value").get(1).shouldHave(text("53453"));

    $$(".glyphicon-pencil").get(1).click();
    input = $$(".modify-value").get(1);
    input.setValue("-4").pressEnter();
    input.shouldNotBe(visible);
    $$(".value").get(1).shouldHave(text("-4"));
  }

  @Test
  public void deleteExistingMeasuredValue() throws Exception {
    open("/admin/values/value");

    $$(".glyphicon-pencil").get(0).click();
    SelenideElement input = $$(".modify-value").get(0);
    input.setValue("-4564.656").pressEnter();
    input.shouldNotBe(visible);

    $$(".value").get(0).shouldHave(text("-4564.7"));

    $$(".glyphicon-pencil").get(0).click();
    input = $$(".modify-value").get(0);
    input.setValue("").pressEnter();
    input.shouldNotBe(visible);

    $$(".value").get(0).shouldHave(text(""));
  }

  @Test
  public void deleteExistingForecastValue() throws Exception {
    open("/admin/values/value");

    $$(".glyphicon-pencil").get(3).click();
    SelenideElement input = $$(".modify-value").get(3);
    input.setValue("53453").pressEnter();
    input.shouldNotBe(visible);
    $$(".value").get(3).shouldHave(text("53453"));

    $$(".glyphicon-pencil").get(3).click();
    input = $$(".modify-value").get(3);
    input.setValue("").pressEnter();
    input.shouldNotBe(visible);
    $$(".value").get(3).shouldHave(text(""));
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
    $$(".goal").get(0).$$(".metric").get(0).$$(".targetLevel").get(0).shouldHave(text("100000"));
    $$(".goal").get(0).$$(".metric").get(0).$$(".startLevel").get(0).shouldHave(text("34363"));
    $$(".goal").get(0).$$(".metric").get(1).$$(".targetLevel").get(0).shouldHave(text("20"));
  }
}
