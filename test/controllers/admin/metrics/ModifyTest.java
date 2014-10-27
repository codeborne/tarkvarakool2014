package controllers.admin.metrics;

import controllers.ControllerTest;
import model.Goal;
import model.Metric;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ModifyTest extends ControllerTest<Modify> {
  @Test
  public void getWhenObjectFound() throws Exception{
    controller.metricId = 2L;
    when(hibernate.get(Metric.class, 2L)).thenReturn(new Metric(new Goal("",10), "name", "%", "a", "b", 2, "c", 4, "d", "e", "f", 1.0));

    assertRender(controller.get());
    assertEquals("name",controller.name);
    assertEquals("%",controller.unit);
    assertEquals("a",controller.publicDescription);
    assertEquals("b",controller.privateDescription);
    assertEquals("c",controller.commentOnStartLevel);
    assertEquals("d",controller.commentOnTargetLevel);
    assertEquals("e",controller.infoSource);
    assertEquals("f",controller.institutionToReport);
    assertEquals(2,(int) controller.startLevel);
    assertEquals(4, (int) controller.targetLevel);
    assertEquals((Double) 1.0, controller.orderNumber);
  }

  @Test
  public void getWhenObjectNotFound() throws Exception{
    controller.metricId = 2L;
    controller.goalId = 3L;
    when(hibernate.get(Metric.class, 2L)).thenReturn(null);

    assertRedirect("/admin/metrics/add?goalId=3", controller.get());
  }

  @Test
  public void updateSuccess() {
    controller.metricId = 2L;
    controller.name = "metric";
    controller.unit = "%";
    controller.publicDescription = "a a a";
    controller.privateDescription = "b";
    controller.startLevel = 5;
    controller.commentOnStartLevel = "c";
    controller.targetLevel = 6;
    controller.commentOnTargetLevel = "d";
    controller.infoSource = "e";
    controller.institutionToReport = "f";
    controller.orderNumber = 5.0;

    Metric metricBeingChanged = new Metric(new Goal("",10),"TERE", null, null, null,0,null,0,null,null, null, 1.0);
    when(hibernate.get(Metric.class, 2L)).thenReturn(metricBeingChanged);

    controller.save();

    assertEquals("metric",metricBeingChanged.getName());
    assertEquals("%",metricBeingChanged.getUnit());
    assertEquals("a a a",metricBeingChanged.getPublicDescription());
    assertEquals("b",metricBeingChanged.getPrivateDescription());
    assertEquals(5,(int) metricBeingChanged.getStartLevel());
    assertEquals("c",metricBeingChanged.getCommentOnStartLevel());
    assertEquals(6, (int) metricBeingChanged.getTargetLevel());
    assertEquals("d",metricBeingChanged.getCommentOnTargetLevel());
    assertEquals("e",metricBeingChanged.getInfoSource());
    assertEquals("f",metricBeingChanged.getInstitutionToReport());
    assertEquals((Double) 5.0, metricBeingChanged.getOrderNumber());
    verify(hibernate).update(metricBeingChanged);
  }
}
