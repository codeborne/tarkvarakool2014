package controllers.admin.values;

import controllers.ControllerTest;
import controllers.UserAwareController;
import model.Goal;
import model.Metric;
import org.hibernate.criterion.Criterion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ModifyTest extends ControllerTest<Modify> {

  @Before
  public void setUp() throws Exception {
    when(request.getPathInfo()).thenReturn("admin/");
  }

  @Test
  public void modifyMeasuredValueSuccess() throws Exception {
    controller.goalId = 1L;
    controller.metricId = 1L;
    controller.year = 2015;
    controller.value = "777";
    controller.isForecast = false;

    when(hibernate.createCriteria(Metric.class).add(any(Criterion.class)).createCriteria(anyString()).add(any(Criterion.class)).list()).thenReturn(asList(
      new Metric(new Goal("some goal", 1000), "Some metric", "", "", "", 777.0, "", 55.0, "", "", "abc", 1.0, true)));

    assertRender(controller.post());

    Metric savedMetric = getUpdatedEntity();
    Assert.assertTrue(controller.errorsList.isEmpty());

    assertEquals(new BigDecimal(777), savedMetric.getValues().get(controller.year));
    assertTrue(savedMetric.getForecasts().isEmpty());

    verify(hibernate).update(savedMetric);
  }

  @Test
  public void modifyForecastValueSuccess() throws Exception {
    controller.goalId = 1L;
    controller.metricId = 1L;
    controller.year = 2015;
    controller.value = "777";
    controller.isForecast = true;

    when(hibernate.createCriteria(Metric.class).add(any(Criterion.class)).createCriteria(anyString()).add(any(Criterion.class)).list()).thenReturn(asList(
      new Metric(new Goal("some goal", 1000), "Some metric", "", "", "", 777.0, "", 55.0, "", "", "abc", 1.0, true)));

    assertRender(controller.post());

    Metric savedMetric = getUpdatedEntity();
    Assert.assertTrue(controller.errorsList.isEmpty());

    assertEquals(new BigDecimal(777), savedMetric.getForecasts().get(controller.year));
    assertTrue(savedMetric.getValues().isEmpty());

    verify(hibernate).update(savedMetric);
  }

  @Test
  public void setMeasuredValueToNullSuccess() throws Exception {
    controller.goalId = 1L;
    controller.metricId = 1L;
    controller.year = 2015;
    controller.value = null;
    controller.isForecast = false;

    when(hibernate.createCriteria(Metric.class).add(any(Criterion.class)).createCriteria(anyString()).add(any(Criterion.class)).list()).thenReturn(asList(
      new Metric(new Goal("some goal", 1000), "Some metric", "", "", "", 777.0, "", 55.0, "", "", "abc", 1.0, true)));

    assertRender(controller.post());

    Metric savedMetric = getUpdatedEntity();
    Assert.assertTrue(controller.errorsList.isEmpty());

    assertEquals(controller.value, savedMetric.getValues().get(controller.year));
    assertTrue(savedMetric.getForecasts().isEmpty());

    verify(hibernate).update(savedMetric);
  }

  @Test
  public void setForecastValueToNullSuccess() throws Exception {
    controller.goalId = 1L;
    controller.metricId = 1L;
    controller.year = 2015;
    controller.value = null;
    controller.isForecast = true;

    when(hibernate.createCriteria(Metric.class).add(any(Criterion.class)).createCriteria(anyString()).add(any(Criterion.class)).list()).thenReturn(asList(
      new Metric(new Goal("some goal", 1000), "Some metric", "", "", "", 777.0, "", 55.0, "", "", "abc", 1.0, true)));

    assertRender(controller.post());

    Metric savedMetric = getUpdatedEntity();
    Assert.assertTrue(controller.errorsList.isEmpty());

    assertEquals(controller.value, savedMetric.getForecasts().get(controller.year));
    assertTrue(savedMetric.getValues().isEmpty());

    verify(hibernate).update(savedMetric);
  }

  @Test
  public void failWhenMetricIdIsNull() throws Exception {
    controller.goalId = 2L;
    controller.year = 2014;
    controller.value = "744";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));

  }

  @Test
  public void postIfErrorsContainsMetricId() {
    controller.goalId = 2L;
    controller.year = 2014;
    controller.value = "744";


    controller.errors.put("metricId", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
  }

  @Test
  public void failWhenGoalIdIsNull() throws Exception {
    controller.metricId = 2L;
    controller.year = 2014;
    controller.value = "744.55";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
  }

  @Test
  public void postIfErrorsContainsGoalId() {
    controller.metricId = 2L;
    controller.year = 2014;
    controller.value = "744";


    controller.errors.put("goalId", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
  }

  @Test
  public void failWhenYearNull() throws Exception {
    controller.goalId = 12L;
    controller.metricId = 21L;
    controller.value = "744";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
  }

  @Test
  public void postIfErrorsContainsYear() {
    controller.goalId = 12L;
    controller.metricId = 21L;
    controller.value = "744";

    controller.errors.put("year", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
  }

  @Test
  public void failWhenYearIsTooBig() throws Exception {
    controller.goalId = 12L;
    controller.metricId = 21L;
    controller.year = UserAwareController.MAXIMUM_YEAR + 1;
    controller.value = "744";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
  }

  @Test
  public void failWhenYearIsTooSmall() throws Exception {
    controller.goalId = 12L;
    controller.metricId = 21L;
    controller.year = UserAwareController.MINIMUM_YEAR - 1;
    controller.value = "744";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
  }

  @Test
  public void postIfErrorsContainsValue() {
    controller.goalId = 12L;
    controller.metricId = 2L;
    controller.year = 2014;

    controller.errors.put("value", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
  }

  @Test
  public void postIfIncorrectMetricOrGoalId() {
    controller.goalId = 12L;
    controller.metricId = 21L;
    controller.year = 2016;
    controller.value = "744";
    controller.isForecast = true;

    when(session.getAttribute("username")).thenReturn("Some username");
    when(hibernate.createCriteria(Goal.class).add(any(Criterion.class)).list()).thenReturn(new ArrayList<Metric>());


    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
  }

}
