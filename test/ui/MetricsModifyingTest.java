package ui;

import model.Goal;
import model.Metric;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertEquals;

public class MetricsModifyingTest extends UITest {

  Goal goal = new Goal("Sisestatud eesmark", 100);

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

    hibernate.save(goal);
    hibernate.save(new Metric(goal, "Some metric", "%", "abc", "def", 10, "ghi", 10, "jkl", "mno", "pqr", -5.5));
    hibernate.save(new Metric(goal, "Some metric1", "", "", "", 0, "", 0, "", "", "", 5.0));

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
    $(By.name("name")).shouldHave(text("Some metric"));
    $(By.name("publicDescription")).shouldHave(text(""));
  }

  @Test
  public void adminModifiesMetric(){
    $(By.name("name")).shouldHave(text("Some metric"));
    $(By.name("unit")).shouldHave(text("%"));
    $(By.name("publicDescription")).shouldHave(text("abc"));
    $(By.name("privateDescription")).shouldHave(text("def"));
    $(By.name("startLevel")).shouldHave(value("10"));
    $(By.name("commentOnStartLevel")).shouldHave(text("ghi"));
    $(By.name("targetLevel")).shouldHave(value("10"));
    $(By.name("commentOnTargetLevel")).shouldHave(text("jkl"));
    $(By.name("infoSource")).shouldHave(value("mno"));
    $(By.name("institutionToReport")).shouldHave(value("pqr"));

    $(By.name("name")).setValue("Metric");
    $(By.name("unit")).setValue("EUR");

    $(".submitButton").click();

    assertEquals("Metric (EUR)", $$("tr.metric").get(0).$(".name").getText());
  }
}


