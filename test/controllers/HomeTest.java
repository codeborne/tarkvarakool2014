package controllers;

import model.Goal;
import model.Metric;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class HomeTest extends ControllerTest<Home>{
  Metric metric = new Metric(new Goal("goal",100), "metric", null, null, null, 10.0, null, 25.0, null, null, null, 1.0, true);

  @Before
  public void setUp() throws Exception {
    controller.minimumYear = UserAwareController.MINIMUM_YEAR;
    controller.maximumYear = UserAwareController.MAXIMUM_YEAR;

    metric.getValues().put(2014, new BigDecimal(11));
    metric.getForecasts().put(2014, new BigDecimal(11));
    metric.getForecasts().put(2015, new BigDecimal(13));
    metric.getForecasts().put(2016, new BigDecimal(15));
    metric.getForecasts().put(2017, new BigDecimal(16));
    metric.getForecasts().put(2018, new BigDecimal(18));
    metric.getForecasts().put(2019, new BigDecimal(19));
    metric.getForecasts().put(2020, new BigDecimal(20));
  }

  @Test
  public void isMetricPerformancePositiveWhenValueEqualsForecast() throws Exception {
    metric.getValues().put(2015, new BigDecimal(13));
    assertTrue(controller.isMetricPerformancePositive(metric));
  }

  @Test
  public void isMetricPerformancePositiveWhenValueIsGreaterThanForecast() throws Exception {
    metric.getValues().put(2015, new BigDecimal(15));
    assertTrue(controller.isMetricPerformancePositive(metric));
  }

  @Test
  public void isMetricPerformancePositiveWhenValueIsLessThanForecast() throws Exception {
    metric.getValues().put(2015, new BigDecimal(11));
    assertFalse(controller.isMetricPerformancePositive(metric));
  }

  @Test
  public void isMetricPerformancePositiveWhenForecastsMissing() throws Exception {
    Metric metric2 = new Metric(new Goal("goal",100), "metric", null, null, null, 10.0, null, 25.0, null, null, null, 1.0, true);
    metric2.getValues().put(2014, new BigDecimal(11));
    assertEquals(null, controller.isMetricPerformancePositive(metric2));
  }
}
