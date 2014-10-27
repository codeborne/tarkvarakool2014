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
    when(hibernate.get(Goal.class, 2L)).thenReturn(new Goal("name", "abc" ,10, 1));

    assertRender(controller.get());

    assertEquals("name", controller.name);
    assertEquals(10, (int) controller.budget);
    assertEquals("abc", controller.comment);

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
    controller.comment = "abc";

    Goal goalBeingChanged = new Goal("TERE", "tre",34, 1);
    when(hibernate.get(Goal.class, 2L)).thenReturn(goalBeingChanged);

    controller.save();

    assertEquals(10, (int)goalBeingChanged.getBudget());
    assertEquals("name", goalBeingChanged.getName());
    assertEquals("abc", goalBeingChanged.getComment());

    verify(hibernate).update(goalBeingChanged);
  }

}






