package ui;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import model.Goal;
import model.Metric;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertEquals;

public class HomeViewTest extends UITest {

  @Before
  public void setUp() throws Exception {
    Goal goal1 = new Goal("Eesmark1","", 10 ,1);
    Goal goal2 = new Goal("Eesmark2","", 12, 2);

    hibernate.save(goal1);
    hibernate.save(goal2);

    Metric metric = new Metric(goal1, "Moodik1", "", "", "", null, "", 23.0, "2014", "http:// statistikaamet http://www.neti.ee", "", 2.0, true);
    metric.setEngUnit("%");
    metric.setEngStartLevelComment("start comment");
    metric.setEngTargetLevelComment("target comment");
    hibernate.save(metric);
    hibernate.save(new Metric(goal1, "Moodik2", "EUR", "", "", 0.0, "", null, "", "", "", 10.0, false));
    hibernate.save(new Metric(goal2, "Moodik3", "", "", "", 15.0, "", null, "", "amet", "", 5.8, true));

    open("/home");

    $(".language-button-est").click();

  }

  @Test
  public void GoalAndBudgetWithCorrectMetrics() {
    open("/home");


    ElementsCollection goals = $$(".goal");
    goals.shouldHaveSize(2);

    SelenideElement goalBlock1 = goals.get(0);
    goalBlock1.$(".name").shouldHave(text("Eesmark1"));
    goalBlock1.$(".budget").shouldHave(text("10"));
    goalBlock1.$$(".metric").get(0).$(".startLevel").shouldHave(text("N/A"));
    goalBlock1.$$(".metric").get(0).$(".targetLevel").shouldHave(text("23 (2014)"));
    goalBlock1.$$(".metric").get(0).$(".infoSource").$(".glyphicon-new-window").shouldBe(visible);
    goalBlock1.$$(".metric").get(0).$(".infoSource").shouldHave(text("statistikaamet"));
    assertEquals("Moodik1", goalBlock1.$$(".metric").get(0).$(".name").getText());
    goalBlock1.$(".name").shouldNotHave(text("Moodik2 (EUR)"));



    SelenideElement goalBlock2 = goals.get(1);
    goalBlock2.$(".name").shouldHave(text("Eesmark2"));
    goalBlock2.$(".budget").shouldHave(text("12"));
    goalBlock2.$$(".metric").get(0).$(".targetLevel").shouldHave(text("N/A"));
    goalBlock2.$$(".metric").get(0).$(".startLevel").shouldHave(text("15"));
    goalBlock2.$$(".metric").get(0).$(".startLevel").shouldNotHave(text("("));
    goalBlock2.$$(".metric").get(0).$(".infoSource").shouldHave(text("amet"));
    goalBlock2.$$(".metric").get(0).$(".infoSource").$(".glyphicon-link").shouldNotBe(visible);

    assertEquals("Moodik3", goalBlock2.$$(".metric").get(0).$(".name").getText());
  }

  @Test
  public void goalsAreDisplayedInCorrectOrder() throws Exception {

    hibernate.save(new Goal("Eesmark5", "", 102, 5));
    hibernate.save(new Goal("Eesmark3", "", 103, 3));
    hibernate.save(new Goal("Eesmark4", "", 104, 4));

    open("/home");

    $$(".goal").get(0).$("h4").shouldHave(text("Eesmark1"));
    $$(".goal").get(1).$("h4").shouldHave(text("Eesmark2"));
    $$(".goal").get(2).$("h4").shouldHave(text("Eesmark3"));
    $$(".goal").get(3).$("h4").shouldHave(text("Eesmark4"));
    $$(".goal").get(4).$("h4").shouldHave(text("Eesmark5"));


  }

  @Test
  public void adminCanSwitchBetweenUserAndAdminView() throws Exception {

    hibernate.save(new User("johny", "p2s3w04d"));
    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

    $("#userViewButton").click();
    $("#adminViewButton").exists();

    $(".actions").shouldNotBe(visible);


    $("#adminViewButton").click();
    $("#userViewButton").exists();

    $(".actions").shouldBe(visible);

    $("#logout-button").click();

  }

  @Test
  public void userViewsThePageInEnglish() throws Exception {
    $(".language-button-eng").click();
    $$(".goal").get(0).$$(".metric").get(0).$(".startLevel").shouldHave(text("start comment"));
    $$(".goal").get(0).$$(".metric").get(0).$(".targetLevel").shouldHave(text("23 %\n (target comment)"));
  }
}

