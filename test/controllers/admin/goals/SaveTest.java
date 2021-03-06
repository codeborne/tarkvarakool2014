package controllers.admin.goals;

import controllers.ControllerTest;
import model.Goal;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SaveTest extends ControllerTest<Save> {

  @Before
  public void setUp() throws Exception {
    when(request.getPathInfo()).thenReturn("admin/");
  }

  @Test
  public void postIfNameNull() {
    controller.budget = 10;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertGoal")));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfNameIsEmpty() {
    controller.name = "";
    controller.budget = 2;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertGoal")));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfNameHasSpacesOnly() {
    controller.name = " \n\n \n    \r\n \r\n \n \n";
    controller.budget = 300;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertGoal")));
    verify(hibernate, never()).save(any(Goal.class));
  }


  @Test
  public void postIfBudgetNull() {
    controller.name = "name";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertBudget")));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfBudgetIsNegative() {
    controller.name = "name";
    controller.budget = -1;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertBudget")));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfInputFieldNameHasErrors() {
    controller.budget = 1;
    controller.errors.put("name", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertGoal")));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfInputFieldCommentHasErrors() {
    controller.budget = 1;
    controller.name = "name";
    controller.errors.put("comment", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertComment")));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfInputFieldBudgetHasErrors() {
    controller.name = "name";
    controller.errors.put("budget", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertBudget")));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postIfInputFieldsNameCommentAndBudgetHaveErrors() {
    controller.errors.put("budget", new RuntimeException());
    controller.errors.put("comment", new RuntimeException());
    controller.errors.put("name", new RuntimeException());

    assertRender(controller.post());

    assertEquals(3, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertGoal")));
    assertTrue(controller.errorsList.contains(messages.get("errorInsertComment")));
    assertTrue(controller.errorsList.contains(messages.get("errorInsertBudget")));
    verify(hibernate, never()).save(any(Goal.class));
  }

  @Test
  public void postWithDuplicateGoalThrowsConstraintViolation() {
    controller.name = "asd";
    controller.budget = 123;
    when(hibernate.createCriteria(Goal.class).list()).thenReturn(asList(new Goal("some goal", 1000), new Goal("goal", 100)));

    doThrow(mock(ConstraintViolationException.class)).when(hibernate).save(any(Goal.class));

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains(messages.get("errorInsertedGoal")));
    verify(transaction).rollback();
  }

  @Test
  public void postIfNewGoalIsAdded() {
    controller.name = "asd";
    controller.budget = 123;
    controller.comment = "";
    when(hibernate.createCriteria(Goal.class).list()).thenReturn(asList(new Goal("some goal", 1000), new Goal("goal", 100)));

    assertRender(controller.post());

    Goal savedGoal = getSavedEntity();

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
    when(hibernate.createCriteria(Goal.class).list()).thenReturn(asList(new Goal("some goal", 1000)));

    assertRender(controller.post());

    Goal savedGoal = getSavedEntity();

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
    Goal updatedGoal = getUpdatedEntity();

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
    when(hibernate.get(Goal.class, 4L )).thenReturn(null);

    when(hibernate.createCriteria(Goal.class).list()).thenReturn(asList(new Goal("some goal", 1000)));

    assertRender(controller.post());
    Goal goal = getSavedEntity();

    assertEquals("kool",goal.getName() );
    assertEquals("maja",goal.getComment());
    assertEquals(41,(int)goal.getBudget());
    assertEquals(2,(int)goal.getSequenceNumber());
  }
}
