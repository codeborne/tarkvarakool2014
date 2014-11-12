package controllers.admin.goals;

import controllers.ControllerTest;
import model.Goal;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.junit.Before;
import org.junit.Test;

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
    when(hibernate.createCriteria(Goal.class).addOrder(any(Order.class)).list()).thenReturn(asList(
      new Goal("some goal", "", 1000, 1),
      new Goal("second goal", "", 2000, 2),
      new Goal("third goal", "", 3000, 3),
      new Goal("fourth goal", "", 4000, 4)));
  }

  @Test
  public void postIfChangedSequenceNumberIsGreaterThanPreviousSequenceNumber() throws Exception {
    controller.id = 8L;
    controller.sequenceNumber = 2;
    when(hibernate.get(Goal.class, 8L)).thenReturn(new Goal("some goal", "", 1000, 1));

    assertRender(controller.post());

    List<Goal> updatedGoals = getUpdatedEntities();

    assertTrue(updatedGoals.size() == 3);

    assertEquals("some goal",updatedGoals.get(0).getName());
    assertEquals(2, (int)updatedGoals.get(0).getSequenceNumber());
    assertEquals("second goal",updatedGoals.get(1).getName());
    assertEquals(1, (int)updatedGoals.get(1).getSequenceNumber());

  }

  @Test
  public void postIfChangedSequenceNumberIsGreaterThanGoalsListSize() throws Exception {
    controller.id = 8L;
    controller.sequenceNumber = 6;
    when(hibernate.get(Goal.class, 8L)).thenReturn(new Goal("some goal", "", 1000, 1));

    assertRender(controller.post());

    List<Goal> updatedGoals = getUpdatedEntities();

    assertTrue(updatedGoals.size() == 5);

    assertEquals("some goal",updatedGoals.get(0).getName());
    assertEquals(4, (int)updatedGoals.get(0).getSequenceNumber());
    assertEquals("second goal",updatedGoals.get(1).getName());
    assertEquals(1, (int)updatedGoals.get(1).getSequenceNumber());
    assertEquals("third goal",updatedGoals.get(2).getName());
    assertEquals(2, (int)updatedGoals.get(2).getSequenceNumber());
    assertEquals("fourth goal",updatedGoals.get(3).getName());
    assertEquals(3, (int)updatedGoals.get(3).getSequenceNumber());
    assertEquals("some goal",updatedGoals.get(4).getName());
    assertEquals(4, (int)updatedGoals.get(4).getSequenceNumber());
  }

  @Test
  public void postIfChangedSequenceNumberIsSmallerThanPreviousSequenceNumber() throws Exception {
    controller.id = 3L;
    controller.sequenceNumber = 1;
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


    when(hibernate.get(Goal.class, 3L)).thenReturn(new Goal("fourth goal", "", 1000, 1));

    assertRender(controller.post());

    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test(expected = HibernateException.class)
  public void postFailureDueToUpdateFailure() throws Exception {
    controller.id = 8L;
    controller.sequenceNumber = 2;

    when(hibernate.get(Goal.class, 8L)).thenReturn(new Goal("some goal", "", 1000, 1));
    doThrow(mock(HibernateException.class)).when(hibernate).update(any(Goal.class));

    assertRender(controller.post());
  }
}