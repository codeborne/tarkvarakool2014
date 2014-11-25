package controllers.admin.goals;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.hibernate.criterion.Order.asc;

public class Home extends UserAwareController {
  public java.util.List<Goal> goals = new ArrayList<>();
  public Long id;
  public Integer sequenceNumber;
  public Set<String> errorsList = new HashSet<>();
  public String message;
  public boolean[] isEverythingTranslated;

  @Override
  @Role("admin")
  public Result get() {
    message = (String) session.getAttribute("message");
    session.removeAttribute("message");

    goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();
    isEverythingTranslated = checkTranslationStatus();

    return render();
  }


  @Override
  @Role("admin")
  public Result post() {
    goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();
    Goal goalToBeChanged = (Goal) hibernate.get(Goal.class, id);
    Integer previousSequenceNumber = goalToBeChanged.getSequenceNumber();
    if (sequenceNumber > goals.size()) {
      sequenceNumber = goals.size();
    }
    if (!previousSequenceNumber.equals(sequenceNumber)) {
      goalToBeChanged.setSequenceNumber(0);
      hibernate.update(goalToBeChanged);
      hibernate.flush();

      if (previousSequenceNumber < sequenceNumber) {
        for (Goal goal : goals) {
          Integer currentSequenceNumber = goal.getSequenceNumber();
          if (currentSequenceNumber <= sequenceNumber && currentSequenceNumber > previousSequenceNumber) {
            goal.setSequenceNumber(currentSequenceNumber - 1);
            hibernate.update(goal);
            hibernate.flush();
          }
        }
      }
      if (previousSequenceNumber > sequenceNumber) {
        Collections.reverse(goals);
        for (Goal goal : goals) {
          Integer currentSequenceNumber = goal.getSequenceNumber();
          if (currentSequenceNumber >= sequenceNumber && currentSequenceNumber < previousSequenceNumber) {
            goal.setSequenceNumber(currentSequenceNumber + 1);
            hibernate.update(goal);
            hibernate.flush();
          }
        }
      }
      goalToBeChanged.setSequenceNumber(sequenceNumber);
      hibernate.update(goalToBeChanged);
      hibernate.flush();
    }

    return render();
  }

  private boolean[] checkTranslationStatus() {
    boolean[] isEverythingTranslated = new boolean[goals.size()];
    int i = 0;
    for(Goal goal:goals) {
      isEverythingTranslated[i] = true;
      if (isBlank(goal.getEngName()) || (!isBlank(goal.getComment()) && isBlank(goal.getEngComment()))) {
        isEverythingTranslated[i] = false;
      }
      else  {
        List<Metric> metrics = goal.getPublicMetrics();
        for (Metric metric : metrics) {
          if ((!isBlank(metric.getUnit()) && isBlank(metric.getEngUnit())) || (!isBlank(metric.getPublicDescription()) && isBlank(metric.getEngPublicDescription())) || isBlank(metric.getEngName())) {
            isEverythingTranslated[i] = false;
          }
        }
      }
      i++;
    }
    return isEverythingTranslated;
  }
}

