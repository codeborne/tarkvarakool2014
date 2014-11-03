package ui;

import com.codeborne.selenide.SelenideElement;
import model.Goal;
import model.Metric;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class YearlyBudgetsModifyingTest extends UITest {

  Goal goal = new Goal("Sisestatud eesmark","", 100,1);
  Goal goal2 = new Goal("eesmark","" ,10, 2);

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();


    hibernate.save(goal);
    hibernate.save(goal2);
    hibernate.save(new Metric(goal, "Some metric", "", "", "", 0, "", 0, "", "", "", 1.0, true));
    hibernate.save(new Metric(goal, "Another metric", "", "erarara", "", 34363, "", 0, "", "", "", 2.0, true));
    hibernate.save(new Metric(goal2, "metric", "", "", "", 23, "", 45, "", "", "", 1.0, true));
  }

  @After
  public void after() {
    $("#logout-button").click();
  }

  @Test
  public void successModifyingEmptyYearlyBudget() throws Exception {
    open("/admin/values/value");
    $$(".goal").get(0).$(".yearlyBudget").$$(".glyphicon").get(0).click();
    SelenideElement input = $$(".goal").get(0).$(".yearlyBudget").$$(".modify-value").get(0);
    input.setValue("4382").pressEnter();

    $$(".goal").get(1).$(".yearlyBudget").$$(".glyphicon").get(6).click();
    SelenideElement input1 = $$(".goal").get(1).$(".yearlyBudget").$$(".modify-value").get(6);
    input1.setValue("3").pressEnter();

    input.shouldNotBe(visible);
    input1.shouldNotBe(visible);
    $$(".goal").get(0).$(".yearlyBudget").$$(".value").get(0).shouldHave(text("4382"));
    $$(".goal").get(1).$(".yearlyBudget").$$(".value").get(6).shouldHave(text("3"));
  }

  @Test
  public void successModifyingExistingYearlyBudget() throws Exception {
    Map<Integer, Long> yearlyBudgets = new HashMap<>();
    yearlyBudgets.put(2016, 328746L);
    goal.setYearlyBudgets(yearlyBudgets);
    hibernate.update(goal);
    hibernate.flush();
    open("/admin/values/value");
    $$(".goal").get(0).$(".yearlyBudget").$$(".glyphicon").get(2).click();
    SelenideElement input = $$(".goal").get(0).$(".yearlyBudget").$$(".modify-value").get(2);
    input.setValue("11").pressEnter();

    input.shouldNotBe(visible);
    $$(".goal").get(0).$(".yearlyBudget").$$(".value").get(2).shouldHave(text("11"));
  }

  @Test
  public void successDeletingExistingYearlyBudget() throws Exception {
    Map<Integer, Long> yearlyBudgets = new HashMap<>();
    yearlyBudgets.put(2018, 555555L);
    goal.setYearlyBudgets(yearlyBudgets);
    hibernate.update(goal);
    hibernate.flush();
    open("/admin/values/value");
    $$(".goal").get(0).$(".yearlyBudget").$$(".glyphicon").get(4).click();
    SelenideElement input = $$(".goal").get(0).$(".yearlyBudget").$$(".modify-value").get(4);
    input.setValue("").pressEnter();

    input.shouldNotBe(visible);
    $$(".goal").get(0).$(".yearlyBudget").$$(".value").get(4).shouldHave(text(""));
  }

  @Test
  public void failureModifyingEmptyYearlyBudget() throws Exception {
    open("/admin/values/value");
    $$(".goal").get(0).$(".yearlyBudget").$$(".glyphicon").get(0).click();
    SelenideElement input = $$(".goal").get(0).$(".yearlyBudget").$$(".modify-value").get(0);
    input.setValue("-1").pressEnter();

    confirm("Sisestage korrektne väärtus.");
    input.shouldBe(visible);
    input.shouldHave(value("-1"));
    open("/admin/values/value");
    $$(".goal").get(0).$(".yearlyBudget").$$(".value").get(0).shouldHave(text(""));
  }

  @Test
  public void failureModifyingExistingYearlyBudget() throws Exception {
    Map<Integer, Long> yearlyBudgets = new HashMap<>();
    yearlyBudgets.put(2018, 555555L);
    goal.setYearlyBudgets(yearlyBudgets);
    hibernate.update(goal);
    hibernate.flush();

    open("/admin/values/value");
    $$(".goal").get(0).$(".yearlyBudget").$$(".glyphicon").get(4).click();
    SelenideElement input = $$(".goal").get(0).$(".yearlyBudget").$$(".modify-value").get(4);
    input.setValue("-5").pressEnter();
    confirm("Sisestage korrektne väärtus.");
    input.shouldBe(visible);

    input.shouldHave(value("-5"));
    open("/admin/values/value");
    $$(".goal").get(0).$(".yearlyBudget").$$(".value").get(4).shouldHave(text("555555"));
  }

  @Test
  public void clickingOnPencilClosesPreviousInputFieldAndSaves() throws Exception {
    open("/admin/values/value");
    $$(".goal").get(0).$(".yearlyBudget").$$(".glyphicon").get(0).click();
    SelenideElement input = $$(".goal").get(0).$(".yearlyBudget").$$(".modify-value").get(0);
    input.setValue("4382");
    $$(".goal").get(1).$(".yearlyBudget").$$(".glyphicon").get(3).click();

    input.shouldNotBe(visible);
    $$(".goal").get(0).$(".yearlyBudget").$$(".value").get(0).shouldHave(text("4382"));
    $$(".goal").get(1).$(".yearlyBudget").$$(".modify-value").get(3).shouldBe(visible);
  }

}