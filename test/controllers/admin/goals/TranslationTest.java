package controllers.admin.goals;

import controllers.ControllerTest;
import model.Goal;
import model.Metric;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TranslationTest extends ControllerTest<Translation>{

  @Before
  public void setUp() throws Exception {
    when(request.getParameterValues("engUnit")).thenReturn(new String[]{"people","%"});
    when(request.getParameterValues("engMetricName")).thenReturn(new String[]{"metric1","metric2"});
    when(request.getParameterValues("engPublicDescription")).thenReturn(new String[]{"descr1",""});
    when(request.getPathInfo()).thenReturn("admin/");
  }

  @Test
  public void postIfGoalIdHasErrors() throws Exception {
    controller.errors.put("goalId",new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfGoalIdIsNull() throws Exception {
    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfEngNameHasErrors() throws Exception {
    controller.goalId = 1L;
    controller.errors.put("engName",new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfEngUnitHasErrors() throws Exception {
    controller.goalId = 1L;
    controller.errors.put("engUnit",new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfEngCommentHasErrors() throws Exception {
    controller.goalId = 1L;
    controller.errors.put("engComment",new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfEngMetricNameHasErrors() throws Exception {
    controller.goalId = 1L;
    controller.errors.put("engMetricName",new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfEngPublicDescriptionHasErrors() throws Exception {
    controller.goalId = 1L;
    controller.errors.put("engPublicDescription",new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postUpdateSuccess() throws Exception {
    controller.goalId = 3L;
    controller.engName = "goal";
    controller.engComment = "comment";
    Goal goal = new Goal("Eesmark", "kommentaar", 100, 1);
    Metric metric1 = new Metric(goal, "moodik1","inimesed" ,"kirjeldus1", null, 0, null, 0, null, null, null, 1.0, true);
    Metric metric2 = new Metric(goal, "moodik2","%", "kirjeldus2", null, 0, null, 0, null, null, null, 1.0, true);
    Set<Metric> metrics = new HashSet<>();
    metrics.add(metric1);
    metrics.add(metric2);
    goal.setMetrics(metrics);

    when(hibernate.get(Goal.class, 3L)).thenReturn(goal);

    assertRender(controller.post());

    Goal updatedGoal = (Goal) getUpdatedEntity();
    Metric updatedMetric1 = (Metric)updatedGoal.getMetrics().toArray()[0];
    Metric updatedMetric2 = (Metric)updatedGoal.getMetrics().toArray()[1];

    assertEquals("goal", updatedGoal.getEngName());
    assertEquals("comment", updatedGoal.getEngComment());
    assertEquals("people", updatedMetric1.getEngUnit());
    assertEquals("metric1", updatedMetric1.getEngName());
    assertEquals("descr1", updatedMetric1.getEngPublicDescription());
    assertEquals("%", updatedMetric2.getEngUnit());
    assertEquals("metric2", updatedMetric2.getEngName());
    assertEquals(null, updatedMetric2.getEngPublicDescription());

  }

  @Test (expected = HibernateException.class)
  public void postUpdateFailure() {
    controller.goalId = 3L;
    controller.engName = "goal";
    controller.engComment = "comment";
    Goal goal = new Goal("Eesmark", "kommentaar", 100, 1);
    Metric metric1 = new Metric(goal, "moodik1","inimesed" ,"kirjeldus1", null, 0, null, 0, null, null, null, 1.0, true);
    Metric metric2 = new Metric(goal, "moodik2","%", "kirjeldus2", null, 0, null, 0, null, null, null, 1.0, true);
    Set<Metric> metrics = new HashSet<>();
    metrics.add(metric1);
    metrics.add(metric2);
    goal.setMetrics(metrics);

    when(hibernate.get(Goal.class, 3L)).thenReturn(goal);
    doThrow(mock(HibernateException.class)).when(hibernate).update(goal);

    assertRender(controller.post());

  }
}