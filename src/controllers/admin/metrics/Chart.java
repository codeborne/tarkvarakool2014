package controllers.admin.metrics;

import controllers.AbstractMetricChart;
import framework.Result;
import framework.Role;

public class Chart extends AbstractMetricChart {


@Override @Role("admin")
public Result post() {
    prepareJsonResponse();
    return json();
  }

}
