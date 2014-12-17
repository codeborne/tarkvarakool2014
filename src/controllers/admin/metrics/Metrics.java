package controllers.admin.metrics;

import controllers.UserAwareController;
import framework.NoBind;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Metrics extends UserAwareController {
  public Long goalId;
  @NoBind
  public Goal goal;
  public Long metricId;
  @NoBind
  public List<List<String>> infoSourceContentList = new ArrayList<>();
  @NoBind
  public Set<Metric> metrics;

  @Override
  @Role("admin")
  public Result get() {
    goal = (Goal) hibernate.get(Goal.class, goalId);
    metrics = goal.getMetrics();
    String language = (String) session.getAttribute("locale");
    for (Metric metric : metrics) {
      infoSourceContentList.add(metric.getInfoSourceAsListOfWordsAndLinks(language));
    }
    return render();
  }
}