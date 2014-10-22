package controllers.admin.metrics;

import controllers.ControllerTest;
import model.Goal;
import model.Metric;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class AddTest extends ControllerTest<Add> {

  @Before
  public void setUp() throws Exception {
    when(session.getAttribute("username")).thenReturn("Some username");
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

  @Test
  public void postIfAddingMetricSuccessfulNameOnly() {
    controller.name = "metric";
    controller.goalId = 5L;

    when(hibernate.get(Goal.class, 5L)).thenReturn(new Goal("name", 5));
    assertRedirect("/admin/metrics/metrics?goalId=5", controller.post());

    Metric savedMetric = (Metric) getSavedEntity();

    Assert.assertEquals("metric", savedMetric.getName());
    verify(hibernate).save(savedMetric);
  }

  @Test
  public void postSaveDuplicate() {
    controller.name = "metric";
    controller.goalId = 2L;

    Goal goal = new Goal("name", 5);
    when(hibernate.get(Goal.class, 2L)).thenReturn(goal);
    doThrow(mock(ConstraintViolationException.class)).when(controller.hibernate).save(any(Metric.class));

    assertRender(controller.post());

    Metric savedMetric = (Metric) getSavedEntity();
    assertTrue(controller.errorsList.contains("See mõõdik on juba sisestatud."));
    Assert.assertEquals(1, controller.errorsList.size());

    verify(hibernate).save(savedMetric);

  }

  @Test
  public void postSaveThrowsException() {
    controller.name = "333 eee";
    controller.goalId = 9L;

    Goal goal = new Goal("name", 5);
    doThrow(new RuntimeException()).when(controller.hibernate).save(any(Metric.class));
    when(hibernate.get(Goal.class, 9L)).thenReturn(goal);
    assertRender(controller.post());


    assertTrue(controller.errorsList.contains("Tekkis viga."));
    Assert.assertEquals(1, controller.errorsList.size());

    Metric savedMetric = (Metric) getSavedEntity();
    verify(hibernate).save(savedMetric);
  }


  @Test
  public void postIfAddingMetricSuccessfulWithAllFields() {
    controller.name = "metric";
    controller.publicDescription = "a    ";
    controller.privateDescription = "\n b \n";
    controller.startLevel = 5;
    controller.commentOnStartLevel = "c";
    controller.targetLevel = 6;
    controller.commentOnTargetLevel = "d";
    controller.infoSource = "e";
    controller.institutionToReport = "f";
    controller.goalId = 5L;

    when(hibernate.get(Goal.class, 5L)).thenReturn(new Goal("name", 5));
    assertRedirect("/admin/metrics/metrics?goalId=5", controller.post());

    Metric savedMetric = (Metric) getSavedEntity();

    Assert.assertEquals("metric", savedMetric.getName());
    Assert.assertEquals("a", savedMetric.getPublicDescription());
    Assert.assertEquals("b", savedMetric.getPrivateDescription());
    Assert.assertEquals(5,(int) savedMetric.getStartLevel());
    Assert.assertEquals("c", savedMetric.getCommentOnStartLevel());
    Assert.assertEquals(6,(int) savedMetric.getTargetLevel());
    Assert.assertEquals("d", savedMetric.getCommentOnTargetLevel());
    Assert.assertEquals("e", savedMetric.getInfoSource());
    Assert.assertEquals("f", savedMetric.getInstitutionToReport());


    verify(hibernate).save(savedMetric);
  }
}