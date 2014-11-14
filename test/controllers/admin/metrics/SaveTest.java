package controllers.admin.metrics;

import controllers.ControllerTest;
import model.Goal;
import model.Metric;
import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.Criterion;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SaveTest extends ControllerTest<Save> {
  public static final long GOAL_ID = 2L;

  @Before
  public void setUp() throws Exception {
    controller.goalId = GOAL_ID;
    when(hibernate.get(Goal.class, GOAL_ID)).thenReturn(new Goal("Eesmark", "Kommentaar", 85, 1));
    when(request.getPathInfo()).thenReturn("admin/");
  }

  @Test
  public void postIfNameIsNull() {
    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertMetric")));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfNameHasOnlySpaces() {
    controller.name = " \n\n \n    \r\n \r\n \n \n";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertMetric")));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfNameIsEmpty() {
    controller.name = "";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertMetric")));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfPublicDescriptionThrowsException() {
    controller.name = "metric";
    controller.errors.put("publicDescription", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfPrivateDescriptionThrowsException() {
    controller.name = "metric";
    controller.errors.put("privateDescription", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfStartLevelIsNotNumber() {
    controller.name = "metric";
    controller.errors.put("startLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfCommentOnStartLevelThrowsException() {
    controller.name = "metric";
    controller.errors.put("commentOnStartLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfTargetLevelIsNotNumber() {
    controller.name = "metric";
    controller.errors.put("targetLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfCommentOnTargetLevelThrowsException() {
    controller.name = "metric";
    controller.errors.put("commentOnTargetLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfInfoSourceThrowsException() {
    controller.name = "metric";
    controller.errors.put("infoSource", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInfoSource")));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfInstitutionToReportThrowsException() {
    controller.name = "metric";
    controller.errors.put("institutionToReport", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postWithDuplicateGoalThrowsConstraintViolation() {
    controller.name = "asd";
    doThrow(mock(ConstraintViolationException.class)).when(hibernate).save(any(Metric.class));
    when(hibernate.createCriteria(Metric.class).add(any(Criterion.class)).setProjection(any(AggregateProjection.class)).uniqueResult()).thenReturn(6.2);

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertedMetric")));
    verify(transaction).rollback();
  }

  @Test
  public void postIfNewMetricIsAddedAndNameTrimmed() {
    controller.name = "metric    ";
    controller.unit = "  %";
    controller.publicDescription = "a b    ";
    controller.privateDescription = "\n b \n";
    controller.commentOnStartLevel = "    c";
    controller.commentOnTargetLevel = "\r d";
    controller.infoSource = "http://";
    controller.institutionToReport = "f   ";
    controller.startLevel = "5.0";
    controller.targetLevel = "6.0";
    when(hibernate.createCriteria(Metric.class).add(any(Criterion.class)).setProjection(any(AggregateProjection.class)).uniqueResult()).thenReturn(6.2);

    assertRender(controller.post());
    Metric savedMetric = (Metric) getSavedEntity();

    assertEquals("metric", savedMetric.getName());
    assertEquals("%", savedMetric.getUnit());
    assertEquals("a b", savedMetric.getPublicDescription());
    assertEquals("b", savedMetric.getPrivateDescription());
    assertEquals("c", savedMetric.getCommentOnStartLevel());
    assertEquals("d", savedMetric.getCommentOnTargetLevel());
    assertEquals("http://", savedMetric.getInfoSource());
    assertEquals("f", savedMetric.getInstitutionToReport());
    assertEquals((Double) 8.0, savedMetric.getOrderNumber());
    assertEquals((Double) 5.0, savedMetric.getStartLevel());
    assertEquals((Double) 6.0, savedMetric.getTargetLevel());
    assertEquals(false, savedMetric.getIsPublic());
  }


  @Test
  public void postIfNewMetricAddedWithNameOnly() {
    controller.name = "metric";

    when(hibernate.createCriteria(Metric.class).add(any(Criterion.class)).setProjection(any(AggregateProjection.class)).uniqueResult()).thenReturn(6.2);

    assertRender(controller.post());

    Metric savedMetric = (Metric) getSavedEntity();

    assertEquals("metric", savedMetric.getName());
    assertEquals(null, savedMetric.getUnit());
    assertEquals(null, savedMetric.getPublicDescription());
    assertEquals(null, savedMetric.getPrivateDescription());
    assertEquals(null, savedMetric.getStartLevel());
    assertEquals(null, savedMetric.getCommentOnStartLevel());
    assertEquals(null, savedMetric.getTargetLevel());
    assertEquals(null, savedMetric.getCommentOnTargetLevel());
    assertEquals(null, savedMetric.getInfoSource());
    assertEquals(null, savedMetric.getInstitutionToReport());
    assertEquals((Double) 8.0, savedMetric.getOrderNumber());
  }

  @Test
  public void updateSuccess() {
    controller.metricId = 2L;
    controller.name = "metric";
    controller.unit = "%";
    controller.publicDescription = "a a a";
    controller.privateDescription = "b";
    controller.startLevel = "5.0";
    controller.commentOnStartLevel = "c";
    controller.targetLevel = "6.0";
    controller.commentOnTargetLevel = "d";
    controller.infoSource = "http://";
    controller.institutionToReport = "f";
    controller.orderNumber = 5.0;
    controller.isPublic = true;

    Metric metricBeingChanged = new Metric(new Goal("", 10), "TERE", null, null, null, 0.0, null, 0.0, null, null, null, 1.0, false);
    when(hibernate.get(Metric.class, 2L)).thenReturn(metricBeingChanged);

    assertRender(controller.post());
    Metric updatedMetric = (Metric) getUpdatedEntity();

    assertEquals("metric", updatedMetric.getName());
    assertEquals("%", updatedMetric.getUnit());
    assertEquals("a a a", updatedMetric.getPublicDescription());
    assertEquals("b", updatedMetric.getPrivateDescription());
    assertEquals((Double) 5.0,updatedMetric.getStartLevel());
    assertEquals("c", updatedMetric.getCommentOnStartLevel());
    assertEquals((Double) 6.0, updatedMetric.getTargetLevel());
    assertEquals("d", updatedMetric.getCommentOnTargetLevel());
    assertEquals("http://", updatedMetric.getInfoSource());
    assertEquals("f", updatedMetric.getInstitutionToReport());
    assertEquals((Double) 5.0, updatedMetric.getOrderNumber());
    assertEquals(true, updatedMetric.getIsPublic());
  }

  @Test
  public void postIfTargetLevelIsNullAndStartLevelIsEmptyString() {
    controller.name="Metric";
    controller.startLevel="";
    when(hibernate.createCriteria(Metric.class).add(any(Criterion.class)).setProjection(any(AggregateProjection.class)).uniqueResult()).thenReturn(6.2);

    assertRender(controller.post());

    Metric savedMetric = (Metric) getSavedEntity();

    assertEquals("Metric", savedMetric.getName());
    assertEquals(null, savedMetric.getStartLevel());
    assertEquals(null, savedMetric.getTargetLevel());
  }

  @Test
  public void postIfStartLevelAndTargetLevelAreNotNumbers() {
    controller.name="Metric";
    controller.startLevel="A";
    controller.targetLevel="5%";
    assertRender(controller.post());

    assertEquals(2, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorStartLevel")));
    assertTrue(controller.errorsList.contains(messages.get("errorTargetLevel")));
    verify(hibernate, never()).save(any(Metric.class));
  }
}
