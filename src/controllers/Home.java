package controllers;

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

  public List<Goal> goals = new ArrayList<>();
  public List<List<List<String>>> infoSourceContentList = new ArrayList<>();
  public Integer minimumYear = UserAwareController.MINIMUM_YEAR;
  public Integer maximumYear = UserAwareController.MAXIMUM_YEAR;
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




