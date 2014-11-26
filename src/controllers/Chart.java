package controllers;

import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.Collection;

public class Chart extends AbstractChart{

  @Override @Role("anonymous")
  public Result post(){
    prepareJsonResponse();
    return render();
  }

  @Override
  protected Collection<Metric> getMetrics(Goal goal) {
    return goal.getPublicMetrics();
  }
}
