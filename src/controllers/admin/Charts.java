package controllers.admin;

import controllers.AbstractChart;
import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.*;

public class Charts extends UserAwareController {

  public Long goalId;
  public Goal goal;
  public List<Metric> metricsWithValidLevels = new ArrayList<>();
  public List<String> graphColors = AbstractChart.CHART_COLORS;

  @Override @Role("admin")
  public Result get(){
    goal = (Goal) hibernate.get(Goal.class, goalId);
    Set<Metric> metrics = goal.getMetrics();

    for (Metric metric: metrics){
      if(metric.getStartLevel() != null && metric.getTargetLevel() != null && metricsWithValidLevels.size()<=10){
        metricsWithValidLevels.add(metric);
      }
    }
    return render();
  }



}
