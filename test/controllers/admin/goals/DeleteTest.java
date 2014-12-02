package controllers.admin.goals;

import controllers.ControllerTest;
import model.Goal;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class DeleteTest extends ControllerTest<Delete> {

  @Test (expected = HibernateException.class)
  public void postDeleteThrowsHibernateException() {
    controller.id = 5L;
    Goal goal = new Goal("", 1);
    when(hibernate.get(Goal.class, 5L)).thenReturn(goal);
    doThrow(mock(HibernateException.class)).when(hibernate).delete(goal);

    assertRender(controller.post());
  }

  @Test
  public void postNoObjectToDelete() {
    controller.id = 5L;
    when(hibernate.get(Goal.class, 5L)).thenReturn(null);

    assertRedirect(Home.class, controller.post());
    verify(hibernate, never()).delete(any(Goal.class));
  }

  @Test
  public void postDeletesGoalAndReordersGoals() throws Exception {
    controller.id = 3L;
    Goal deletableGoal = new Goal("second goal", "", 2000, 2);
    when(hibernate.createCriteria(Goal.class).addOrder(any(Order.class)).list()).thenReturn(asList(
      new Goal("some goal", "", 1000, 1),
      deletableGoal,
      new Goal("third goal", "", 3000, 3),
      new Goal("fourth goal", "", 4000, 4)));
    when(hibernate.get(Goal.class, 3L)).thenReturn(deletableGoal);

    assertRedirect(Home.class, controller.post());

    List<Goal> updatedGoals = getUpdatedEntities();

    assertTrue(updatedGoals.size() == 2);

    for (Goal updatedGoal : updatedGoals) {
      verify(hibernate).update(updatedGoal);
      if ("third goal".equals(updatedGoal.getName())) {
        assertEquals(2, (int) updatedGoal.getSequenceNumber());
      }
      if ("fourth goal".equals(updatedGoal.getName())) {
        assertEquals(3, (int) updatedGoal.getSequenceNumber());
      }
    }

    verify(hibernate).delete(deletableGoal);
  }
}
