package ui;

import com.codeborne.selenide.SelenideElement;
import model.Goal;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertEquals;

public class MetricsAddingTest extends UITest {

  @Before
  public void logIn() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();
    hibernate.save(new Goal("Tere","", 124,1));
    open("/admin/goals/home");
  }

  @After
  public void tearDown() throws Exception {
    $("#logout-button").click();
  }

  @Test
  public void submitFailsWithoutMetricName() throws Exception {
    $$(".metricsButton").get(0).click();

    $(".saveGoalButton").click();

    $(".alert-danger").shouldHave(text(messages.get("errorInsertMetric")));

  }

  @Test
  public void submitSuccessWithMetricName() throws Exception {
    $$(".metricsButton").get(0).click();

    $(".addMetric").$(By.name("name")).setValue("Koer");
    $(".saveGoalButton").click();

    assertEquals("Koer", $$("tr.metric").get(0).$(".name").getText());
  }
  @Test
  public void submitSuccessWithLevelsWithCommas() throws Exception {
    $$(".metricsButton").get(0).click();

    $(".addMetric").$(By.name("name")).setValue("Koer");
    $(".addMetric").$(By.name("startLevel")).setValue("1,5");
    $(".addMetric").$(By.name("targetLevel")).setValue("8,5");
    $(".saveGoalButton").click();

    assertEquals("Koer", $$("tr.metric").get(0).$(".name").getText());
    assertEquals("1,5", $$("tr.metric").get(0).$(".startLevel").getText());
    assertEquals("8,5", $$("tr.metric").get(0).$(".targetLevel").getText());
  }

  @Test
  public void submitFailsWithIncorrectStartLevelAndTargetLevel() throws Exception {
    $$(".metricsButton").get(0).click();
    $(".addMetric").$(By.name("name")).setValue("Koer");
    $(".addMetric").$(By.name("startLevel")).setValue("5%");
    $(".saveGoalButton").click();

    $(".alert-danger").shouldHave(text(messages.get("errorStartLevel")));

    $(".addMetric").$(By.name("startLevel")).setValue("5");
    $(".addMetric").$(By.name("targetLevel")).setValue("abc");
    $(".saveGoalButton").click();

    $(".alert-danger").shouldHave(text(messages.get("errorTargetLevel")));
  }

  @Test
  public void successIfAllFieldsHaveValue() throws Exception {
    $$(".metricsButton").get(0).click();

    $(".addMetric").$(By.name("name")).setValue("Koer");
    $(".addMetric").$(By.name("unit")).setValue("%");
    $(".addMetric").$(By.name("publicDescription")).setValue("trallalaa");
    $(".addMetric").$(By.name("privateDescription")).setValue("trullalaa");
    $(".addMetric").$(By.name("startLevel")).setValue("435");
    $(".addMetric").$(By.name("commentOnStartLevel")).setValue("blahblah");
    $(".addMetric").$(By.name("targetLevel")).setValue("1");
    $(".addMetric").$(By.name("commentOnTargetLevel")).setValue("iejoja");
    $(".addMetric").$(By.name("infoSource")).setValue("http://");
    $(".addMetric").$(By.name("institutionToReport")).setValue("koht");

    $(".saveGoalButton").click();

    $$("tr.metric").shouldHaveSize(1);

    SelenideElement row = $$("tr.metric").get(0);

    row.$(".name").shouldHave(text("Koer"));
    row.$(".unit").shouldHave(text("%"));
    row.$(".publicDescription").shouldHave(text("trallalaa"));
    row.$(".privateDescription").shouldHave(text("trullalaa"));
    row.$(".startLevel").shouldHave(text("435"));
    row.$(".commentOnStartLevel").shouldHave(text("blahblah"));
    row.$(".targetLevel").shouldHave(text("1"));
    row.$(".commentOnTargetLevel").shouldHave(text("iejoja"));
    row.$(".infoSource").shouldHave(text("http://"));
    row.$(".infoSource").$("a").shouldBe(visible);
    row.$(".institutionToReport").shouldHave(text("koht"));


  }

  @Test
  public void goalNameIsShownInTheMetricsTable() throws Exception {
    $$(".metricsButton").get(0).click();
    $("h4").shouldHave(text("Tere"));
  }

}
