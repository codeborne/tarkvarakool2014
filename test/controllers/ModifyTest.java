package controllers;

import framework.Redirect;
import model.Goal;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ModifyTest {

  @Test
  public void testGetWhenObjectFound() {
    Session hibernate = mock(Session.class);

    Modify modify = new Modify();
    modify.id = 2L;
    modify.hibernate = hibernate;

    Goal expectedGoal = new Goal("name", 300);
    when(hibernate.get(Goal.class, 2L)).thenReturn(expectedGoal);

    modify.get();

    assertEquals("name", modify.name);
    assertEquals(300, (int)modify.budget);
  }

  @Test
  public void testGetWhenObjectNotFound() {
    Session hibernate = mock(Session.class);

    Modify modify = new Modify();
    modify.id = 2L;
    modify.hibernate = hibernate;

    when(hibernate.get(Goal.class, 2L)).thenReturn(null);

    try {
      modify.get();
      fail();
    } catch (Redirect e) {
      assertEquals("add", e.getMessage());
    }
  }

  @Test
  public void postTestWithNoErrors() {
    Session hibernate = mock(Session.class);
    Modify modify = new Modify();
    modify.id = 2L;
    modify.hibernate = hibernate;
    modify.name = "nimi";
    modify.budget = 600;
    Goal expectedGoal = new Goal(modify.name, modify.budget);

    Goal goalBeingChanged = new Goal("nimi", 500);
    when(hibernate.get(Goal.class, 2L)).thenReturn(goalBeingChanged);

    try {
      modify.post();
      fail();
    } catch (Redirect e) {
      Assert.assertEquals("goals", e.getMessage());
      Assert.assertEquals(expectedGoal.getName(), goalBeingChanged.getName());
      Assert.assertEquals(expectedGoal.getBudget(), goalBeingChanged.getBudget());

    }

  }

  @Test
  public void postTestIfNameNull() {
    Modify modify = new Modify();
    modify.name = null;
    modify.budget = 2;

    modify.post();
    assertEquals(1, modify.errorsList.size());
    assertTrue(modify.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void postTestIfNameIsEmpty() {
    Modify modify = new Modify();
    modify.name = "";
    modify.budget = 2;

    modify.post();
    assertEquals(1, modify.errorsList.size());
    assertTrue(modify.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void postTestIfBudgetNull() {
    Modify modify = new Modify();
    modify.name = "name";
    modify.budget = null;

    modify.post();
    assertEquals(1, modify.errorsList.size());
    assertTrue(modify.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void postTestIfBudgetIsNegative() {
    Modify modify = new Modify();
    modify.name = "name";
    modify.budget = -1;

    modify.post();
    assertEquals(1, modify.errorsList.size());
    assertTrue(modify.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void postIfBudgetConversionFails(){
    Modify modify = new Modify();
    modify.name = "name";
    modify.budget = 1;
    modify.errors.put("budget", new NumberFormatException());

    modify.post();
    assertEquals(1, modify.errorsList.size());
    assertTrue(modify.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void postIfErrorsContainsName() {
    Modify modify = new Modify();
    modify.name="name";
    modify.budget = 1;
    modify.errors.put("name", new RuntimeException());

    modify.post();
    assertEquals(1, modify.errorsList.size());
    assertTrue(modify.errorsList.contains("Tekkis viga."));

  }

  @Test
  public void postIfErrorsContainsBudget() {
    Modify modify = new Modify();
    modify.name="name";
    modify.budget = 1;
    modify.errors.put("budget", new Exception());

    modify.post();
    assertEquals(1, modify.errorsList.size());
    assertTrue(modify.errorsList.contains("Tekkis viga."));

  }

  @Test
  public void postWithDuplicateGoal() {
    Modify modify = new Modify();
    modify.name = "name";
    modify.budget = 1;
    modify.id = 2L;
    Session hibernate = mock(Session.class);
    modify.hibernate = hibernate;

    Goal expectedGoal = new Goal("goal", 100);

    doReturn(expectedGoal).when(hibernate).get(Goal.class, 2L);

    ConstraintViolationException expectedException = mock(ConstraintViolationException.class);
    doThrow(expectedException).when(hibernate).update(any(Goal.class));

    modify.post();
    assertEquals(1, modify.errorsList.size());
    assertTrue(modify.errorsList.contains("See eesmärk on juba sisestatud."));
  }

  @Test
  public void postUpdateFailsWithException() {
    Modify modify = new Modify();
    modify.name = "name";
    modify.budget = 1;
    modify.id = 2L;
    Session hibernate = mock(Session.class);
    modify.hibernate = hibernate;

    Goal expectedGoal = new Goal("goal", 100);

    doReturn(expectedGoal).when(hibernate).get(Goal.class, 2L);

    Exception expectedException = new RuntimeException();
    doThrow(expectedException).when(hibernate).update(any(Goal.class));

    modify.post();
    assertEquals(1, modify.errorsList.size());
    assertTrue(modify.errorsList.contains("Tekkis viga."));
  }
}






