package ui;

import framework.Messages;
import model.Goal;
import model.Metric;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TranslationModifyingTest extends UITest {

  Goal goal = new Goal("Sisestatud eesmark","Siin on kommentaar", 100, 1);


  @Before
  public void setUp() throws Exception {
    messages = new Messages(false).getResolverFor("et");

    hibernate.save(new User("johny", "p2s3w04d"));

    open("/home");

    $(".language-button-est").click();

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
    $("#translationTable").$(".name").shouldHave(text("Sisestatud eesmark"));
    $("#translationTable").$(By.name("engName")).shouldBe(empty);
    $("#translationTable").$(".comment").shouldHave(text("Siin on kommentaar"));
    $("#translationTable").$(By.name("engComment")).shouldBe(empty);

    $("#translationTable").$(".metric-name").shouldHave(text("üks mõõdik"));
    $("#translationTable").$(".unit").shouldHave(text("inimest"));
    $("#translationTable").$(".publicDescription").shouldHave(text("Loeb kokku mitu inimest on"));
    $("#translationTable").$(By.name("engMetricName")).shouldBe(empty);
    $("#translationTable").$(By.name("engUnit")).shouldBe(empty);
    $("#translationTable").$(By.name("engPublicDescription")).shouldBe(empty);
  }

  @Test
  public void adminTranslates() throws Exception {
    open("/admin/goals/home");
    $$(".goal").get(0).$(".translationButton").click();
    $("#translationTable").$(By.name("engName")).setValue("inserted goal");
    $("#translationTable").$(By.name("engComment")).setValue("This is a comment");
    $("#translationTable").$(By.name("engMetricName")).setValue("a metric");
    $("#translationTable").$(By.name("engUnit")).setValue("people");
    $("#saveTranslationButton").click();
    $(".alert-success").shouldHave(text("Salvestamine õnnestus"));
      $$(".goal").get(0).$(".translationButton").click();
    $("#translationTable").$(By.name("engName")).shouldHave(value("inserted goal"));
    $("#translationTable").$(By.name("engComment")).shouldHave(value("This is a comment"));
    $("#translationTable").$(By.name("engMetricName")).shouldHave(value("a metric"));
    $("#translationTable").$(By.name("engUnit")).shouldHave(value("people"));
    $("#translationTable").$(By.name("engPublicDescription")).shouldBe(empty);

  }

  @Test
  public void adminChangesTranslation() throws Exception {
    goal.setEngName("inserted goal");
    goal.setEngComment("comment");
    hibernate.update(goal);
    hibernate.flush();

    open("/admin/goals/home");
    $$(".goal").get(0).$(".translationButton").click();
    $("#translationTable").$(By.name("engName")).shouldHave(value("inserted goal"));
    $("#translationTable").$(By.name("engComment")).shouldHave(value("comment"));
    $("#translationTable").$(By.name("engName")).setValue("added goal");
    $("#translationTable").$(By.name("engComment")).setValue("");
    $("#saveTranslationButton").click();
    $(".alert-success").shouldHave(text("Salvestamine õnnestus"));
    $$(".goal").get(0).$(".translationButton").click();
    $("#translationTable").$(By.name("engName")).shouldHave(value("added goal"));
    $("#translationTable").$(By.name("engComment")).shouldHave(value(""));
  }

  @Test
  public void adminTranslatesEverythingAndGlobeTurnsGreen() throws Exception {
    open("/admin/goals/home");
    $$(".goal").get(0).$("span.glyphicon-globe").shouldHave(cssClass("redValue"));
    $$(".goal").get(0).$(".translationButton").click();
    $("#translationTable").$(By.name("engName")).setValue("inserted goal");
    $("#translationTable").$(By.name("engComment")).setValue("This is a comment");
    $("#translationTable").$(By.name("engMetricName")).setValue("a metric");
    $("#translationTable").$(By.name("engUnit")).setValue("people");
    $("#translationTable").$(By.name("engPublicDescription")).setValue("describe something");
    $("#saveTranslationButton").click();
    $$(".goal").get(0).$("span.glyphicon-globe").shouldHave(cssClass("greenValue"));
    $$(".goal").get(0).$(".translationButton").click();
    $("#translationTable").$(By.name("engComment")).setValue("");
    $("#saveTranslationButton").click();
    $$(".goal").get(0).$("span.glyphicon-globe").shouldHave(cssClass("redValue"));
  }
}

