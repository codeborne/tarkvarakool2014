package controllers.admin;

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
  public List<String> graphColors;


  @Override @Role("admin")
  public Result get(){
    graphColors = Arrays.asList("#1abc9c", "#3498db", "#9b59b6", "#34495e", "#f1c40f", "#e67e22", "#e74c3c", "#95a5a6", "#d35400", "#2980b9", "#16a085");
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
