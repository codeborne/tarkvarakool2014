package controllers;

import model.Goal;
import org.hibernate.HibernateException;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DeleteTest extends ControllerTest<Delete>{

  @Test
  public void postDeletesGoal() {
    controller.id = 5L;
    Goal expectedGoal = new Goal("name", 300);
    when(hibernate.get(Goal.class, 5L)).thenReturn(expectedGoal);

    assertRedirect(Goals.class, controller.post());

    verify(hibernate).delete(expectedGoal);
  }

  @Test (expected = HibernateException.class)
  public void postDeleteThrowsHibernateException() {
    controller.id = 5L;
    Goal goal = new Goal("a", 1);
    when(hibernate.get(Goal.class, 5L)).thenReturn(goal);
    doThrow(new HibernateException("")).when(hibernate).delete(goal);

    assertRender(controller.post());
  }

  @Test
  public void postNoObjectToDelete() {
    controller.id = 5L;
    when(hibernate.get(Goal.class, 5L)).thenReturn(null);

    assertRedirect(Goals.class, controller.post());
  }
}
