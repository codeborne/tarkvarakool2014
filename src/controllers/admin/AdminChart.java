package controllers.admin;

import controllers.AbstractChart;
import model.Goal;
import model.Metric;

import java.util.Collection;

public class AdminChart extends AbstractChart {

  @Override
  protected Collection<Metric> getMetrics(Goal goal) {
    return goal.getMetrics();
  }

}
