package controllers.metrics;

import controllers.AbstractMetricChart;
import framework.Result;
import framework.Role;

public class Chart extends AbstractMetricChart {

  @Override @Role("anonymous")
  public Result post() {
    prepareJsonResponse();
    return render();
  }
}

