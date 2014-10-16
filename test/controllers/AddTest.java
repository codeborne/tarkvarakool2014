package controllers;

import model.Goal;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AddTest extends ControllerTest<Add> {

  @Test
  public void addNullName() {
    Add add = new Add();
    add.name = null;
    add.budget = 300;

    assertRender(add.post());

    assertTrue(add.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void addBlankName() {
    Add add = new Add();
    add.name = "";
    add.budget = 300;

    assertRender(add.post());

    assertTrue(add.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void addSpacesOnlyName() {
    Add add = new Add();
    add.name = " \n\n \n    \r\n \r\n \n \n";
    add.budget = 300;

    assertRender(add.post());

    assertTrue(add.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void addNullBudget() {
    Add add = new Add();
    add.name = "abc";
    add.budget = null;

    assertRender(add.post());

    assertTrue(add.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void addNegativeBudget() {
    Add add = new Add();
    add.name = "abc";
    add.budget = -1;

    assertRender(add.post());

    assertTrue(add.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void addBudgetNumberFormatException() {
    Add add = new Add();
    add.name = "abc";
    add.budget = 55;
    add.errors.put("budget", new NumberFormatException());

    assertRender(add.post());

    assertTrue(add.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void addNameException() {
    Add add = new Add();
    add.name = "abc";
    add.budget = 55;
    add.errors.put("name", new RuntimeException());

    assertRender(add.post());

    assertTrue(add.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void addBudgetException() {
    Add add = new Add();
    add.name = "abc";
    add.budget = 55;
    add.errors.put("budget", new RuntimeException());

    assertRender(add.post());

    assertTrue(add.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void testSaveSuccessAndNameTrim() throws Exception {
    Add add = new Add();
    add.name = "\n \r\n ab cd \n \r\n ";
    add.budget = 111;
    add.hibernate = hibernate;

    assertRedirect(Goals.class, add.post());

    ArgumentCaptor<Goal> captor = ArgumentCaptor.forClass(Goal.class);
    verify(add.hibernate).save(captor.capture());
    Goal capturedGoal = captor.getValue();
    assertEquals("ab cd", capturedGoal.getName());
    assertEquals(111, (int) capturedGoal.getBudget());
  }

  @Test
  public void saveDuplicate() {
    Add add = new Add();
    add.name = "aaa aaa";
    add.budget = 5555;
    add.hibernate = hibernate;

    doThrow(mock(ConstraintViolationException.class)).when(add.hibernate).save(any(Goal.class));

    assertRender(add.post());

    assertTrue(add.errorsList.contains("See eesmärk on juba sisestatud."));

    ArgumentCaptor<Goal> captor = ArgumentCaptor.forClass(Goal.class);
    verify(add.hibernate).save(captor.capture());
    Goal capturedGoal = captor.getValue();
    assertEquals("aaa aaa", capturedGoal.getName());
    assertEquals(5555, (int) capturedGoal.getBudget());
  }

  @Test
  public void saveException() {
    Add add = new Add();
    add.name = "34567 hh";
    add.budget = 999999;
    add.hibernate = hibernate;

    doThrow(new RuntimeException()).when(add.hibernate).save(any(Goal.class));

    assertRender(add.post());

    assertTrue(add.errorsList.contains("Tekkis viga."));

    ArgumentCaptor<Goal> captor = ArgumentCaptor.forClass(Goal.class);
    verify(add.hibernate).save(captor.capture());
    Goal capturedGoal = captor.getValue();
    assertEquals("34567 hh", capturedGoal.getName());
    assertEquals(999999, (int) capturedGoal.getBudget());
  }
}
