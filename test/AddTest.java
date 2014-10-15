import controllers.Add;
import framework.Redirect;
import model.Goal;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;

import java.util.Arrays;

import static framework.HibernateMockHelper.*;
import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AddTest {

  @Test
  public void addNullName() {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.name = null;
    add.budget = 300;

    add.post();

    verifyZeroInteractions(add.hibernate);
    assertEquals(Arrays.asList("Sisestage eesmärk."), add.errorsList);
  }

  @Test
  public void addBlankName() {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.name = "";
    add.budget = 300;

    add.post();

    verifyZeroInteractions(add.hibernate);
    assertEquals(Arrays.asList("Sisestage eesmärk."), add.errorsList);
  }

  @Test
  public void addNullBudget() {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.name = "abc";
    add.budget = null;

    add.post();

    verifyZeroInteractions(add.hibernate);
    assertEquals(Arrays.asList("Sisestage korrektne eelarve."), add.errorsList);
  }

  @Test
  public void addNegativeBudget() {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.name = "abc";
    add.budget = -1;

    add.post();

    verifyZeroInteractions(add.hibernate);
    assertEquals(Arrays.asList("Sisestage korrektne eelarve."), add.errorsList);
  }

  @Test
  public void addBudgetNumberFormatException() {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.errors.put("budget", new NumberFormatException());
    add.name = "abc";
    add.budget = 55;

    add.post();

    verifyZeroInteractions(add.hibernate);
    assertEquals(Arrays.asList("Sisestage korrektne eelarve."), add.errorsList);
  }

  @Test
  public void addNameException() {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.errors.put("name", new RuntimeException());
    add.name = "abc";
    add.budget = 55;

    add.post();

    verifyZeroInteractions(add.hibernate);
    assertEquals(Arrays.asList("Tekkis viga."), add.errorsList);
  }

  @Test
  public void addBudgetException() {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.errors.put("budget", new RuntimeException());
    add.name = "abc";
    add.budget = 55;

    add.post();

    verifyZeroInteractions(add.hibernate);
    assertEquals(Arrays.asList("Tekkis viga."), add.errorsList);
  }

  @Test
  public void testSaveSuccess() throws Exception {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.name = "abc";
    add.budget = 55;

    try {
      add.post();
      fail();
    } catch (Redirect e) {
      assertEquals("goals", e.getMessage());
    }

    verify(add.hibernate).save(any(Goal.class));
  }

  @Test
  public void saveDuplicate() {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.name = "abc";
    add.budget = 55;

    when(add.hibernate.save(any(Goal.class))).thenThrow(mock(ConstraintViolationException.class));
    add.post();

    assertEquals(Arrays.asList("See eesmärk on juba sisestatud."), add.errorsList);
  }

  @Test
  public void saveException() {
    Add add = new Add();
    add.hibernate = mock(Session.class);

    add.name = "abc";
    add.budget = 55;

    when(add.hibernate.save(any(Goal.class))).thenThrow(new RuntimeException());
    add.post();

    assertEquals(Arrays.asList("Tekkis viga."), add.errorsList);
  }
}
