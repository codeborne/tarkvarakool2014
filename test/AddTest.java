import controllers.Add;
import framework.Redirect;
import model.Goal;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AddTest {

  @Test
  public void addNullName() {
    Add add = new Add();
    add.name = null;
    add.budget = 300;

    add.post();

    assertTrue(add.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void addBlankName() {
    Add add = new Add();
    add.name = "";
    add.budget = 300;

    add.post();

    assertTrue(add.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void addSpacesOnlyName() {
    Add add = new Add();
    add.name = "     ";
    add.budget = 300;

    add.post();

    assertTrue(add.errorsList.contains("Sisestage eesmärk."));
  }

  @Test
  public void addNullBudget() {
    Add add = new Add();
    add.name = "abc";
    add.budget = null;

    add.post();

    assertTrue(add.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void addNegativeBudget() {
    Add add = new Add();
    add.name = "abc";
    add.budget = -1;

    add.post();

    assertTrue(add.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void addBudgetNumberFormatException() {
    Add add = new Add();
    add.errors.put("budget", new NumberFormatException());
    add.name = "abc";
    add.budget = 55;

    add.post();

    assertTrue(add.errorsList.contains("Sisestage korrektne eelarve."));
  }

  @Test
  public void addNameException() {
    Add add = new Add();
    add.errors.put("name", new RuntimeException());
    add.name = "abc";
    add.budget = 55;

    add.post();

    assertTrue(add.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void addBudgetException() {
    Add add = new Add();
    add.errors.put("budget", new RuntimeException());
    add.name = "abc";
    add.budget = 55;

    add.post();

    assertTrue(add.errorsList.contains("Tekkis viga."));
  }

  @Test
  public void testSaveSuccess() throws Exception {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.name = "   abcd   ";
    add.budget = 1;

    try {
      add.post();
      fail();
    } catch (Redirect e) {
      assertEquals("goals", e.getMessage());
    }

    ArgumentCaptor<Goal> argument = ArgumentCaptor.forClass(Goal.class);
    verify(add.hibernate).save(argument.capture());
    assertEquals("abcd", argument.getValue().getName());
    assertEquals(1, (int) argument.getValue().getBudget());
  }

  @Test
  public void saveDuplicate() {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.name = "aaa aaa";
    add.budget = 5555;

    doThrow(mock(ConstraintViolationException.class)).when(add.hibernate).save(any(Goal.class));
    add.post();

    assertTrue(add.errorsList.contains("See eesmärk on juba sisestatud."));

    ArgumentCaptor<Goal> argument = ArgumentCaptor.forClass(Goal.class);
    verify(add.hibernate).save(argument.capture());
    assertEquals("aaa aaa", argument.getValue().getName());
    assertEquals(5555, (int) argument.getValue().getBudget());
  }

  @Test
  public void saveException() {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.name = "34567 hh";
    add.budget = 999999;

    doThrow(new RuntimeException()).when(add.hibernate).save(any(Goal.class));
    add.post();

    assertTrue(add.errorsList.contains("Tekkis viga."));

    ArgumentCaptor<Goal> argument = ArgumentCaptor.forClass(Goal.class);
    verify(add.hibernate).save(argument.capture());
    assertEquals("34567 hh", argument.getValue().getName());
    assertEquals(999999, (int) argument.getValue().getBudget());
  }
}
