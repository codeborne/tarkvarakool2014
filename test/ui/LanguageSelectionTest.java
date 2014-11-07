package ui;

import model.Goal;
import model.Metric;
import org.junit.Before;
import org.junit.Test;

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

    hibernate.save(new Metric(goal1, "Mood1", "", "", "", 100, "", 150, "", "", "", 1.0, true));
    Metric metric = new Metric(goal1, "Mood2", "$", "", "", 200, "", 220, "", "", "", 2.0, true);
    metric.setEngName("metr1");
    metric.setEngUnit("$");
    hibernate.save(metric);

    open("/admin/login");


  }
  @Test
  public void languageButtonEst(){

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
  public void languageButtonEng(){

    open("/home");

    $(".language-button-eng").click();

    $$(".goal").get(0).$("h4").shouldHave(text("Goal1"));
    $$(".goal").get(1).$("h4").shouldHave(text("Goal2"));
    $$(".goal").get(0).$$(".metric").get(1).$(".name").shouldHave(text("metr1 ($)"));

    $(".title").shouldHave(text("Metrics of MKM"));


  }
}
