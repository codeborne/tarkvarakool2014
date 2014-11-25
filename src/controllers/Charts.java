package controllers;

import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Charts extends UserAwareController {

  public Long goalId;
  public Goal goal;
  public Set<Metric> metricsWithValidLevels = new HashSet<>();
  public List<String> graphColors;


  @Override @Role("anonymous")
  public Result get(){
    graphColors = Arrays.asList("#1abc9c", "#3498db", "#9b59b6", "#34495e", "#f1c40f", "#e67e22", "#e74c3c", "#95a5a6", "#d35400", "#2980b9", "#16a085");
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
