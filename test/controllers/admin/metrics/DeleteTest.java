package controllers.admin.metrics;

import controllers.ControllerTest;
import model.Goal;
import model.Metric;
import org.hibernate.HibernateException;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DeleteTest extends ControllerTest<Delete>{
  @Test
  public void postIfDeleteSuccessful() throws Exception {
    controller.id = 5L;
    controller.goalId = 3L;
    Metric metric = new Metric(new Goal("name", 5), "muki", "", "", "", 0, "", 0, "", "", "");

    when(hibernate.get(Metric.class, 5L)).thenReturn(metric);

    assertRedirect("/admin/metrics/metrics?goalId=3", controller.post());

    verify(hibernate).delete(metric);

  }

  @Test
  public void postIfObjectNotFoundToDelete() throws Exception {

    controller.id = 5L;
    controller.goalId = 3L;

    when(hibernate.get(Metric.class, 5L)).thenReturn(null);

    assertRedirect("/admin/metrics/metrics?goalId=3", controller.post());

  }

  @Test (expected = HibernateException.class)
  public void postIfDeleteThrowsHibernateException() throws Exception {

    controller.id = 5L;
    Metric metric = new Metric(new Goal("name", 5), "muki", "", "", "", 0, "", 0, "", "", "");

    when(hibernate.get(Metric.class, 5L)).thenReturn(metric);
    doThrow(mock(HibernateException.class)).when(hibernate).delete(metric);


    assertRender(controller.post());


  }
}