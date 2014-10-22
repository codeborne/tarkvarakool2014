package controllers.admin.metrics;

import controllers.ControllerTest;
import model.Metric;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class AddTest extends ControllerTest<Add> {




  @Test
  public void saveNameOnly() {
    controller.name = "metric";
    controller.goalId = 5L;

    controller.save();

    Metric savedMetric = (Metric) getSavedEntity();

    assertEquals("metric",savedMetric.getName());
    assertEquals(null,savedMetric.getPublicDescription());
    assertEquals(null,savedMetric.getPrivateDescription());
    assertEquals(0, (int) savedMetric.getStartLevel());
    assertEquals(null,savedMetric.getCommentOnStartLevel());
    assertEquals(0,(int) savedMetric.getTargetLevel());
    assertEquals(null,savedMetric.getCommentOnTargetLevel());
    assertEquals(null,savedMetric.getInfoSource());
    assertEquals(null, savedMetric.getInstitutionToReport());
    verify(hibernate).save(savedMetric);
  }

  @Test
  public void saveAllFields()  {
    controller.name = "metric";
    controller.publicDescription = "a";
    controller.privateDescription = "b";
    controller.startLevel = 5;
    controller.commentOnStartLevel = "c";
    controller.targetLevel = 6;
    controller.commentOnTargetLevel = "d";
    controller.infoSource = "e";
    controller.institutionToReport = "f";

    controller.save();

    Metric savedMetric = (Metric) getSavedEntity();

    assertEquals("metric",savedMetric.getName());
    assertEquals("a",savedMetric.getPublicDescription());
    assertEquals("b",savedMetric.getPrivateDescription());
    assertEquals(5,(int) savedMetric.getStartLevel());
    assertEquals("c",savedMetric.getCommentOnStartLevel());
    assertEquals(6, (int) savedMetric.getTargetLevel());
    assertEquals("d",savedMetric.getCommentOnTargetLevel());
    assertEquals("e",savedMetric.getInfoSource());
    assertEquals("f",savedMetric.getInstitutionToReport());
    verify(hibernate).save(savedMetric);
  }

}