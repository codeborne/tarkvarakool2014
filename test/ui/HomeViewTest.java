package ui;


import model.Goal;
import model.Metric;

import org.junit.Test;


import static com.codeborne.selenide.Condition.text;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class HomeViewTest extends UITest {

  @Test
  public void MetricsInTheCorrectOrder() {

    Goal goal1 = new Goal("Eesmark1", 145);

    hibernate.save(goal1);

    hibernate.save(new Metric(goal1, "a", "", "", 0, "", 0, "", "", ""));
    hibernate.save(new Metric(goal1, "b", "", "", 0, "", 0, "", "", ""));

    open("/home");

    $$("tr.metric").get(0).$(".name").shouldHave(text("a"));
    $$("tr.metric").get(1).$(".name").shouldHave(text("b"));


  }

  @Test
  public void GoalAndBudgetOnHomePage() {
    hibernate.save(new Goal("Eesmark", 124));

    open("/home");

    $(".nameInTable").shouldHave(text("Eesmark"));
    $(".budgetInTable").shouldHave(text("124"));

  }

  @Test
  public void GoalAndBudgetWithCorrectMetrics() {

    Goal goal1 = new Goal("Eesmark1", 10);
    Goal goal2 = new Goal("Eesmark2", 12);

    hibernate.save(goal1);
    hibernate.save(goal2);

    hibernate.save(new Metric(goal1, "Moodik1", "", "", 0, "", 0, "", "", ""));
    hibernate.save(new Metric(goal1, "Moodik2", "", "", 0, "", 0, "", "", ""));
    hibernate.save(new Metric(goal2, "Moodik3", "", "", 0, "", 0, "", "", ""));

    open("/home");

    $$(".nameInTable").get(0).shouldHave(text("Eesmark1"));
    $$(".budgetInTable").get(0).shouldHave(text("10"));
    $$(".nameInTable").get(1).shouldHave(text("Eesmark2"));
    $$(".budgetInTable").get(1).shouldHave(text("12"));

    $$(".table").get(0).$$(".metric").get(0).$(".name").shouldHave(text("Moodik1"));
    $$(".table").get(0).$$(".metric").get(1).$(".name").shouldHave(text("Moodik2"));
    $$(".table").get(1).$$(".metric").get(0).$(".name").shouldHave(text("Moodik3"));

  }
}

