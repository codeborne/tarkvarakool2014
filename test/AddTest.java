import controllers.Add;
import model.Goal;
import org.hibernate.Session;
import org.hibernate.mapping.Array;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddTest {

  @Test
  public void addNullName() {
    Add add = new Add();
    add.hibernate=mock(Session.class);
    when(add.hibernate.save(Goal.class)).thenThrow(new RuntimeException("Save shouldn't be called"));

    add.name = null;
    add.budget = 300;

    add.post();
    assertEquals(Arrays.asList("Sisestage eesmärk."), add.errorsList);
  }

  @Test
  public void addBlankName() {
    Add add = new Add();
    add.hibernate=mock(Session.class);
    when(add.hibernate.save(Goal.class)).thenThrow(new RuntimeException("Save shouldn't be called"));

    add.name = "";
    add.budget = 300;

    add.post();
    assertEquals(Arrays.asList("Sisestage eesmärk."), add.errorsList);
  }

  @Test
  public void addNullBudget() {
    Add add = new Add();
    add.hibernate=mock(Session.class);
    when(add.hibernate.save(Goal.class)).thenThrow(new RuntimeException("Save shouldn't be called"));

    add.name = "abc";
    add.budget = null;

    add.post();
    assertEquals(Arrays.asList("Sisestage korrektne eelarve."), add.errorsList);
  }

  @Test
  public void addNegativeBudget() {
    Add add = new Add();
    add.hibernate=mock(Session.class);
    when(add.hibernate.save(Goal.class)).thenThrow(new RuntimeException("Save shouldn't be called"));

    add.name = "abc";
    add.budget = -1;

    add.post();
    assertEquals(Arrays.asList("Sisestage korrektne eelarve."), add.errorsList);
  }

  @Test
  public void addBudgetNumberFormatException() {
    Add add = new Add();
    add.hibernate=mock(Session.class);
    when(add.hibernate.save(Goal.class)).thenThrow(new RuntimeException("Save shouldn't be called"));

    add.errors.put("budget", new NumberFormatException());
    add.name = "abc";
    add.budget = 55;

    add.post();

    assertEquals(Arrays.asList("Sisestage korrektne eelarve."), add.errorsList);
  }

  @Test
  public void addNameException() {
    Add add = new Add();
    add.hibernate = mock(Session.class);
    when(add.hibernate.save(Goal.class)).thenThrow(new RuntimeException("Save shouldn't be called"));

    add.errors.put("name", new RuntimeException());
    add.name = "abc";
    add.budget = 55;

    add.post();

    assertEquals(Arrays.asList("Tekkis viga."), add.errorsList);
  }

  @Test
  public void addBudgetException() {
    Add add = new Add();
    add.hibernate = mock(Session.class);
    when(add.hibernate.save(Goal.class)).thenThrow(new RuntimeException("Save shouldn't be called"));

    add.errors.put("budget", new RuntimeException());
    add.name = "abc";
    add.budget = 55;

    add.post();

    assertEquals(Arrays.asList("Tekkis viga."), add.errorsList);
  }


}
