package controllers;

import framework.NoBind;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import static org.hibernate.criterion.Order.asc;

public class Home extends UserAwareController {

  @NoBind
  public List<Goal> goals = new ArrayList<>();
  @NoBind
  public List<List<List<String>>> infoSourceContentList = new ArrayList<>();
  @NoBind
  public Integer minimumYear = MINIMUM_YEAR;
  @NoBind
  public Integer maximumYear = MAXIMUM_YEAR;
  @NoBind
  public int currentYear;

  @Override
  @Role("anonymous")
  public Result get() {
    currentYear =  Calendar.getInstance().get(Calendar.YEAR);
    goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();
    String language = (String) session.getAttribute("locale");
    for(Goal goal:goals) {
      Set<Metric> metrics = goal.getMetrics();
      List<List<String>> infoSourceListAtMetricLevel = new ArrayList<>();
      for (Metric metric : metrics) {
        infoSourceListAtMetricLevel.add(metric.getInfoSourceAsListOfWordsAndLinks(language));
      }
      infoSourceContentList.add(infoSourceListAtMetricLevel);
    }
    return render();
  }

  public long getBudgetShareSpentToDate(Goal goal) {
    double budgetShareSpentToDate =0;
    for (int year = MINIMUM_YEAR; year<=currentYear; year++){
      if(goal.getYearlyBudgets().get(year)!=null) {
        budgetShareSpentToDate = budgetShareSpentToDate + goal.getYearlyBudgets().get(year).doubleValue();
      }
    }

    return Math.round(budgetShareSpentToDate*100/goal.getBudget().doubleValue());
  }


  public Boolean isMetricPerformancePositive(Metric metric){
    for(int year = maximumYear; year>=minimumYear; year--){
      if(metric.getValues().get(year)!= null && metric.getForecasts().get(year)!=null){
        if((metric.getValues().get(year).compareTo(metric.getForecasts().get(year))>=0 && !metric.getIsDecreasing()) ||
          metric.getValues().get(year).compareTo(metric.getForecasts().get(year))<=0 && metric.getIsDecreasing()){
          return true;
        }
        else {
          return false;
        }
      }
    }
    return null;
  }
}




