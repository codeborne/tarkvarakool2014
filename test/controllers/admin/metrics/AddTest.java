package controllers.admin.metrics;

import controllers.ControllerTest;
import model.Goal;
import model.Metric;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class AddTest extends ControllerTest<Add> {




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