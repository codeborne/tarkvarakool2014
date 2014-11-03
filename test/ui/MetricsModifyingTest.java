package ui;

import model.Goal;
import model.Metric;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertEquals;

public class MetricsModifyingTest extends UITest {

  Goal goal = new Goal("Sisestatud eesmark","", 100,1);

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

    hibernate.save(goal);
    hibernate.save(new Metric(goal, "Some metric", "%", "abc", "def", 10, "ghi", 10, "jkl", "mno", "pqr", -5.5, true));
    hibernate.save(new Metric(goal, "Some metric1", "", "", "", 0, "", 0, "", "", "", 5.0, true));

    open("/admin/goals/home");

    $$(".metricsButton").get(0).click();
    $$(".modifyButton").get(0).click();
  }

  @After
  public void after() {
    $("#logout-button").click();
  }

  @Test
  public void adminClicksOnModifyButton() {
    $(".name").$("input").shouldHave(value("Some metric"));
    $(".publicDescription").$("input").shouldHave(value("abc"));
  }

  @Test
  public void adminModifiesMetric(){
    $(".name").$("input").shouldHave(value("Some metric"));
    $(".unit").$("input").shouldHave(value("%"));
    $(".publicDescription").$("input").shouldHave(value("abc"));
    $(".privateDescription").$("input").shouldHave(value("def"));
    $(".startLevel").$("input").shouldHave(value("10"));
    $(".commentOnStartLevel").$("input").shouldHave(value("ghi"));
    $(".targetLevel").$("input").shouldHave(value("10"));
    $(".commentOnTargetLevel").$("input").shouldHave(value("jkl"));
    $(".infoSource").$("input").shouldHave(value("mno"));
    $(".institutionToReport").$("input").shouldHave(value("pqr"));


    $(".name").$("input").setValue("Metric");
    $(".unit").$("input").setValue("EUR");

    $(".saveGoalButton").click();

    assertEquals("Metric", $$("tr.metric").get(0).$(".name").getText());
    assertEquals("EUR", $$("tr.metric").get(0).$(".unit").getText());
  }
}


