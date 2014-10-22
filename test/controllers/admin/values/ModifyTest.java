package controllers.admin.values;

import controllers.ControllerTest;
import controllers.UserAwareController;
import model.Value;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

public class ModifyTest extends ControllerTest<Modify> {

  @Test
  public void ModifyValueSuccess() throws Exception {

    controller.goalId = 1L;
    controller.metricId = 1L;
    controller.year = 2015;
    controller.value = 777L;

    assertRender(controller.post());

    Value savedValue = (Value) getSavedEntity();
    Assert.assertTrue(controller.errorsList.isEmpty());

    verify(hibernate).save(savedValue);
  }

  @Test
  public void FailWhenMetricIdIsNull() throws Exception {
    controller.goalId=2L;
    controller.year=2014;
    controller.value = 744L;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));

  }

  @Test
  public void postIfErrorsContainsMetricId() {
    controller.goalId = 2L;
    controller.year=2014;
    controller.value = 744L;


    controller.errors.put("metricId", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void FailWhenGoalIdIsNull() throws Exception {
    controller.metricId=2L;
    controller.year=2014;
    controller.value = 744L;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void postIfErrorsContainsGoalId() {
    controller.metricId = 2L;
    controller.year=2014;
    controller.value = 744L;


    controller.errors.put("goalId", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void FailWhenYearNull() throws Exception {
    controller.goalId = 12L;
    controller.metricId=21L;
    controller.value = 744L;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void postIfErrorsContainsYear() {
    controller.goalId = 12L;
    controller.metricId=21L;
    controller.value = 744L;

    controller.errors.put("year", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void FailWhenYearIsTooBig() throws Exception {
    controller.goalId = 12L;
    controller.metricId=21L;
    controller.year = UserAwareController.MAXIMUM_YEAR + 1;
    controller.value = 744L;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void FailWhenYearIsTooSmall() throws Exception {
    controller.goalId = 12L;
    controller.metricId=21L;
    controller.year = UserAwareController.MINIMUM_YEAR - 1;
    controller.value = 744L;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void FailWhenValueIsNull() throws Exception {
    controller.goalId = 12L;
    controller.metricId=2L;
    controller.year=2014;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne väärtus."));
  }

  @Test
  public void postIfErrorsContainsValue() {
    controller.goalId = 12L;
    controller.metricId=2L;
    controller.year=2014;

    controller.errors.put("value", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne väärtus."));
  }

}
