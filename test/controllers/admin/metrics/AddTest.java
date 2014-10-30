package controllers.admin.metrics;

import controllers.ControllerTest;
import model.Metric;
import org.hibernate.Criteria;
import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.Criterion;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class AddTest extends ControllerTest<Add> {


  @Test
  public void saveRequiredFieldsOnly() {
    controller.name = "metric";
    controller.goalId = 5L;

    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Metric.class)).thenReturn(criteria);
    when(criteria.createCriteria(anyString())).thenReturn(criteria);
    when(criteria.add(any(Criterion.class))).thenReturn(criteria);
    when(criteria.setProjection(any(AggregateProjection.class))).thenReturn(criteria);
    when(criteria.uniqueResult()).thenReturn(6.2);

    controller.save();

    Metric savedMetric = (Metric) getSavedEntity();

    assertEquals("metric",savedMetric.getName());
    assertEquals(null,savedMetric.getUnit());
    assertEquals(null,savedMetric.getPublicDescription());
    assertEquals(null,savedMetric.getPrivateDescription());
    assertEquals(null,savedMetric.getStartLevel());
    assertEquals(null,savedMetric.getCommentOnStartLevel());
    assertEquals(null,savedMetric.getTargetLevel());
    assertEquals(null,savedMetric.getCommentOnTargetLevel());
    assertEquals(null,savedMetric.getInfoSource());
    assertEquals(null, savedMetric.getInstitutionToReport());
    assertEquals((Double) 8.0, savedMetric.getOrderNumber());
    verify(hibernate).save(savedMetric);
  }



  @Test
  public void saveAllFields()  {
    controller.name = "metric";
    controller.unit = "%";
    controller.publicDescription = "a";
    controller.privateDescription = "b";
    controller.startLevel = 5;
    controller.commentOnStartLevel = "c";
    controller.targetLevel = 6;
    controller.commentOnTargetLevel = "d";
    controller.infoSource = "e";
    controller.institutionToReport = "f";
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Metric.class)).thenReturn(criteria);
    when(criteria.createCriteria(anyString())).thenReturn(criteria);
    when(criteria.add(any(Criterion.class))).thenReturn(criteria);
    when(criteria.setProjection(any(AggregateProjection.class))).thenReturn(criteria);
    when(criteria.uniqueResult()).thenReturn(2.2);


    controller.save();

    Metric savedMetric = (Metric) getSavedEntity();

    assertEquals("metric",savedMetric.getName());
    assertEquals("%",savedMetric.getUnit());
    assertEquals("a",savedMetric.getPublicDescription());
    assertEquals("b",savedMetric.getPrivateDescription());
    assertEquals(5,(int) savedMetric.getStartLevel());
    assertEquals("c",savedMetric.getCommentOnStartLevel());
    assertEquals(6, (int) savedMetric.getTargetLevel());
    assertEquals("d",savedMetric.getCommentOnTargetLevel());
    assertEquals("e",savedMetric.getInfoSource());
    assertEquals("f",savedMetric.getInstitutionToReport());
    assertEquals((Double) 4.0, savedMetric.getOrderNumber());
    verify(hibernate).save(savedMetric);
  }
}