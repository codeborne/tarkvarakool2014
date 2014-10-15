package controllers;

import framework.Redirect;
import model.Goal;
import org.hibernate.HibernateException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class DeleteTest extends ControllerTest<Delete>{


  @Test
  public void postDeletesGoal() {
    controller.id = 5L;
    Goal expectedGoal = new Goal("name", 300);
    when(hibernate.get(Goal.class, 5L)).thenReturn(expectedGoal);

    try {
      controller.post();
      fail();
    } catch (Redirect e) {
      assertEquals("goals", e.getMessage());
      assertSame(expectedGoal, getDeletedEntities().get(0));
    }
  }


  @Test (expected = HibernateException.class)
  public void postDeleteThrowsHibernateException() {
    controller.id = 5L;
    Goal goal = new Goal("", 1);
    when(hibernate.get(Goal.class, 5L)).thenReturn(goal);
    doThrow(new HibernateException("")).when(hibernate).delete(goal);

    controller.post();
  }


  @Test
  public void postNoObjectToDelete() {
    controller.id = 5L;
    when(hibernate.get(Goal.class, 5L)).thenReturn(null);

    try {
      controller.post();
      fail();
    }
    catch (Redirect e) {
      assertEquals("goals", e.getMessage());
    }
  }

}
