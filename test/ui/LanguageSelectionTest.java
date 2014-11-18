package ui;

import model.Goal;
import model.Metric;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LanguageSelectionTest extends UITest {


  @Before
  public void setUp() {
    Goal goal1 = new Goal("Eesmark1", "", 145, 1);
    goal1.setEngName("Goal1");
    Goal goal2 = new Goal("Eesmark2", "", 120, 2);
    goal2.setEngName("Goal2");

    hibernate.save(goal1);
    hibernate.save(goal2);

    hibernate.save(new Metric(goal1, "Mood1", "", "", "", 100.0, "", 150.0, "", "", "", 1.0, true));
    Metric metric = new Metric(goal1, "Mood2", "$", "", "", 200.0, "", 220.0, "", "", "", 2.0, true);
    metric.setEngName("metr1");
    metric.setEngUnit("$");
    hibernate.save(metric);

    open("/admin/login");


  }

  @Test
  public void languageButtonEst() {

    open("/home");

    $(".language-button-est").click();

    $$(".goal").get(0).$("h4").shouldHave(text("Eesmark1"));

    $(".title").shouldHave(text("Infoühiskonna arendamise mõõdikud"));


    $$(".switch-button").get(1).click();

    $(".table").shouldHave(text("Mõõdik"));
    $(".table").shouldHave(text("Algtase"));
    $(".table").shouldHave(text("Sihttase"));
  }

  @Test
  public void languageButtonEng() {

    open("/home");

    $(".language-button-eng").click();

    $$(".goal").get(0).$("h4").shouldHave(text("Goal1"));
    $$(".goal").get(1).$("h4").shouldHave(text("Goal2"));
    $$(".goal").get(0).$$(".metric").get(1).$(".name").shouldHave(text("metr1"));

    $(".title").shouldHave(text("Metrics of MKM"));


  }

  @Test
  public void adminSwitchLanguageInUserView() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();
    $("#userViewButton").click();
    $(".language-button-eng").click();

    $(".title").shouldHave(text("Metrics of MKM"));

    $("#adminViewButton").click();

    $(".title").shouldHave(text("Infoühiskonna arendamise mõõdikud"));
    $("#logout-button").click();
  }
}
