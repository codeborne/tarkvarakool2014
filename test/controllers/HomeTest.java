package controllers;

import org.junit.Before;
import org.junit.Test;

public class HomeTest extends ControllerTest<Home>{

  @Before
  public void setUp() throws Exception {
    controller.minimumYear = UserAwareController.MINIMUM_YEAR;
    controller.maximumYear = UserAwareController.MAXIMUM_YEAR;
  }

  @Test
  public void get() throws Exception {

  }

  @Test
  public void isMetricPerformancePositive() throws Exception {
//    Metric metric = new Metric();
  }
}

