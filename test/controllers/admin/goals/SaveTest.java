package controllers.admin.goals;

import controllers.ControllerTest;
import model.Goal;
import org.hibernate.Criteria;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SaveTest extends ControllerTest<Save> {

  @Test
  public void postIfNameNull() {
    controller.budget = 10;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfNameIsEmpty() {
    controller.name = "";
    controller.budget = 2;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfNameHasSpacesOnly() {
    controller.name = " \n\n \n    \r\n \r\n \n \n";
    controller.budget = 300;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
    verify(hibernate, never()).save(any(Goal.class));
  }


  @Test
  public void postIfBudgetNull() {
    controller.name = "name";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve."));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfBudgetIsNegative() {
    controller.name = "name";
    controller.budget = -1;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve."));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfInputFieldNameHasErrors() {
    controller.budget = 1;
    controller.errors.put("name", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfInputFieldCommentHasErrors() {
    controller.budget = 1;
    controller.name = "name";
    controller.errors.put("comment", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage kommentaar."));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfInputFieldBudgetHasErrors() {
    controller.name = "name";
    controller.errors.put("budget", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve."));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfInputFieldsNameCommentAndBudgetHaveErrors() {
    controller.errors.put("budget", new RuntimeException());
    controller.errors.put("comment", new RuntimeException());
    controller.errors.put("name", new RuntimeException());

    assertRender(controller.post());

    assertEquals(3, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
    assertTrue(controller.errorsList.contains("Sisestage kommentaar."));
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve."));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postWithDuplicateGoalThrowsConstraintViolation() {
    controller.name = "asd";
    controller.budget = 123;
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Goal.class)).thenReturn(criteria);
    when(criteria.list()).thenReturn(Arrays.asList(new Goal("some goal", 1000), new Goal("goal", 100)));

    doThrow(mock(ConstraintViolationException.class)).when(hibernate).save(any(Goal.class));

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("See eesmärk on juba sisestatud."));
    verify(transaction).rollback();
  }

  @Test
  public void postIfNewGoalIsAdded() {
    controller.name = "asd";
    controller.budget = 123;
    controller.comment = "";
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Goal.class)).thenReturn(criteria);
    when(criteria.list()).thenReturn(Arrays.asList(new Goal("some goal", 1000), new Goal("goal", 100)));

    assertRender(controller.post());

    Goal savedGoal = (Goal) getSavedEntity();

    assertEquals("asd", savedGoal.getName());
    assertEquals(123, (int) savedGoal.getBudget());
    assertEquals(3,(int) savedGoal.getSequenceNumber());
    assertEquals("",savedGoal.getComment());
  }

  @Test
  public void postIfNewGoalIsAddedAndNameTrimmed() {
    controller.name = "\n \r\n ab cd \n \r\n ";
    controller.comment = "\ntest\n  ";
    controller.budget = 123;
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Goal.class)).thenReturn(criteria);
    when(criteria.list()).thenReturn(Arrays.asList(new Goal("some goal", 1000)));

    assertRender(controller.post());

    Goal savedGoal = (Goal) getSavedEntity();

    assertEquals("ab cd",savedGoal.getName());
    assertEquals("test",savedGoal.getComment());
    assertEquals(2,(int) savedGoal.getSequenceNumber());
  }


  @Test
  public void postIfGoalIsSuccessfullyModified() {
    controller.id = 2L;
    controller.name = "name";
    controller.budget = 10;
    controller.comment = "abc";

    Goal goalBeingChanged = new Goal("TERE", "tre",34, 1);
    when(hibernate.get(Goal.class, 2L)).thenReturn(goalBeingChanged);

    assertRender(controller.post());
    Goal updatedGoal = (Goal) getUpdatedEntity();

    assertEquals(10, (int)updatedGoal.getBudget());
    assertEquals("name", updatedGoal.getName());
    assertEquals("abc", updatedGoal.getComment());
  }

  @Test
  public void postIfModifyingNonExistingGoal() throws Exception {
    controller.id = 4L;
    controller.name = "kool";
    controller.budget = 41;
    controller.comment = "maja";

    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Goal.class)).thenReturn(criteria);
    when(hibernate.get(Goal.class, 4L )).thenReturn(null);
    when(criteria.list()).thenReturn(Arrays.asList(new Goal("some goal", 1000)));

    assertRender(controller.post());
    Goal goal = (Goal) getSavedEntity();

    assertEquals("kool",goal.getName() );
    assertEquals("maja",goal.getComment());
    assertEquals(41,(int)goal.getBudget());
    assertEquals(2,(int)goal.getSequenceNumber());
  }
}
