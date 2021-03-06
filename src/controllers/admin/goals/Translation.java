package controllers.admin.goals;

import controllers.UserAwareController;
import framework.NoBind;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Translation extends UserAwareController {

  public Long goalId;
  public String engName;
  public String engComment;
  @NoBind
  public Set<String> errorsList = new HashSet<>();
  @NoBind
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
    String[] engInfoSource = request.getParameterValues("engInfoSource");
    String[] engStartLevelComment = request.getParameterValues("engStartLevelComment");
    String[] engTargetLevelComment = request.getParameterValues("engTargetLevelComment");



    checkErrors();
    if (errorsList.isEmpty()) {

      goal = (Goal) hibernate.get(Goal.class, goalId);
      List<Metric> metrics = goal.getPublicMetrics();
      goal.setEngName(trimInput(engName));
      goal.setEngComment(trimInput(engComment));
      int i = 0;
      for (Metric metric : metrics) {
          metric.setEngName(trimInput(engMetricName[i]));
          metric.setEngUnit(trimInput(engUnit[i]));
          metric.setEngPublicDescription(trimInput(engPublicDescription[i]));
          metric.setEngInfoSource(trimInput(engInfoSource[i]));
          metric.setEngStartLevelComment(trimInput(engStartLevelComment[i]));
          metric.setEngTargetLevelComment(trimInput(engTargetLevelComment[i]));
          i++;
      }
      hibernate.update(goal);
      hibernate.flush();
    }

    session.setAttribute("message",(messages.get("translationSuccess")));

    return redirect(Home.class);

  }

  public String trimInput(String input) {
    input = input.trim();
    if ("".equals(input)) {
      return null;
    } else {
      return input;
    }
  }

  public void checkErrors() {
    checkGoalId();
    checkEngName();
    checkEngComment();
    checkEngMetricName();
    checkEngPublicDescription();
    checkEngUnit();
    checkEngInfoSource();
    checkEngStartLevelSource();
    checkEngTargetLevelSource();
  }

  private void checkEngInfoSource() {
    if(errors.containsKey("engInfoSource")){
      errorsList.add(messages.get("error"));
    }
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

  private void checkEngTargetLevelSource() {
    if (errors.containsKey("engTargetLevelComment"))
      errorsList.add(messages.get("error"));
  }

  private void checkEngStartLevelSource() {
    if (errors.containsKey("engStartLevelComment"))
      errorsList.add(messages.get("error"));
  }
}