package controllers.admin.values;

import controllers.ControllerTest;
import controllers.UserAwareController;
import model.Goal;
import model.Metric;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ModifyTest extends ControllerTest<Modify> {

  @Before
  public void setUp() throws Exception {
    when(session.getAttribute("username")).thenReturn("Some username");
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Metric.class)).thenReturn(criteria);
    when(criteria.createCriteria(anyString())).thenReturn(criteria);
    when(criteria.add(any(Criterion.class))).thenReturn(criteria);
    when(criteria.list()).thenReturn(Arrays.asList(new Metric(new Goal("some goal", 1000), "Some metric", "", "", 777, "", 55, "", "", "abc")));
  }

  @Test
  public void ModifyValueSuccess() throws Exception {
    controller.goalId = 1L;
    controller.metricId = 1L;
    controller.year = 2015;
    controller.value = 777L;

    assertRender(controller.post());

    Metric savedMetric = (Metric) getUpdatedEntity();
    Assert.assertTrue(controller.errorsList.isEmpty());

    verify(hibernate).update(savedMetric);
  }

  @Test
  public void FailWhenMetricIdIsNull() throws Exception {
    controller.goalId = 2L;
    controller.year = 2014;
    controller.value = 744L;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));

  }

  @Test
  public void postIfErrorsContainsMetricId() {
    controller.goalId = 2L;
    controller.year = 2014;
    controller.value = 744L;


    controller.errors.put("metricId", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void FailWhenGoalIdIsNull() throws Exception {
    controller.metricId = 2L;
    controller.year = 2014;
    controller.value = 744L;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void postIfErrorsContainsGoalId() {
    controller.metricId = 2L;
    controller.year = 2014;
    controller.value = 744L;


    controller.errors.put("goalId", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void FailWhenYearNull() throws Exception {
    controller.goalId = 12L;
    controller.metricId = 21L;
    controller.value = 744L;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void postIfErrorsContainsYear() {
    controller.goalId = 12L;
    controller.metricId = 21L;
    controller.value = 744L;

    controller.errors.put("year", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void FailWhenYearIsTooBig() throws Exception {
    controller.goalId = 12L;
    controller.metricId = 21L;
    controller.year = UserAwareController.MAXIMUM_YEAR + 1;
    controller.value = 744L;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void FailWhenYearIsTooSmall() throws Exception {
    controller.goalId = 12L;
    controller.metricId = 21L;
    controller.year = UserAwareController.MINIMUM_YEAR - 1;
    controller.value = 744L;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void FailWhenValueIsNull() throws Exception {
    controller.goalId = 12L;
    controller.metricId = 2L;
    controller.year = 2014;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne väärtus."));
  }

  @Test
  public void postIfErrorsContainsValue() {
    controller.goalId = 12L;
    controller.metricId = 2L;
    controller.year = 2014;

    controller.errors.put("value", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne väärtus."));
  }

}
