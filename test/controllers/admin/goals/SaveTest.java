package controllers.admin.goals;

import controllers.ControllerTest;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SaveTest extends ControllerTest<SaveTest.TestSave> {

  @Before
  public void setUp() throws Exception {
    controller = spy(controller);
  }

  @Test
  public void postIfNameNull() {
    controller.budget = 10;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
    verify(controller, never()).save();
  }

  @Test
  public void postIfNameIsEmpty() {
    controller.name = "";
    controller.budget = 2;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
    verify(controller, never()).save();
  }

  @Test
  public void postIfNameHasSpacesOnly() {
    controller.name = " \n\n \n    \r\n \r\n \n \n";
    controller.budget = 300;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
    verify(controller, never()).save();
  }


  @Test
  public void postIfBudgetNull() {
    controller.name = "name";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
    verify(controller, never()).save();
  }

  @Test
  public void postIfBudgetIsNegative() {
    controller.name = "name";
    controller.budget = -1;

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
    verify(controller, never()).save();
  }

  @Test
  public void postIfInputFieldNameHasErrors() {
    controller.budget = 1;
    controller.errors.put("name", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
    verify(controller, never()).save();
  }

  @Test
  public void postIfInputFieldBudgetHasErrors() {
    controller.name = "name";
    controller.errors.put("budget", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
    verify(controller, never()).save();
  }

  @Test
  public void postIfInputFieldsNameAndBudgetHaveErrors() {
    controller.errors.put("budget", new RuntimeException());
    controller.errors.put("name", new RuntimeException());

    assertRender(controller.post());

    assertEquals(2, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage eesmärk."));
    assertTrue(controller.errorsList.contains("Sisestage korrektne eelarve (1 - 2 147 483 647)."));
    verify(controller, never()).save();
  }

  @Test
  public void postWithDuplicateGoalThrowsConstraintViolation() {
    controller.name = "asd";
    controller.budget = 123;
    doThrow(mock(ConstraintViolationException.class)).when(controller).save();

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("See eesmärk on juba sisestatud."));
    verify(transaction).rollback();
  }

  @Test
  public void postCallsSave() {
    controller.name = "asd";
    controller.budget = 123;

    assertRedirect(Home.class, controller.post());

    verify(controller).save();
  }

  @Test
  public void postCallsSaveWithTrimmedName() {
    controller.name = "\n \r\n ab cd \n \r\n ";;
    controller.budget = 123;

    assertRedirect(Home.class, controller.post());

    assertEquals("ab cd",controller.name);
    verify(controller).save();
  }

  public static class TestSave extends Save {
    @Override
    protected void save() {
    }
  }
}
