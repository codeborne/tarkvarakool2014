package controllers.admin.metrics;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Metrics extends UserAwareController {
  public Long goalId;
  public Goal goal;
  public Long metricId;
  public List<List<String>> infoSourceContentList = new ArrayList<>();
  public Set<Metric> metrics;

  @Override
  @Role("admin")
  public Result get() {
    goal = (Goal) hibernate.get(Goal.class, goalId);
    metrics = goal.getMetrics();
    for (Metric metric : metrics) {
      infoSourceContentList.add(checkInfoSourceContent(metric));

    }

    return render();
  }

  private List<String> checkInfoSourceContent(Metric metric) {
    List<String> infoSourceList = new ArrayList<>();

    String infoSource = metric.getInfoSource();
    StringTokenizer stringTokenizer = new StringTokenizer(infoSource);
    while (stringTokenizer.hasMoreElements()) {
      infoSourceList.add(stringTokenizer.nextElement().toString());

    }
    return infoSourceList;
  }
}