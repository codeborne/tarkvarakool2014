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
import static com.codeborne.selenide.Selenide.*;

public class ValuesModifyingTest extends UITest {

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
    hibernate.save(new Metric(goal, "Another metric", "", "erarara", "", 34363, "", 0, "", "", ""));
  }

  @After
  public void after() {
    $("#logout-button").click();
  }


  @Test
  public void successModifyingEmptyMeasuredValue () throws Exception {
    open("/admin/values/value");

    $$(".glyphicon").get(0).click();
    SelenideElement input = $$(".modify-value").get(0);
    input.setValue("4564.656").pressEnter();
    input.shouldNotBe(visible); // wait for response

    $$(".value").get(0).shouldHave(text("4564.656"));
  }

  @Test
  public void successModifyingExistingMeasuredValue() throws Exception {
    open("/admin/values/value");

    $$(".glyphicon").get(0).click();
    SelenideElement input = $$(".modify-value").get(0);
    input.setValue("-4564.656").pressEnter();
    input.shouldNotBe(visible); // wait for response

    $$(".value").get(0).shouldHave(text("-4564.656"));

    $$(".glyphicon").get(0).click();
    input = $$(".modify-value").get(0);
    input.setValue("43453564.656").pressEnter();
    input.shouldNotBe(visible); // wait for response

    $$(".value").get(0).shouldHave(text("43453564.656"));

  }

  @Test
  public void modifyingEmptyForecastValue() throws Exception {
    open("/admin/values/value");

    $$(".glyphicon").get(1).click();
    SelenideElement input = $$(".modify-value").get(1);
    input.setValue("0").pressEnter();
    input.shouldNotBe(visible); // wait for response
    $$(".value").get(1).shouldHave(text("0"));
  }

  @Test
  public void modifyigExistingForecastValue() throws Exception {
    open("/admin/values/value");

    $$(".glyphicon").get(1).click();
    SelenideElement input = $$(".modify-value").get(1);
    input.setValue("53453").pressEnter();
    input.shouldNotBe(visible); // wait for response
    $$(".value").get(1).shouldHave(text("53453"));

    $$(".glyphicon").get(1).click();
    input = $$(".modify-value").get(1);
    input.setValue("-4").pressEnter();
    input.shouldNotBe(visible); // wait for response
    $$(".value").get(1).shouldHave(text("-4"));
  }

  @Test
  public void deleteExistingMeasuredValue() throws Exception {
    open("/admin/values/value");

    $$(".glyphicon").get(0).click();
    SelenideElement input = $$(".modify-value").get(0);
    input.setValue("-4564.656").pressEnter();
    input.shouldNotBe(visible); // wait for response

    $$(".value").get(0).shouldHave(text("-4564.656"));

    $$(".glyphicon").get(0).click();
    input = $$(".modify-value").get(0);
    input.setValue("").pressEnter();
    input.shouldNotBe(visible); // wait for response

    $$(".value").get(0).shouldHave(text(""));
  }

  @Test
  public void deleteExistingForecastValue() throws Exception {
    open("/admin/values/value");

    $$(".glyphicon").get(3).click();
    SelenideElement input = $$(".modify-value").get(3);
    input.setValue("53453").pressEnter();
    input.shouldNotBe(visible); // wait for response
    $$(".value").get(3).shouldHave(text("53453"));

    $$(".glyphicon").get(3).click();
    input = $$(".modify-value").get(3);
    input.setValue("").pressEnter();
    input.shouldNotBe(visible); // wait for response
    $$(".value").get(3).shouldHave(text(""));
  }
}
