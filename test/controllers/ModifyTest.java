package controllers;

import model.Goal;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ModifyTest extends ControllerTest<Modify> {

  @Test
  public void testGetWhenObjectFound() {
    controller.id = 2L;

    when(hibernate.get(Goal.class, 2L)).thenReturn(new Goal("name", 10));

    assertRender(controller.get());

    assertEquals("name", controller.name);
    assertEquals(10, (int) controller.budget);
  }

  @Test
  public void testGetWhenObjectNotFound() {
    controller.id = 2L;
    when(hibernate.get(Goal.class, 2L)).thenReturn(null);

    assertRedirect("/add", controller.get());
  }

  @Test
  public void postTestWithNoErrors() {
    controller.id = 2L;
    controller.name = "name";
    controller.budget = 10;

    Goal expectedGoal = new Goal(controller.name, controller.budget);
    Goal goalBeingChanged = new Goal("name", 10);
    when(hibernate.get(Goal.class, 2L)).thenReturn(goalBeingChanged);

    assertRedirect(Goals.class, controller.post());

    assertEquals(expectedGoal.getBudget(), goalBeingChanged.getBudget());
    assertEquals(expectedGoal.getName(), goalBeingChanged.getName());
  }

  @Test
  public void postTestIfNameNull() {
    controller.name = null;
    controller.budget = 10;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void postTestIfNameIsEmpty() {
    controller.name = "";
    controller.budget = 2;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void postTestIfBudgetNull() {
    controller.name = "name";
    controller.budget = null;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void postTestIfBudgetIsNegative() {
    controller.name = "name";
    controller.budget = -1;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void postIfBudgetConversionFails() {
    controller.name = "name";
    controller.budget = 1;
    controller.errors.put("budget", new NumberFormatException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void postIfErrorsContainsName() {
    controller.name = "name";
    controller.budget = 1;
    controller.errors.put("name", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void postIfErrorsContainsBudget() {
    controller.name = "name";
    controller.budget = 1;
    controller.errors.put("budget", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void postWithDuplicateGoal() {
    controller.name = "name";
    controller.budget = 1;
    controller.id = 2L;

    Goal expectedGoal = new Goal("goal", 100);

    doReturn(expectedGoal).when(hibernate).get(Goal.class, 2L);

    ConstraintViolationException expectedException = mock(ConstraintViolationException.class);
    doThrow(expectedException).when(hibernate).update(any(Goal.class));

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("See eesmärk on juba sisestatud."));
  }

  @Test
  public void postUpdateFailsWithException() {
    controller.name = "name";
    controller.budget = 1;
    controller.id = 2L;

    Goal expectedGoal = new Goal("goal", 100);

    doReturn(expectedGoal).when(hibernate).get(Goal.class, 2L);

    Exception expectedException = new RuntimeException();
    doThrow(expectedException).when(hibernate).update(any(Goal.class));

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }
}






