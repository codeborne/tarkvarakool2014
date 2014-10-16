package controllers;

import framework.Redirect;
import framework.Render;
import framework.Result;
import model.Goal;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AddTest {

  @Test
  public void addNullName() {
    Add add = new Add();
    add.name = null;
    add.budget = 300;

    Result result = add.post();
    assertTrue(result instanceof Render);

    assertTrue(add.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void addBlankName() {
    Add add = new Add();
    add.name = "";
    add.budget = 300;

    Result result = add.post();
    assertTrue(result instanceof Render);

    assertTrue(add.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void addSpacesOnlyName() {
    Add add = new Add();
    add.name = "     ";
    add.budget = 300;

    Result result = add.post();
    assertTrue(result instanceof Render);

    assertTrue(add.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void addNullBudget() {
    Add add = new Add();
    add.name = "abc";
    add.budget = null;

    Result result = add.post();
    assertTrue(result instanceof Render);

    assertTrue(add.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void addNegativeBudget() {
    Add add = new Add();
    add.name = "abc";
    add.budget = -1;

    Result result = add.post();
    assertTrue(result instanceof Render);

    assertTrue(add.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void addBudgetNumberFormatException() {
    Add add = new Add();
    add.errors.put("budget", new NumberFormatException());
    add.name = "abc";
    add.budget = 55;

    Result result = add.post();
    assertTrue(result instanceof Render);

    assertTrue(add.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void addNameException() {
    Add add = new Add();
    add.errors.put("name", new RuntimeException());
    add.name = "abc";
    add.budget = 55;

    Result result = add.post();
    assertTrue(result instanceof Render);

    assertTrue(add.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void addBudgetException() {
    Add add = new Add();
    add.errors.put("budget", new RuntimeException());
    add.name = "abc";
    add.budget = 55;

    Result result = add.post();
    assertTrue(result instanceof Render);

    assertTrue(add.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void testSaveSuccess() throws Exception {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.name = " \n  ab cd  \n\n \r\n";
    add.budget = 1;

    Result result = add.post();
    assertTrue(result instanceof Redirect);
    assertEquals("/goals", ((Redirect)result).getPath());

    ArgumentCaptor<Goal> captor = ArgumentCaptor.forClass(Goal.class);
    verify(add.hibernate).save(captor.capture());
    Goal capturedGoal = captor.getValue();
    assertEquals("ab cd", capturedGoal.getName());
    assertEquals(1, (int) capturedGoal.getBudget());
  }

  @Test
  public void saveDuplicate() {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.name = "aaa aaa";
    add.budget = 5555;

    doThrow(mock(ConstraintViolationException.class)).when(add.hibernate).save(any(Goal.class));

    Result result = add.post();
    assertTrue(result instanceof Render);

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
    add.hibernate = mock(Session.class);

    add.name = "34567 hh";
    add.budget = 999999;

    doThrow(new RuntimeException()).when(add.hibernate).save(any(Goal.class));

    Result result = add.post();
    assertTrue(result instanceof Render);

    assertTrue(add.errorsList.contains("Tekkis viga."));

    ArgumentCaptor<Goal> captor = ArgumentCaptor.forClass(Goal.class);
    verify(add.hibernate).save(captor.capture());
    Goal capturedGoal = captor.getValue();
    assertEquals("34567 hh", capturedGoal.getName());
    assertEquals(999999, (int) capturedGoal.getBudget());
  }
}