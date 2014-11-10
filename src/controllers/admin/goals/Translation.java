package controllers.admin.goals;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.HashSet;
import java.util.Set;

public class Translation extends UserAwareController {

  public Long goalId;
  public String engName;
  public String engComment;
  public Set<String> errorsList = new HashSet<>();
  public Goal goal;

  @Override
  @Role("admin")
  public Result get() {
    goal = (Goal) hibernate.get(Goal.class, goalId);
    return render();
  }

  @Override
  @Role("admin")
  public Result post() {
    String[] engUnit = request.getParameterValues("engUnit");
    String[] engMetricName = request.getParameterValues("engMetricName");
    String[] engPublicDescription = request.getParameterValues("engPublicDescription");

    checkErrors();
    if (errorsList.isEmpty()) {

      goal = (Goal) hibernate.get(Goal.class, goalId);
      Set<Metric> metrics = goal.getMetrics();
      goal.setEngName(engName);
      goal.setEngComment(engComment);
      int i = 0;
      for(Metric metric : metrics){
        if(metric.getIsPublic()==true) {
          metric.setEngName(engMetricName[i]);
          metric.setEngUnit(engUnit[i]);
          metric.setEngPublicDescription(engPublicDescription[i]);
          i++;
        }
      }
      hibernate.update(goal);
      hibernate.flush();
    }

    return render();
  }

  public void checkErrors() {
    checkGoalId();
    checkEngName();
    checkEngComment();
    checkEngMetricName();
    checkEngPublicDescription();
    checkEngUnit();

  }

  public void checkGoalId() {
    if (errors.containsKey("goalId") || goalId == null)
      errorsList.add(messages.get("error"));
  }

  public void checkEngName() {
    if (errors.containsKey("engName"))
      errorsList.add(messages.get("error"));
  }
  public void checkEngUnit() {
    if (errors.containsKey("engUnit"))
      errorsList.add(messages.get("error"));
  }

  public void checkEngPublicDescription() {
    if (errors.containsKey("engPublicDescription"))
      errorsList.add(messages.get("error"));
  }
  public void checkEngComment() {
    if (errors.containsKey("engComment"))
      errorsList.add(messages.get("error"));
  }
  public void checkEngMetricName() {
    if (errors.containsKey("engMetricName"))
      errorsList.add(messages.get("error"));
  }




}