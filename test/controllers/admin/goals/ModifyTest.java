package controllers.admin.goals;

import controllers.ControllerTest;
import model.Goal;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ModifyTest extends ControllerTest<Modify> {

  @Test
  public void getWhenObjectFound() {
    controller.id = 2L;
    when(hibernate.get(Goal.class, 2L)).thenReturn(new Goal("name", 10));

    assertRender(controller.get());

    assertEquals("name", controller.name);
    assertEquals(10, (int) controller.budget);
  }

  @Test
  public void getWhenObjectNotFound() {
    controller.id = 2L;
    when(hibernate.get(Goal.class, 2L)).thenReturn(null);

    assertRedirect(Add.class, controller.get());
  }

  @Test
  public void updateSuccess() {
    controller.id = 2L;
    controller.name = "name";
    controller.budget = 10;

    Goal goalBeingChanged = new Goal("TERE", 34);
    when(hibernate.get(Goal.class, 2L)).thenReturn(goalBeingChanged);

    controller.save();

    assertEquals(10, (int)goalBeingChanged.getBudget());
    assertEquals("name", goalBeingChanged.getName());
    verify(hibernate).update(goalBeingChanged);
  }

}






