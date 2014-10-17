package controllers;

import model.Goal;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AddTest extends ControllerTest<Add> {

  @Test
  public void postIfNameIsNull() {
    controller.budget = 10;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void postIfNameIsEmpty() {
    controller.name = "";
    controller.budget = 300;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void postIfNameHasSpacesOnly() {
    controller.name = " \n\n \n    \r\n \r\n \n \n";
    controller.budget = 300;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void postIfBudgetIsNull() {
    controller.name = "abc";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
  }

  @Test
  public void postIfBudgetIsNegative() {
    controller.name = "abc";
    controller.budget = -1;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
  }

  @Test
  public void postIfBudgetConversionFails() {
    controller.name = "abc";
    controller.errors.put("budget", new NumberFormatException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
  }

  @Test
  public void postIfNameThrowsException() {
    controller.budget = 55;
    controller.errors.put("name", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void postIfBudgetThrowsException() {
    controller.name = "abc";
    controller.errors.put("budget", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
  }

  @Test
  public void postIfNameAndBudgetThrowException() {
    controller.errors.put("budget", new RuntimeException());
    controller.errors.put("name", new RuntimeException());

    assertRender(controller.post());

    assertEquals(2, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
  }

  @Test
  public void postNameTrimAndSaveSuccess() throws Exception {
    controller.name = "\n \r\n ab cd \n \r\n ";
    controller.budget = 111;

    assertRedirect(Goals.class, controller.post());

    Goal savedGoal = (Goal) getSavedEntity();

    assertEquals("ab cd", savedGoal.getName());
    assertEquals(111, (int) savedGoal.getBudget());
    verify(hibernate).save(savedGoal);
  }

  @Test
  public void postSaveDuplicate() {
    controller.name = "aaa aaa";
    controller.budget = 5555;

    doThrow(mock(ConstraintViolationException.class)).when(controller.hibernate).save(any(Goal.class));

    assertRender(controller.post());

    assertTrue(controller.errorsList.contains("See eesmärk on juba sisestatud."));
    assertEquals(1, controller.errorsList.size());

    Goal savedGoal = (Goal) getSavedEntity();

    assertEquals("aaa aaa", savedGoal.getName());
    assertEquals(5555, (int) savedGoal.getBudget());

  }

  @Test
  public void postSaveThrowsException() {
    controller.name = "34567 hh";
    controller.budget = 999999;

    doThrow(new RuntimeException()).when(controller.hibernate).save(any(Goal.class));

    assertRender(controller.post());

    assertTrue(controller.errorsList.contains("Tekkis viga."));
    assertEquals(1, controller.errorsList.size());

    Goal savedGoal = (Goal) getSavedEntity();

    assertEquals("34567 hh", savedGoal.getName());
    assertEquals(999999, (int) savedGoal.getBudget());
  }
}
