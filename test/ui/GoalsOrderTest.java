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

import static com.codeborne.selenide.CollectionCondition.texts;
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
    hibernate.save(new Metric(goal,"Metric1", "m", "","", 100,"", 1,"","","",1.0, true));
    open("/admin/goals/home");

  }

  @After
  public void tearDown() {
    $("#logout-button").click();
  }

  @Test
  public void goalsAreDisplayedInCorrectOrder() {
    $$(".goal .nameInTable").shouldHave(
      texts("Eesmark1", "Eesmark2", "Eesmark3", "Eesmark4", "Eesmark5"));
  }

  @Test
  public void adminMovesElementToFirstPosition() {
    dragAndDrop($$(".goal").get(2).$(".glyphicon-sort"), -2);

    $$(".goal").get(0).$(".nameInTable").shouldHave(text("Eesmark3"));
    $$(".goal").get(0).$(".budgetInTable").shouldHave(text("103"));

    open("/admin/goals/home");

    $$(".goal").get(0).$(".nameInTable").shouldHave(text("Eesmark3"));
    $$(".goal").get(0).$(".budgetInTable").shouldHave(text("103"));
  }

  @Test
  public void goalsAreDisplayedInCorrectOrderInAdminValuesView() {
    open("/admin/values/value");
    $$(".goal h4").shouldHave(
      texts("Eesmark1", "Eesmark2", "Eesmark3", "Eesmark4", "Eesmark5"));
  }

  @Test
  public void adminMovesElementInTheMiddle() {
    dragAndDrop($$(".goal").get(3).$(".glyphicon-sort"), -2);

    $$(".goal .nameInTable").shouldHave(texts("Eesmark1", "Eesmark4", "Eesmark2", "Eesmark3", "Eesmark5"));
  }

  @Test
  public void adminTriesToMoveElementToLastRow() {
    dragAndDrop($$(".goal").get(1).$(".glyphicon-sort"), +5);
    
    $$(".goal .nameInTable").shouldHave(
      texts("Eesmark1", "Eesmark2", "Eesmark3", "Eesmark4", "Eesmark5"));
  }

  @Test @Ignore
  public void adminMovesElementToLastPosition() {
    dragAndDrop($$(".goal").get(1).$(".glyphicon-sort"), +3);
    $$(".goal .nameInTable").shouldHave(texts("Eesmark1", "Eesmark3", "Eesmark4", "Eesmark5", "Eesmark2"));
  }

  private void dragAndDrop(SelenideElement draggable, int shiftByRows) {
    SelenideElement row = draggable.closest("tr");
    
    Actions actions = actions().clickAndHold(draggable.toWebElement());
    if (shiftByRows > 0) {
      actions.moveByOffset(0, shiftByRows * row.getSize().getHeight() + 5);
    }
    else {
      actions.moveByOffset(0, shiftByRows * row.getSize().getHeight() - 5);
    }
    actions.release().build().perform();
  }
}
