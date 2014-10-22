package ui;

import com.codeborne.selenide.SelenideElement;
import model.Goal;
import model.Metric;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MetricsTest extends UITest {

  @Before
  public void logIn() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();
  }

  @After
  public void tearDown() throws Exception {
    $("#logout-button").click();
  }

  @Test
  public void submitFailsWithoutMetricName() throws Exception {
    hibernate.save(new Goal("Tere", 124));
    open("/admin/goals/home");

    $$(".metricsButton").get(0).click();
    $("#addMetricButton").click();


    $("#submitButton").click();

    $(".alert-danger").shouldHave(text("Sisestage mõõdik."));
    $("h3").shouldHave(text("Lisage mõõdik: Tere"));
  }

  @Test
  public void submitSuccessWithMetricName() throws Exception {
    hibernate.save(new Goal("Teere", 124));
    open("/admin/goals/home");

    $$(".metricsButton").get(0).click();
    $("#addMetricButton").click();


    $(By.name("name")).setValue("Koer");
    $("#submitButton").click();
    $$("tr.metric").get(0).$(".name").shouldHave(text("Koer"));
  }

  @Test
  public void adminCancelsAddingMetric() throws Exception {
    hibernate.save(new Goal("Teere", 124));
    open("/admin/goals/home");

    $$(".metricsButton").get(0).click();

    $("#goBackButton").click();
    SelenideElement row = $$("tr.goal").get(0);

    row.$(".nameInTable").shouldHave(text("Teere"));

  }

  @Test
  public void failIfAdminAddsDuplicateMetric() throws Exception {

    Goal goal1 = new Goal("Kool", 145);
    hibernate.save(goal1);
    hibernate.save(new Metric(goal1, "muki", "", "", 0, "", 0, "", "", ""));
    open("/admin/goals/home");

    $$(".metricsButton").get(0).click();
    $("#addMetricButton").click();
    $(By.name("name")).setValue("muki");
    $("#submitButton").click();
    $(".alert-danger").shouldHave(text("See mõõdik on juba sisestatud."));

  }

  @Test
  public void successIfAllFieldsHaveValue() throws Exception {
    hibernate.save(new Goal("Teere", 124));
    open("/admin/goals/home");

    $$(".metricsButton").get(0).click();
    $("#addMetricButton").click();

    $(By.name("name")).setValue("Koer");
    $(By.name("publicDescription")).setValue("trallalaa");
    $(By.name("privateDescription")).setValue("trullalaa");
    $(By.name("startLevel")).setValue("435");
    $(By.name("commentOnStartLevel")).setValue("blahblah");
    $(By.name("targetLevel")).setValue("1");
    $(By.name("commentOnTargetLevel")).setValue("iejoja");
    $(By.name("infoSource")).setValue("ioejoia");
    $(By.name("institutionToReport")).setValue("koht");

    $("#submitButton").click();

    $$("tr.metric").shouldHaveSize(1);

    SelenideElement row = $$("tr.metric").get(0);

    row.$(".name").shouldHave(text("Koer"));
    row.$(".publicDescription").shouldHave(text("trallalaa"));
    row.$(".privateDescription").shouldHave(text("trullalaa"));
    row.$(".startLevel").shouldHave(text("435"));
    row.$(".commantOnStartLevel").shouldHave(text("blahblah"));
    row.$(".targetLevel").shouldHave(text("1"));
    row.$(".commentOnTargetLevel").shouldHave(text("iejoja"));
    row.$(".infoSource").shouldHave(text("ioejoia"));
    row.$(".institutionToReport").shouldHave(text("koht"));
  }

  @Test
  public void ifGoalNameIsAddedToTheMetricsTable() throws Exception {
    hibernate.save(new Goal("Tere", 124));
    open("/admin/goals/home");

    $$(".metricsButton").get(0).click();
    $("h3").shouldHave(text("Tere"));
  }

}
