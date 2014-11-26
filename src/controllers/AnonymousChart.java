package controllers;

import model.Goal;
import model.Metric;

import java.util.Collection;

public class AnonymousChart extends AbstractChart {

  @Override
  protected Collection<Metric> getMetrics(Goal goal) {
    return goal.getPublicMetrics();
  }
}
