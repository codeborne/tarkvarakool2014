package ui;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import model.Goal;
import model.Metric;
import org.junit.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertEquals;

public class HomeViewTest extends UITest {


  @Test
  public void GoalAndBudgetWithCorrectMetrics() {

    Goal goal1 = new Goal("Eesmark1","", 10 ,1);
    Goal goal2 = new Goal("Eesmark2","", 12, 2);

    hibernate.save(goal1);
    hibernate.save(goal2);

    hibernate.save(new Metric(goal1, "Moodik1", "", "", "", 0, "", 0, "", "", "", 2.0));
    hibernate.save(new Metric(goal1, "Moodik2", "EUR", "", "", 0, "", 0, "", "", "", 10.0));
    hibernate.save(new Metric(goal2, "Moodik3", "", "", "", 0, "", 0, "", "", "", 5.8));

    open("/home");

    ElementsCollection goals = $$(".goal");
    goals.shouldHaveSize(2);

    SelenideElement goalBlock1 = goals.get(0);
    goalBlock1.$(".name").shouldHave(text("Eesmark1"));
    goalBlock1.$(".budget").shouldHave(text("10"));
    assertEquals("Moodik1", goalBlock1.$$(".metric").get(0).$(".name").getText());
    assertEquals("Moodik2 (EUR)", goalBlock1.$$(".metric").get(1).$(".name").getText());


    SelenideElement goalBlock2 = goals.get(1);
    goalBlock2.$(".name").shouldHave(text("Eesmark2"));
    goalBlock2.$(".budget").shouldHave(text("12"));
    assertEquals("Moodik3", goalBlock2.$$(".metric").get(0).$(".name").getText());
  }

  @Test
  public void goalsAreDisplayedInCorrectOrder() throws Exception {
    hibernate.save(new Goal("Eesmark1", "", 101, 1));
    hibernate.save(new Goal("Eesmark5", "", 102, 5));
    hibernate.save(new Goal("Eesmark3", "", 103, 3));
    hibernate.save(new Goal("Eesmark4", "", 104, 4));
    hibernate.save(new Goal("Eesmark2", "", 105, 2));
    open("/home");

    $$(".goal").get(0).$("h4").shouldHave(text("Eesmark1"));
    $$(".goal").get(1).$("h4").shouldHave(text("Eesmark2"));
    $$(".goal").get(2).$("h4").shouldHave(text("Eesmark3"));
    $$(".goal").get(3).$("h4").shouldHave(text("Eesmark4"));
    $$(".goal").get(4).$("h4").shouldHave(text("Eesmark5"));


  }
}

