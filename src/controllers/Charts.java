package controllers;

import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Charts extends UserAwareController {

  public Long goalId;
  public Goal goal;
  public Set<Metric> metricsWithValidLevels = new HashSet<>();
  public List<String> graphColors = AbstractChart.CHART_COLORS;

  @Override @Role("anonymous")
  public Result get(){
    goal = (Goal) hibernate.get(Goal.class, goalId);
    List<Metric> metrics = goal.getPublicMetrics();

    for (Metric metric: metrics){
      if(metric.getStartLevel() != null && metric.getTargetLevel() != null && metricsWithValidLevels.size()<=10){
        metricsWithValidLevels.add(metric);
      }
    }
    return render();
  }



}
