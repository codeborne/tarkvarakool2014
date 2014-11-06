package controllers.admin.goals;

import com.google.gson.Gson;
import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.HashSet;
import java.util.Set;

public class Modify extends UserAwareController {
  public Long goalId;
  public Long metricId;
  public String value;
  public String field;

  public Set<String> errorsList = new HashSet<>();
  public String jsonResponse;

  public Goal goal;
  public Metric metric;

  @Override
  @Role("admin")
  public Result post() {
    checkErrors();
    if (errorsList.isEmpty()) {

      if (field.equals("engMetricName")) {
        metric = (Metric) hibernate.get(Metric.class, metricId);
        metric.setEngName(value);
        hibernate.update(metric);
        hibernate.flush();
      } else if (field.equals("engUnit")) {
        metric = (Metric) hibernate.get(Metric.class, metricId);
        metric.setEngUnit(value);
        hibernate.update(metric);
        hibernate.flush();
      } else if (field.equals("engPublicDescription")) {
        metric = (Metric) hibernate.get(Metric.class, metricId);
        metric.setEngPublicDescription(value);
        hibernate.update(metric);
        hibernate.flush();
      } else if (field.equals("engName")) {
        goal = (Goal) hibernate.get(Goal.class, goalId);
        goal.setEngName(value);
        hibernate.update(goal);
        hibernate.flush();
      } else if (field.equals("engComment")) {
        goal = (Goal) hibernate.get(Goal.class, goalId);
        goal.setEngComment(value);
        hibernate.update(goal);
        hibernate.flush();
      }


    }

    jsonResponse = new Gson().toJson(errorsList);

    return render();
  }

  public void checkErrors() {
    checkGoalIdAndMetricId();
    checkEngName();
    checkEngComment();
    checkEngMetricName();
    checkEngPublicDescription();
    checkEngUnit();

  }

  public void checkGoalIdAndMetricId() {
    if (errors.containsKey("goalId") || errors.containsKey("metricId") || (goalId == null && metricId==null))
      errorsList.add("Tekkis viga.");
  }

  public void checkEngName() {
    if (errors.containsKey("engName"))
      errorsList.add("Tekkis viga.");
  }
  public void checkEngUnit() {
    if (errors.containsKey("engUnit"))
      errorsList.add("Tekkis viga.");
  }

  public void checkEngPublicDescription() {
    if (errors.containsKey("engPublicDescription"))
      errorsList.add("Tekkis viga.");
  }
  public void checkEngComment() {
    if (errors.containsKey("engComment"))
      errorsList.add("Tekkis viga.");
  }
  public void checkEngMetricName() {
    if (errors.containsKey("engMetricName"))
      errorsList.add("Tekkis viga.");
  }

}
