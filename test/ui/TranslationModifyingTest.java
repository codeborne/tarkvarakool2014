package ui;

import framework.Messages;
import model.Goal;
import model.Metric;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TranslationModifyingTest extends UITest {

  Goal goal = new Goal("Sisestatud eesmark","Siin on kommentaar", 100, 1);


  @Before
  public void setUp() throws Exception {
    messages = new Messages(false).getResolverFor("et");

    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

    hibernate.save(goal);
    hibernate.save(new Metric(goal, "üks mõõdik", "inimest", "Loeb kokku mitu inimest on", "", 1.0, "2014", 0.0, "", "", "", 1.0, true));
  }

  @After
  public void tearDown() throws Exception {
    $("#logout-button").click();

  }

  @Test
  public void adminGoesToTranslationPage() throws Exception {

    open("/admin/goals/home");

    $$(".goal").get(0).$(".translationButton").click();
    $(".goalTable").$(".name").shouldHave(text("Sisestatud eesmark"));
    $(".goalTable").$(By.name("engName")).shouldBe(empty);
    $(".goalTable").$(".comment").shouldHave(text("Siin on kommentaar"));
    $(".goalTable").$(By.name("engComment")).shouldBe(empty);

    $(".metricTable").$(".name").shouldHave(text("üks mõõdik"));
    $(".metricTable").$(".unit").shouldHave(text("inimest"));
    $(".metricTable").$(".publicDescription").shouldHave(text("Loeb kokku mitu inimest on"));
    $(".metricTable").$(By.name("engMetricName")).shouldBe(empty);
    $(".metricTable").$(By.name("engUnit")).shouldBe(empty);
    $(".metricTable").$(By.name("engPublicDescription")).shouldBe(empty);
  }

  @Test
  public void adminTranslates() throws Exception {
    open("/admin/goals/home");
    $$(".goal").get(0).$(".translationButton").click();
    $(".goalTable").$(By.name("engName")).setValue("inserted goal");
    $(".goalTable").$(By.name("engComment")).setValue("This is a comment");
    $(".metricTable").$(By.name("engMetricName")).setValue("a metric");
    $(".metricTable").$(By.name("engUnit")).setValue("people");
    $("#saveTranslationButton").click();
    $(".goalTable").$(By.name("engName")).shouldHave(value("inserted goal"));
    $(".goalTable").$(By.name("engComment")).shouldHave(value("This is a comment"));
    $(".metricTable").$(By.name("engMetricName")).shouldHave(value("a metric"));
    $(".metricTable").$(By.name("engUnit")).shouldHave(value("people"));
    $(".metricTable").$(By.name("engPublicDescription")).shouldBe(empty);
  }

  @Test
  public void adminChangesTranslation() throws Exception {
    goal.setEngName("inserted goal");
    goal.setEngComment("comment");
    hibernate.update(goal);
    hibernate.flush();

    open("/admin/goals/home");
    $$(".goal").get(0).$(".translationButton").click();
    $(".goalTable").$(By.name("engName")).shouldHave(value("inserted goal"));
    $(".goalTable").$(By.name("engComment")).shouldHave(value("comment"));
    $(".goalTable").$(By.name("engName")).setValue("added goal");
    $(".goalTable").$(By.name("engComment")).setValue("");
    $("#saveTranslationButton").click();
    $(".goalTable").$(By.name("engName")).shouldHave(value("added goal"));
    $(".goalTable").$(By.name("engComment")).shouldHave(value(""));



  }
}

