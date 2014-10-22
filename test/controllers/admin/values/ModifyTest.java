package controllers.admin.values;

import controllers.ControllerTest;
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
  public void postIfErrorsContainsMetricId() {
    controller.goalId = 2L;
    controller.year=2014;
    controller.value = 744L;


    controller.errors.put("metricId", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }
}
