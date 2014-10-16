package controllers;

import model.Goal;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AddTest extends ControllerTest<Add> {

  @Test
  public void addNullName() {
    controller.name = null;
    controller.budget = 300;

    assertRender(controller.post());

    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void addBlankName() {
    controller.name = "";
    controller.budget = 300;

    assertRender(controller.post());

    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void addSpacesOnlyName() {
    controller.name = " \n\n \n    \r\n \r\n \n \n";
    controller.budget = 300;

    assertRender(controller.post());

    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void addNullBudget() {
    controller.name = "abc";
    controller.budget = null;

    assertRender(controller.post());

    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void addNegativeBudget() {
    controller.name = "abc";
    controller.budget = -1;

    assertRender(controller.post());

    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void addBudgetNumberFormatException() {
    controller.name = "abc";
    controller.budget = 55;
    controller.errors.put("budget", new NumberFormatException());

    assertRender(controller.post());

    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void addNameException() {
    controller.name = "abc";
    controller.budget = 55;
    controller.errors.put("name", new RuntimeException());

    assertRender(controller.post());

    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void addBudgetException() {
    controller.name = "abc";
    controller.budget = 55;
    controller.errors.put("budget", new RuntimeException());

    assertRender(controller.post());

    assertTrue(controller.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void testSaveSuccessAndNameTrim() throws Exception {
    controller.name = "\n \r\n ab cd \n \r\n ";
    controller.budget = 111;
    controller.hibernate = hibernate;

    assertRedirect(Goals.class, controller.post());

    Goal savedGoal = (Goal)getSavedEntity();

    assertEquals("ab cd", savedGoal.getName());
    assertEquals(111, (int) savedGoal.getBudget());
  }

  @Test
  public void saveDuplicate() {
    controller.name = "aaa aaa";
    controller.budget = 5555;
    controller.hibernate = hibernate;

    doThrow(mock(ConstraintViolationException.class)).when(controller.hibernate).save(any(Goal.class));

    assertRender(controller.post());

    assertTrue(controller.errorsList.contains("See eesmärk on juba sisestatud."));

    Goal savedGoal = (Goal)getSavedEntity();
    assertEquals("aaa aaa", savedGoal.getName());
    assertEquals(5555, (int) savedGoal.getBudget());
  }

  @Test
  public void saveException() {
    controller.name = "34567 hh";
    controller.budget = 999999;
    controller.hibernate = hibernate;

    doThrow(new RuntimeException()).when(controller.hibernate).save(any(Goal.class));

    assertRender(controller.post());

    assertTrue(controller.errorsList.contains("Tekkis viga."));

    Goal savedGoal = (Goal)getSavedEntity();
    assertEquals("34567 hh", savedGoal.getName());
    assertEquals(999999, (int) savedGoal.getBudget());
  }
}
