package controllers.admin;

import com.google.gson.Gson;
import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Chart extends UserAwareController {

  public Integer minimumYear = UserAwareController.MINIMUM_YEAR;
  public Integer maximumYear = UserAwareController.MAXIMUM_YEAR;

  public String jsonResponse;

  public List<Goal> goals = new ArrayList<>();
  public Long goalId;
  public Goal goal;




  @Override @Role("admin")
  public Result post(){
    goal = (Goal) hibernate.get(Goal.class, goalId);

    Set<Metric> metrics = goal.getMetrics();

    Set<Metric> metricsWithValidLevels= new HashSet<>();

    for (Metric metric: metrics){
      if(metric.getStartLevel() != null && metric.getTargetLevel() != null && metricsWithValidLevels.size()<=10){
        metricsWithValidLevels.add(metric);
      }
    }


    List<String> header = new ArrayList<>();
    header.add(messages.get("year"));
    for (Metric metric:metricsWithValidLevels){
      header.add(metric.getName());
    }
    header.add(messages.get("budget"));

    List<String> row = new ArrayList<>();
    row.add( new Gson().toJson(header));
    for (int year = minimumYear; year<=maximumYear;year++){

      String values = "["+"\""+year+"\"" ;
      for (Metric metric:metricsWithValidLevels){
        Double value = null;
        if(metric.getStartLevel() != metric.getTargetLevel() && metric.getValues().get(year) != null) {
          value = (metric.getValues().get(year).doubleValue() - metric.getStartLevel()) / (metric.getTargetLevel() - metric.getStartLevel());
          value = Math.round( value * 1000.0 ) / 1000.0;
        }
        values = values +  "," +value;
      }
      values = values + ","+ goal.getYearlyBudgets().get(year)+"]";

      row.add(values);

    }

    jsonResponse =  row.toString();
    System.out.println(jsonResponse);
    return render();
  }



}