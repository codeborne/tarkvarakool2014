package controllers.admin;

import controllers.AbstractChart;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.Collection;

public class Chart extends AbstractChart {

  @Override @Role("admin")
  public Result post(){
    prepareJsonResponse();
    return render();
  }

  @Override
  protected Collection<Metric> getMetrics(Goal goal) {
    return goal.getMetrics();
  }
}
