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
    when(request.getParameterValues("engInfoSource")).thenReturn(new String[]{"info1",""});
    when(request.getParameterValues("engStartLevelComment")).thenReturn(new String[]{"startComment1","startComment2"});
    when(request.getParameterValues("engTargetLevelComment")).thenReturn(new String[]{"targetComment1","targetComment2"});
    when(request.getPathInfo()).thenReturn("admin/");
  }

  @Test
  public void get() throws Exception {
    controller.goalId = 5L;
    when(hibernate.get(Goal.class, 5L)).thenReturn(new Goal("goal",null, 1,1));
    assertRender(controller.get());
  }

  @Test
  public void postIfGoalIdHasErrors() throws Exception {
    controller.errors.put("goalId",new RuntimeException());

    assertRedirect(Home.class, controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfGoalIdIsNull() throws Exception {
    assertRedirect(Home.class, controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfEngNameHasErrors() throws Exception {
    controller.goalId = 1L;
    controller.errors.put("engName",new RuntimeException());

    assertRedirect(Home.class, controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfEngUnitHasErrors() throws Exception {
    controller.goalId = 1L;
    controller.errors.put("engUnit",new RuntimeException());

    assertRedirect(Home.class, controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfEngCommentHasErrors() throws Exception {
    controller.goalId = 1L;
    controller.errors.put("engComment",new RuntimeException());

    assertRedirect(Home.class, controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfEngInfoSourceHasErrors() throws Exception {
    controller.goalId = 1L;
    controller.errors.put("engInfoSource",new RuntimeException());

    assertRedirect(Home.class, controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfEngMetricNameHasErrors() throws Exception {
    controller.goalId = 1L;
    controller.errors.put("engMetricName",new RuntimeException());

    assertRedirect(Home.class, controller.post());


    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfEngPublicDescriptionHasErrors() throws Exception {
    controller.goalId = 1L;
    controller.errors.put("engPublicDescription",new RuntimeException());

    assertRedirect(Home.class, controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfEngStartLevelCommentHasErrors() throws Exception {
    controller.goalId = 1L;
    controller.errors.put("engStartLevelComment",new RuntimeException());

    assertRedirect(Home.class, controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).update(any(Goal.class));
  }

  @Test
  public void postIfEngTargetLevelCommentHasErrors() throws Exception {
    controller.goalId = 1L;
    controller.errors.put("engTargetLevelComment",new RuntimeException());

    assertRedirect(Home.class, controller.post());

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
    Metric metric1 = new Metric(goal, "moodik1","inimesed" ,"kirjeldus1", null, 0.0, "kommentaar1", 0.0, "kommentaar2", null, null, 1.0, true);
    Metric metric2 = new Metric(goal, "moodik2","%", "kirjeldus2", null, 0.0, "kommentaar3", 0.0, "kommentaar4", null, null, 1.0, true);
    Set<Metric> metrics = new HashSet<>();
    metrics.add(metric1);
    metrics.add(metric2);
    goal.setMetrics(metrics);

    when(hibernate.get(Goal.class, 3L)).thenReturn(goal);

    assertRedirect(Home.class, controller.post());

    Goal updatedGoal = getUpdatedEntity();
    Metric updatedMetric1 = (Metric)updatedGoal.getMetrics().toArray()[0];
    Metric updatedMetric2 = (Metric)updatedGoal.getMetrics().toArray()[1];

    assertEquals("goal", updatedGoal.getEngName());
    assertEquals("comment", updatedGoal.getEngComment());
    assertEquals("people", updatedMetric1.getEngUnit());
    assertEquals("metric1", updatedMetric1.getEngName());
    assertEquals("descr1", updatedMetric1.getEngPublicDescription());
    assertEquals("info1", updatedMetric1.getEngInfoSource());
    assertEquals("startComment1", updatedMetric1.getEngStartLevelComment());
    assertEquals("targetComment1", updatedMetric1.getEngTargetLevelComment());
    assertEquals("%", updatedMetric2.getEngUnit());
    assertEquals("metric2", updatedMetric2.getEngName());
    assertEquals(null, updatedMetric2.getEngPublicDescription());
    assertEquals(null, updatedMetric2.getEngInfoSource());
    assertEquals("startComment2", updatedMetric2.getEngStartLevelComment());
    assertEquals("targetComment2", updatedMetric2.getEngTargetLevelComment());

    verify(controller.session).setAttribute("message", (messages.get("translationSuccess")));

  }

  @Test (expected = HibernateException.class)
  public void postUpdateFailure() {
    controller.goalId = 3L;
    controller.engName = "goal";
    controller.engComment = "comment";
    Goal goal = new Goal("Eesmark", "kommentaar", 100, 1);
    Metric metric1 = new Metric(goal, "moodik1","inimesed" ,"kirjeldus1", null, 0.0, null, 0.0, null, null, null, 1.0, true);
    Metric metric2 = new Metric(goal, "moodik2","%", "kirjeldus2", null, 0.0, null, 0.0, null, null, null, 1.0, true);
    Set<Metric> metrics = new HashSet<>();
    metrics.add(metric1);
    metrics.add(metric2);
    goal.setMetrics(metrics);

    when(hibernate.get(Goal.class, 3L)).thenReturn(goal);
    doThrow(mock(HibernateException.class)).when(hibernate).update(goal);

    assertRedirect(Home.class, controller.post());

  }
}