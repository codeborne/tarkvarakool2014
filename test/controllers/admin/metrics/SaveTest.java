package controllers.admin.metrics;

import controllers.ControllerTest;
import model.Goal;
import model.Metric;
import org.hibernate.Criteria;
import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.Criterion;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SaveTest extends ControllerTest<Save> {

  @Before
  public void setUp() throws Exception {
    when(hibernate.get(Goal.class, 2L)).thenReturn(new Goal("Eesmark", "Kommentaar", 85, 1));

  }

  @Test
  public void postIfNameIsNull() {
    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage mõõdik."));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfNameHasOnlySpaces() {
    controller.name = " \n\n \n    \r\n \r\n \n \n";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage mõõdik."));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfNameIsEmpty() {
    controller.name = "";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage mõõdik."));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfPublicDescriptionThrowsException() {
    controller.name = "metric";
    controller.errors.put("publicDescription", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga avalikus kirjelduses"));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfPrivateDescriptionThrowsException() {
    controller.name = "metric";
    controller.errors.put("privateDescription", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga mitteavalikus kirjelduses"));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfStartLevelIsNotNumber() {
    controller.name = "metric";
    controller.errors.put("startLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Algtase peab olema number"));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfCommentOnStartLevelThrowsException() {
    controller.name = "metric";
    controller.errors.put("commentOnStartLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga algtaseme kommentaaris"));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfTargetLevelIsNotNumber() {
    controller.name = "metric";
    controller.errors.put("targetLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sihttase peab olema number"));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfCommentOnTargetLevelThrowsException() {
    controller.name = "metric";
    controller.errors.put("commentOnTargetLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga sihttaseme kommentaaris"));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfInfoSourceThrowsException() {
    controller.name = "metric";
    controller.errors.put("infoSource", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga infoallika sisestamisel"));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postIfInstitutionToReportThrowsException() {
    controller.name = "metric";
    controller.errors.put("institutionToReport", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga raporteeritava asutuse sisestamisel"));
    verify(hibernate, never()).save(any(Metric.class));
  }

  @Test
  public void postWithDuplicateGoalThrowsConstraintViolation() {
    controller.name = "asd";
    doThrow(mock(ConstraintViolationException.class)).when(hibernate).save(any(Metric.class));
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Metric.class)).thenReturn(criteria);
    when(criteria.createCriteria(anyString())).thenReturn(criteria);
    when(criteria.add(any(Criterion.class))).thenReturn(criteria);
    when(criteria.setProjection(any(AggregateProjection.class))).thenReturn(criteria);
    when(criteria.uniqueResult()).thenReturn(6.2);

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("See mõõdik on juba sisestatud."));
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
    controller.infoSource = "  e";
    controller.institutionToReport = "f   ";
    controller.goalId = 5L;
    controller.startLevel = 5;
    controller.targetLevel = 6;
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Metric.class)).thenReturn(criteria);
    when(criteria.createCriteria(anyString())).thenReturn(criteria);
    when(criteria.add(any(Criterion.class))).thenReturn(criteria);
    when(criteria.setProjection(any(AggregateProjection.class))).thenReturn(criteria);
    when(criteria.uniqueResult()).thenReturn(6.2);

    assertRender(controller.post());
    Metric savedMetric = (Metric) getSavedEntity();

    assertEquals("metric", savedMetric.getName());
    assertEquals("%", savedMetric.getUnit());
    assertEquals("a b", savedMetric.getPublicDescription());
    assertEquals("b", savedMetric.getPrivateDescription());
    assertEquals("c", savedMetric.getCommentOnStartLevel());
    assertEquals("d", savedMetric.getCommentOnTargetLevel());
    assertEquals("e", savedMetric.getInfoSource());
    assertEquals("f", savedMetric.getInstitutionToReport());
    assertEquals((Double) 8.0, savedMetric.getOrderNumber());
    assertEquals(5, (int) savedMetric.getStartLevel());
    assertEquals(6, (int) savedMetric.getTargetLevel());
  }


  @Test
  public void postIfNewMetricAddedWithNameOnly() {
    controller.name = "metric";

    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Metric.class)).thenReturn(criteria);
    when(criteria.createCriteria(anyString())).thenReturn(criteria);
    when(criteria.add(any(Criterion.class))).thenReturn(criteria);
    when(criteria.setProjection(any(AggregateProjection.class))).thenReturn(criteria);
    when(criteria.uniqueResult()).thenReturn(6.2);

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
    controller.startLevel = 5;
    controller.commentOnStartLevel = "c";
    controller.targetLevel = 6;
    controller.commentOnTargetLevel = "d";
    controller.infoSource = "e";
    controller.institutionToReport = "f";
    controller.orderNumber = 5.0;

    Metric metricBeingChanged = new Metric(new Goal("", 10), "TERE", null, null, null, 0, null, 0, null, null, null, 1.0);
    when(hibernate.get(Metric.class, 2L)).thenReturn(metricBeingChanged);

    assertRender(controller.post());
    Metric updatedMetric = (Metric) getUpdatedEntity();

    assertEquals("metric", updatedMetric.getName());
    assertEquals("%", updatedMetric.getUnit());
    assertEquals("a a a", updatedMetric.getPublicDescription());
    assertEquals("b", updatedMetric.getPrivateDescription());
    assertEquals(5, (int) updatedMetric.getStartLevel());
    assertEquals("c", updatedMetric.getCommentOnStartLevel());
    assertEquals(6, (int) updatedMetric.getTargetLevel());
    assertEquals("d", updatedMetric.getCommentOnTargetLevel());
    assertEquals("e", updatedMetric.getInfoSource());
    assertEquals("f", updatedMetric.getInstitutionToReport());
    assertEquals((Double) 5.0, updatedMetric.getOrderNumber());
  }
}
