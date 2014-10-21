package controllers.admin.goals;

import controllers.ControllerTest;
import model.Goal;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ModifyTest extends ControllerTest<Modify> {

  @Before
  public void setUp() throws Exception {
    when(session.getAttribute("username")).thenReturn("Some username");
  }

  @Test
  public void getWhenObjectFound() {
    controller.id = 2L;

    when(hibernate.get(Goal.class, 2L)).thenReturn(new Goal("name", 10));

    assertRender(controller.get());

    assertEquals("name", controller.name);
    assertEquals(10, (int) controller.budget);
  }

  @Test
  public void getWhenObjectNotFound() {
    controller.id = 2L;
    when(hibernate.get(Goal.class, 2L)).thenReturn(null);

    assertRedirect(Add.class, controller.get());
  }

  @Test
  public void postNameTrimAndUpdateSuccess() {
    controller.id = 2L;
    controller.name = "\n name";
    controller.budget = 10;

    Goal goalBeingChanged = new Goal("TERE", 33);
    when(hibernate.get(Goal.class, 2L)).thenReturn(goalBeingChanged);

    assertRedirect(Home.class, controller.post());

    assertEquals(10, (int)goalBeingChanged.getBudget());
    assertEquals("name", goalBeingChanged.getName());
    verify(hibernate).update(goalBeingChanged);
  }

  @Test
  public void postIfNameNull() {
    controller.budget = 10;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void postIfNameIsEmpty() {
    controller.name = "";
    controller.budget = 2;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void postIfBudgetNull() {
    controller.name = "name";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
  }

  @Test
  public void postIfBudgetIsNegative() {
    controller.name = "name";
    controller.budget = -1;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
  }

  @Test
  public void postIfBudgetConversionFails() {
    controller.name = "name";
    controller.errors.put("budget", new NumberFormatException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
  }

  @Test
  public void postIfErrorsContainsName() {
    controller.budget = 1;
    controller.errors.put("name", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void postIfErrorsContainsBudget() {
    controller.name = "name";
    controller.errors.put("budget", new Exception());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
  }

  @Test
  public void postIfErrorContainsNameAndBudget() {
    controller.errors.put("budget", new RuntimeException());
    controller.errors.put("name", new RuntimeException());

    assertRender(controller.post());

    assertEquals(2, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
  }

  @Test
  public void postWithDuplicateGoal() {
    controller.name = "name";
    controller.budget = 1;
    controller.id = 2L;

    Goal expectedGoal = new Goal("goal", 100);

    when(hibernate.get(Goal.class, 2L)).thenReturn(expectedGoal);

    ConstraintViolationException expectedException = mock(ConstraintViolationException.class);
    doThrow(expectedException).when(hibernate).update(any(Goal.class));

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("See eesmärk on juba sisestatud."));
  }

  @Test(expected = RuntimeException.class)
  public void postCatchesOnlyConstraintViolationException() {
    controller.name = "name";
    controller.budget = 1;
    controller.id = 2L;

    Goal expectedGoal = new Goal("goal", 100);

    when(hibernate.get(Goal.class, 2L)).thenReturn(expectedGoal);

    Exception expectedException = new RuntimeException();
    doThrow(expectedException).when(hibernate).update(any(Goal.class));

    controller.post();
  }
}






