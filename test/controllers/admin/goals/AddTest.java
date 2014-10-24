package controllers.admin.goals;

import controllers.ControllerTest;
import model.Goal;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class AddTest extends ControllerTest<Add> {

  @Test
  public void saveSuccess() {
    controller.name = "ab cd";
    controller.budget = 111;
    controller.comment = "rioaerioaje";

    controller.post();

    Goal savedGoal = (Goal) getSavedEntity();

    assertEquals("ab cd", savedGoal.getName());
    assertEquals(111, (int) savedGoal.getBudget());
    assertEquals("rioaerioaje", savedGoal.getComment());

    verify(hibernate).save(savedGoal);
  }

  @Test
  public void saveEmptyCommentSuccess() {
    controller.name = "ab cd";
    controller.budget = 111;
    controller.comment = "";

    controller.post();

    Goal savedGoal = (Goal) getSavedEntity();

    assertEquals("ab cd", savedGoal.getName());
    assertEquals(111, (int) savedGoal.getBudget());
    assertEquals(null, savedGoal.getComment());

    verify(hibernate).save(savedGoal);
  }

  @Test
  public void get() {
    assertRender(controller.get());
  }

}
