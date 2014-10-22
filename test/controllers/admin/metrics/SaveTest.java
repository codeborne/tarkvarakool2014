package controllers.admin.metrics;

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
  public void postIfNameIsNull() {
    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage mõõdik."));
    verify(controller, never()).save();
  }

  @Test
  public void postIfNameHasOnlySpaces() {
    controller.name = " \n\n \n    \r\n \r\n \n \n";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage mõõdik."));
    verify(controller, never()).save();
  }

  @Test
  public void postIfNameIsEmpty() {
    controller.name = "";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage mõõdik."));
    verify(controller, never()).save();
  }

  @Test
  public void postIfPublicDescriptionThrowsException() {
    controller.name = "metric";
    controller.errors.put("publicDescription", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga avalikus kirjelduses"));
    verify(controller, never()).save();
  }

  @Test
  public void postIfPrivateDescriptionThrowsException() {
    controller.name = "metric";
    controller.errors.put("privateDescription", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga mitteavalikus kirjelduses"));
    verify(controller, never()).save();
  }

  @Test
  public void postIfStartLevelIsNotNumber() {
    controller.name = "metric";
    controller.errors.put("startLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Algtase peab olema number"));
    verify(controller, never()).save();
  }

  @Test
  public void postIfCommentOnStartLevelThrowsException() {
    controller.name = "metric";
    controller.errors.put("commentOnStartLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga algtaseme kommentaaris"));
    verify(controller, never()).save();
  }

  @Test
  public void postIfTargetLevelIsNotNumber() {
    controller.name = "metric";
    controller.errors.put("targetLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sihttase peab olema number"));
    verify(controller, never()).save();
  }

  @Test
  public void postIfCommentOnTargetLevelThrowsException() {
    controller.name = "metric";
    controller.errors.put("commentOnTargetLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga sihttaseme kommentaaris"));
    verify(controller, never()).save();
  }

  @Test
  public void postIfInfoSourceThrowsException() {
    controller.name = "metric";
    controller.errors.put("infoSource", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga infoallika sisestamisel"));
    verify(controller, never()).save();
  }

  @Test
  public void postIfInstitutionToReportThrowsException() {
    controller.name = "metric";
    controller.errors.put("institutionToReport", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga raporteeritava asutuse sisestamisel"));
    verify(controller, never()).save();
  }

  @Test
  public void postCallsSave() {
    controller.name = "metric";
    controller.goalId = 5L;

    assertRedirect("/admin/metrics/metrics?goalId=5", controller.post());

    verify(controller).save();
  }

  @Test
  public void postWithDuplicateGoalThrowsConstraintViolation() {
    controller.name = "asd";
    doThrow(mock(ConstraintViolationException.class)).when(controller).save();

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("See mõõdik on juba sisestatud."));
    verify(transaction).rollback();
  }

  @Test
  public void postCallsSaveWithTrimmedFields() {
    controller.name = "metric    ";
    controller.publicDescription = "a b    ";
    controller.privateDescription = "\n b \n";
    controller.commentOnStartLevel = "    c";
    controller.commentOnTargetLevel = "\r d";
    controller.infoSource = "  e";
    controller.institutionToReport = "f   ";
    controller.goalId = 5L;

    assertRedirect("/admin/metrics/metrics?goalId=5", controller.post());

    assertEquals("metric",controller.name);
    assertEquals("a b",controller.publicDescription);
    assertEquals("b",controller.privateDescription);
    assertEquals("c",controller.commentOnStartLevel);
    assertEquals("d",controller.commentOnTargetLevel);
    assertEquals("e",controller.infoSource);
    assertEquals("f",controller.institutionToReport);
    verify(controller).save();
  }

  public static class TestSave extends controllers.admin.metrics.Save {
    @Override
    protected void save() {
    }
  }
}
