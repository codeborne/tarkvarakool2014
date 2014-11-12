package controllers.admin.goals;

import controllers.ControllerTest;
import model.Goal;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
  }

  @Test
  public void postDeletesGoalAndReordersGoals() throws Exception {
    controller.id = 3L;
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Goal.class)).thenReturn(criteria);
    when(criteria.addOrder(any(Order.class))).thenReturn(criteria);
    Goal deletableGoal = new Goal("second goal", "", 2000, 2);
    when(criteria.list()).thenReturn(Arrays.asList(new Goal("some goal", "", 1000, 1), deletableGoal,
      new Goal("third goal", "", 3000, 3), new Goal("fourth goal", "", 4000, 4)));
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
