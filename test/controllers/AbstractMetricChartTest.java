package controllers;

import model.Goal;
import model.Metric;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class AbstractMetricChartTest extends ControllerTest<AbstractMetricChartTest.TestAbstractMetricChart>{

  public Metric metric = new Metric(new Goal("eesmärk", 1000), "mõõdik", "%", "", "", 20.0, "", 55.0, "", "", "abc", 1.0, true);

  @Before
  public void setUp() throws Exception {
    when(session.getAttribute("locale")).thenReturn("et");
    when(request.getPathInfo()).thenReturn("anonymous");
    controller = spy(controller);
    metric.setEngName("metric");
  }

  @Test
  public void createHeader() throws Exception {
    controller.metric = metric;
    assertEquals(asList("%","mõõdik", "null"), controller.createHeader());
  }

  @Test
  public void createValuesRowByYearWhenValuesExist() throws Exception {
    metric.getValues().put(2014, new BigDecimal(25));
    metric.getValues().put(2015, new BigDecimal(30));
    metric.getValues().put(2016, new BigDecimal(35));
    controller.metric = metric;
    assertEquals("[\"2014\",25,\"2014 Prognoos: - Tulemus: 25 %\"]", controller.createValuesRowByYear(2014));
    assertEquals("[\"2015\",30,\"2015 Prognoos: - Tulemus: 30 %\"]", controller.createValuesRowByYear(2015));
    assertEquals("[\"2016\",35,\"2016 Prognoos: - Tulemus: 35 %\"]", controller.createValuesRowByYear(2016));
    assertEquals("[\"2017\",0,\"2017 Prognoos: - Tulemus: -\"]", controller.createValuesRowByYear(2017));
  }

  @Test
  public void createValuesRowByYearWhen2014ValueDoesNotExist() throws Exception {
    controller.metric = metric;
    assertEquals("[\"2014\",0,\"2014 Prognoos: - Tulemus: -\"]", controller.createValuesRowByYear(2014));
  }

  public static class TestAbstractMetricChart extends AbstractMetricChart {
  }

  @Test
  public void prepareJson() throws Exception {
    metric.getValues().put(2014, new BigDecimal(25));
    metric.getValues().put(2015, new BigDecimal(30));
    metric.getValues().put(2016, new BigDecimal(35));
    controller.metricId = 2L;
    when(hibernate.get(Metric.class, 2L)).thenReturn(metric);
    controller.prepareJsonResponse();
    assertEquals("[[[\"%\",\"mõõdik\",\"null\"], [\"2014\",25,\"2014 Prognoos: - Tulemus: 25 %\"], " +
      "[\"2015\",30,\"2015 Prognoos: - Tulemus: 30 %\"], [\"2016\",35,\"2016 Prognoos: - Tulemus: 35 %\"], " +
      "[\"2017\",0,\"2017 Prognoos: - Tulemus: -\"], [\"2018\",0,\"2018 Prognoos: - Tulemus: -\"], " +
      "[\"2019\",0,\"2019 Prognoos: - Tulemus: -\"], [\"2020\",0,\"2020 Prognoos: - Tulemus: -\"]], " +
      "[[\"%\",\"mõõdik\",\"null\"], [\"2014\",0, \"\"], [\"2015\",0, \"\"], [\"2016\",0, \"\"], " +
      "[\"2017\",0, \"\"], [\"2018\",0, \"\"], [\"2019\",0, \"\"], [\"2020\",0, \"\"]]]",controller.jsonResponse);
  }


}