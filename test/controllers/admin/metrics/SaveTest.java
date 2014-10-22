package controllers.admin.metrics;

import controllers.ControllerTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SaveTest extends ControllerTest<SaveTest.TestSave> {
  @Test
  public void testName() throws Exception {

  }

  @Test
  public void postIfNameIsNull() {
    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage mõõdik."));

  }

  @Test
  public void postIfNameHasOnlySpaces() {
    controller.name = " \n\n \n    \r\n \r\n \n \n";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage mõõdik."));

  }

  @Test
  public void postIfNameIsEmpty() {
    controller.name = "";

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sisestage mõõdik."));

  }


  @Test
  public void postIfPublicDescriptionThrowsException() {
    controller.name = "metric";
    controller.errors.put("publicDescription", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga avalikus kirjelduses"));
  }

  @Test
  public void postIfPrivateDescriptionThrowsException() {
    controller.name = "metric";
    controller.errors.put("privateDescription", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga mitteavalikus kirjelduses"));

  }

  @Test
  public void postIfStartLevelIsNotNumber() {
    controller.name = "metric";
    controller.errors.put("startLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Algtase peab olema number"));

  }

  @Test
  public void postIfCommentOnStartLevelThrowsException() {
    controller.name = "metric";
    controller.errors.put("commentOnStartLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga algtaseme kommentaaris"));
  }

  @Test
  public void postIfTargetLevelIsNotNumber() {
    controller.name = "metric";
    controller.errors.put("targetLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Sihttase peab olema number"));
  }

  @Test
  public void postIfCommentOnTargetLevelThrowsException() {
    controller.name = "metric";
    controller.errors.put("commentOnTargetLevel", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga sihttaseme kommentaaris"));

  }

  @Test
  public void postIfInfoSourceThrowsException() {
    controller.name = "metric";
    controller.errors.put("infoSource", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga infoallika sisestamisel"));

  }

  @Test
  public void postIfInstitutionToReportThrowsException() {
    controller.name = "metric";
    controller.errors.put("institutionToReport", new RuntimeException());

    assertRender(controller.post());

    assertEquals(1, controller.errorsList.size());
    assertTrue(controller.errorsList.contains("Viga raporteeritava asutuse sisestamisel"));
  }

  public static class TestSave extends controllers.admin.metrics.Save {
    @Override
    protected void save() {
    }
  }
}
