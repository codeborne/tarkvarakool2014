package controllers;

import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hibernate.criterion.Order.asc;

public class Home extends UserAwareController {

  public List<Goal> goals = new ArrayList<>();
  public List<List<List<String>>> infoSourceContentList = new ArrayList<>();

  @Override
  @Role("anonymous")
  public Result get() {
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
}




