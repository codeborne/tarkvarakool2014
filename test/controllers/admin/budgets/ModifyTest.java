package controllers.admin.budgets;

import controllers.ControllerTest;
import model.Goal;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ModifyTest extends ControllerTest<Modify> {

  @Before
  public void setUp() throws Exception {
    when(request.getPathInfo()).thenReturn("admin/");
  }

  @Test
  public void postIfErrorsContainsGoalId() throws Exception {
    controller.yearlyBudget = 123L;
    controller.errors.put("goalId", new RuntimeException());

    assertJson(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
  }

  @Test
  public void postIfGoalIdIsNull() throws Exception {
    controller.yearlyBudget = 145L;
    controller.year = 2019;

    assertJson(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
  }

  @Test
  public void postIfErrorsContainsYear() throws Exception {
    controller.yearlyBudget = 123L;
    controller.errors.put("year", new RuntimeException());

    assertJson(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
  }

  @Test
  public void postIfYearIsNull() throws Exception {
    controller.yearlyBudget = 145L;

    assertJson(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));

  }

  @Test
  public void postIfYearIsBiggerThanAllowed() throws Exception {
    controller.goalId = 45L;
    controller.year = 2060;
    controller.yearlyBudget = 150L;

    assertJson(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));

  }

  @Test
  public void postIfYearIsSmallerThanAllowed() throws Exception {
    controller.goalId = 45L;
    controller.year = 2010;
    controller.yearlyBudget = 150L;

    assertJson(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));

  }

  @Test
  public void postIfErrorsContainsYearlyBudget() throws Exception {
    controller.goalId = 45L;
    controller.year = 2017;
    controller.errors.put("yearlyBudget", new RuntimeException());

    assertJson(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertValue")));
  }


  @Test
  public void postIfYearlyBudgetIsNegative() throws Exception {
    controller.goalId = 45L;
    controller.year = 2017;
    controller.yearlyBudget = -14L;

    assertJson(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertValue")));
  }

  @Test
  public void postSuccess() throws Exception {
    controller.goalId = 1L;
    controller.year = 2015;
    controller.yearlyBudget = 555L;
    when(hibernate.createCriteria(Goal.class).add(any(SimpleExpression.class)).list()).thenReturn(asList(new Goal("some goal", 1000)));

    assertJson(controller.post());

    Goal updatedGoal = getUpdatedEntity();
    assertTrue(controller.errorsList.isEmpty());
    assertTrue(updatedGoal.getYearlyBudgets().containsKey(2015));
    Assert.assertEquals((Long) 555L, updatedGoal.getYearlyBudgets().get(2015));
    verify(hibernate).update(updatedGoal);

  }

  @Test
  public void postIfYearlyBudgetIsNull() throws Exception {
    controller.goalId = 45L;
    controller.year = 2017;
    when(hibernate.createCriteria(Goal.class).add(any(SimpleExpression.class)).list()).thenReturn(asList(new Goal("some goal", 1000)));

    assertJson(controller.post());

    Goal updatedGoal = getUpdatedEntity();
    assertTrue(controller.errorsList.isEmpty());
    assertTrue(updatedGoal.getYearlyBudgets().containsKey(2017));
    Assert.assertEquals(null, updatedGoal.getYearlyBudgets().get(2015));
    verify(hibernate).update(updatedGoal);
  }

  @Test
  public void postFailure() throws Exception {
    controller.goalId = 1L;
    controller.year = 2015;
    controller.yearlyBudget = 555L;
    when(hibernate.createCriteria(Goal.class).add(any(SimpleExpression.class)).list()).thenReturn(asList(
      new Goal("some goal", 1000),
      new Goal("second goal", 2000)));

    assertJson(controller.post());

    assertEquals(1,controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("error")));
  }
}
