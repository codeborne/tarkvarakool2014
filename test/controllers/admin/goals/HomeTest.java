package controllers.admin.goals;

import controllers.ControllerTest;
import model.Goal;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class HomeTest extends ControllerTest<Home> {

  @Before
  public void setUp() throws Exception {
    when(request.getPathInfo()).thenReturn("admin/");
  }

  @Test
  public void postIfChangedSequenceNumberIsGreaterThanPreviousSequenceNumber() throws Exception {
    controller.id = 8L;
    controller.sequenceNumber = 2;
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Goal.class)).thenReturn(criteria);
    when(criteria.addOrder(any(Order.class))).thenReturn(criteria);
    when(criteria.list()).thenReturn(asList(new Goal("some goal", "", 1000, 1), new Goal("second goal", "", 2000, 2),
      new Goal("third goal", "", 3000, 3)));
    when(hibernate.get(Goal.class, 8L)).thenReturn(new Goal("some goal", "", 1000, 1));

    assertRender(controller.post());

    List<Goal> updatedGoals = getUpdatedEntities();

    assertTrue(updatedGoals.size() == 3);

    for (Goal updatedGoal : updatedGoals) {
      verify(hibernate, atLeastOnce()).update(updatedGoal);
      if ("some goal".equals(updatedGoal.getName())) {
        assertEquals(2, (int) updatedGoal.getSequenceNumber());
      }
      if ("second goal".equals(updatedGoal.getName())) {
        assertEquals(1, (int) updatedGoal.getSequenceNumber());
      }
    }

  }

  @Test
  public void postIfChangedSequenceNumberIsGreaterThanGoalsListSize() throws Exception {
    controller.id = 8L;
    controller.sequenceNumber = 5;
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Goal.class)).thenReturn(criteria);
    when(criteria.addOrder(any(Order.class))).thenReturn(criteria);
    when(criteria.list()).thenReturn(asList(new Goal("some goal", "", 1000, 1), new Goal("second goal", "", 2000, 2),
      new Goal("third goal", "", 3000, 3)));
    when(hibernate.get(Goal.class, 8L)).thenReturn(new Goal("some goal", "", 1000, 1));

    assertRender(controller.post());

    List<Goal> updatedGoals = getUpdatedEntities();

    assertTrue(updatedGoals.size() == 4);

    for (Goal updatedGoal : updatedGoals) {
      verify(hibernate, atLeastOnce()).update(updatedGoal);
      if ("some goal".equals(updatedGoal.getName())) {
        assertEquals(3, (int) updatedGoal.getSequenceNumber());
      }
      if ("second goal".equals(updatedGoal.getName())) {
        assertEquals(1, (int) updatedGoal.getSequenceNumber());
      }
      if ("third goal".equals(updatedGoal.getName())) {
        assertEquals(2, (int) updatedGoal.getSequenceNumber());
      }
    }
  }

  @Test
  public void postIfChangedSequenceNumberIsSmallerThanPreviousSequenceNumber() throws Exception {
    controller.id = 3L;
    controller.sequenceNumber = 1;
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Goal.class)).thenReturn(criteria);
    when(criteria.addOrder(any(Order.class))).thenReturn(criteria);
    when(criteria.list()).thenReturn(Arrays.asList(new Goal("some goal", "", 1000, 1), new Goal("second goal", "", 2000, 2),
      new Goal("third goal", "", 3000, 3), new Goal("fourth goal", "", 4000, 4)));
    when(hibernate.get(Goal.class, 3L)).thenReturn(new Goal("fourth goal", "", 4000, 4));

    assertRender(controller.post());

    List<Goal> updatedGoals = getUpdatedEntities();
    assertEquals(asList("fourth goal", "third goal", "second goal", "some goal", "fourth goal"),
      updatedGoals.stream().map(Goal::getName).collect(toList()));
    assertEquals(asList(1, 4, 3, 2, 1),
      updatedGoals.stream().map(Goal::getSequenceNumber).collect(toList()));
  }

  @Test
  public void postIfInsertedSameSequenceNumber() throws Exception {
    controller.id = 3L;
    controller.sequenceNumber = 1;
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Goal.class)).thenReturn(criteria);
    when(criteria.addOrder(any(Order.class))).thenReturn(criteria);
    when(criteria.list()).thenReturn(asList(new Goal("some goal", "", 1000, 1), new Goal("second goal", "", 2000, 2),
      new Goal("third goal", "", 3000, 3), new Goal("fourth goal", "", 4000, 4)));
    when(hibernate.get(Goal.class, 3L)).thenReturn(new Goal("fourth goal", "", 1000, 1));

    assertRender(controller.post());

    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test(expected = HibernateException.class)
  public void postFailure() throws Exception {
    controller.id = 8L;
    controller.sequenceNumber = 2;
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Goal.class)).thenReturn(criteria);
    when(criteria.addOrder(any(Order.class))).thenReturn(criteria);
    when(criteria.list()).thenReturn(Arrays.asList(new Goal("some goal", "", 1000, 1), new Goal("second goal", "", 2000, 2),
      new Goal("third goal", "", 3000, 3)));
    when(hibernate.get(Goal.class, 8L)).thenReturn(new Goal("some goal", "", 1000, 1));
    doThrow(mock(HibernateException.class)).when(hibernate).update(any(Goal.class));

    assertRender(controller.post());
  }
}