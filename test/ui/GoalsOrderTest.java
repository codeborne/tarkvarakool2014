package ui;

import com.codeborne.selenide.SelenideElement;
import model.Goal;
import model.Metric;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class GoalsOrderTest extends UITest {

  @Before
  public void setUp() throws Exception {
    hibernate.save(new User("johny", "p2s3w04d"));

    open("/admin/login");

    $(By.name("username")).setValue("johny");
    $(By.name("password")).setValue("p2s3w04d");

    $("#submit").click();

    Goal goal = new Goal("Eesmark1", "", 101, 1);
    hibernate.save(goal);
    hibernate.save(new Goal("Eesmark5", "", 102, 5));
    hibernate.save(new Goal("Eesmark3", "", 103, 3));
    hibernate.save(new Goal("Eesmark4", "", 104, 4));
    hibernate.save(new Goal("Eesmark2", "", 105, 2));
    hibernate.save(new Metric(goal,"Metric1", "m", "","", 100,"", 1,"","","",1.0));
    open("/admin/goals/home");

  }

  @After
  public void tearDown() throws Exception {
    $("#logout-button").click();
  }

  @Test
  public void goalsAreDisplayedInCorrectOrder() throws Exception {

    $$(".goal").get(0).$(".nameInTable").shouldHave(text("Eesmark1"));
    $$(".goal").get(1).$(".nameInTable").shouldHave(text("Eesmark2"));
    $$(".goal").get(2).$(".nameInTable").shouldHave(text("Eesmark3"));
    $$(".goal").get(3).$(".nameInTable").shouldHave(text("Eesmark4"));
    $$(".goal").get(4).$(".nameInTable").shouldHave(text("Eesmark5"));

  }

  @Test @Ignore
  public void adminMovesElementToFirstPosition() throws Exception {

    $$(".goal").get(2).$(".glyphicon-sort").dragAndDropTo("#sortableGoals tr:first-child");

    $$(".goal").get(0).$(".nameInTable").shouldHave(text("Eesmark3"));
    $$(".goal").get(0).$(".budgetInTable").shouldHave(text("103"));

    open("/admin/goals/home");

    $$(".goal").get(0).$(".nameInTable").shouldHave(text("Eesmark3"));
    $$(".goal").get(0).$(".budgetInTable").shouldHave(text("103"));
  }

  @Test
  public void goalsAreDisplayedInCorrectOrderInAdminValuesView() throws Exception {
    open("/admin/values/value");

    $$(".goal").get(0).$("h4").shouldHave(text("Eesmark1"));
    $$(".goal").get(1).$("h4").shouldHave(text("Eesmark2"));
    $$(".goal").get(2).$("h4").shouldHave(text("Eesmark3"));
    $$(".goal").get(3).$("h4").shouldHave(text("Eesmark4"));
    $$(".goal").get(4).$("h4").shouldHave(text("Eesmark5"));

  }

  @Test @Ignore
  public void adminMovesElementInTheMiddle() throws Exception {
    $$(".goal").get(3).$(".glyphicon-sort").dragAndDropTo("#sortableGoals tr:nth-child(2) ");


    $$(".goal").get(0).$(".nameInTable").shouldHave(text("Eesmark1"));
    $$(".goal").get(1).$(".nameInTable").shouldHave(text("Eesmark4"));
    $$(".goal").get(2).$(".nameInTable").shouldHave(text("Eesmark2"));
    $$(".goal").get(3).$(".nameInTable").shouldHave(text("Eesmark3"));
    $$(".goal").get(4).$(".nameInTable").shouldHave(text("Eesmark5"));

  }

  @Test @Ignore
  public void adminTriesToMoveElementToLastRow() throws Exception {
    $$(".goal").get(1).$(".glyphicon-sort").dragAndDropTo("#sortableGoals tr:last-child");

    $$(".goal").get(0).$(".nameInTable").shouldHave(text("Eesmark1"));
    $$(".goal").get(1).$(".nameInTable").shouldHave(text("Eesmark2"));
    $$(".goal").get(2).$(".nameInTable").shouldHave(text("Eesmark3"));
    $$(".goal").get(3).$(".nameInTable").shouldHave(text("Eesmark4"));
    $$(".goal").get(4).$(".nameInTable").shouldHave(text("Eesmark5"));

  }

  @Test @Ignore
  public void adminMovesElementToLastPosition() throws Exception {
    SelenideElement source = $$(".goal").get(1).$(".glyphicon-sort");
    SelenideElement target = $("#sortableGoals tr:nth-child(5)");

    slowDragAndDrop(source, target);
    $$("#sortableGoals tr");

    $$(".goal").get(0).$(".nameInTable").shouldHave(text("Eesmark1"));
    $$(".goal").get(1).$(".nameInTable").shouldHave(text("Eesmark3"));
    $$(".goal").get(2).$(".nameInTable").shouldHave(text("Eesmark4"));
    $$(".goal").get(3).$(".nameInTable").shouldHave(text("Eesmark5"));
    $$(".goal").get(4).$(".nameInTable").shouldHave(text("Eesmark2"));

  }

  private void slowDragAndDrop(SelenideElement source, SelenideElement target) {
    Actions actions = actions().clickAndHold(source);
    int numberOfMovements = Math.abs(target.getLocation().getY() - source.getLocation().getY());
    for (int i = 0; i < numberOfMovements; i++) {
      actions = actions.moveByOffset(0, 1);
    }
    actions.release().build().perform();
  }
}
