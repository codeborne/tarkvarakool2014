package controllers;

import com.google.gson.Gson;
import model.Goal;
import model.Metric;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractChart extends UserAwareController {

  public Integer minimumYear = UserAwareController.MINIMUM_YEAR;
  public Integer maximumYear = UserAwareController.MAXIMUM_YEAR;
  public String jsonResponse;
  public List<Goal> goals = new ArrayList<>();
  public Long goalId;
  public Goal goal;

  public void prepareJsonResponse(){
    goal = (Goal) hibernate.get(Goal.class, goalId);
    Long availableBudget = goal.getBudget().longValue();

    List<Metric> metricsWithValidLevels= new ArrayList<>();

    for (Metric metric: getMetrics(goal)){
      if(metric.getStartLevel() != null && metric.getTargetLevel() != null && metricsWithValidLevels.size()<=10){
        metricsWithValidLevels.add(metric);
      }
    }

    List<String> header = new ArrayList<>();
    header.add(messages.get("year"));
    for (Metric metric:metricsWithValidLevels){
      header.add(metric.getName());
    }
    header.add(messages.get("budgetLeft"));

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
        else if (year == 2014 && metric.getValues().get(year)==null){
          value = 0.0;
        }
        values = values +  "," +value;
      }
      if(goal.getYearlyBudgets().get(year)!=null) {
        availableBudget = availableBudget - goal.getYearlyBudgets().get(year);
        values = values + "," + availableBudget + "]";
      }
      else if (year == 2014 && goal.getYearlyBudgets().get(year) == null){
        values = values + "," + availableBudget + "]";
      }
      else {
        values = values + "," + null + "]";
      }
      row.add(values);
    }
    jsonResponse =  row.toString();
  }

  protected abstract Collection<Metric> getMetrics(Goal goal);
}
