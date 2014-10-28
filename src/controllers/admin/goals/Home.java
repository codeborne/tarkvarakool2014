package controllers.admin.goals;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;

import java.util.ArrayList;
import java.util.Collections;

import static org.hibernate.criterion.Order.asc;

public class Home extends UserAwareController {
  public java.util.List<Goal> goals = new ArrayList<>();
  public Long id;
  public Integer sequenceNumber;

  @Override
  @Role("admin")
  public Result get() {
    goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();
    return render();
  }

  @Override
  @Role("admin")
  public Result post() {
    goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();
    Goal goalToBeChanged = (Goal) hibernate.get(Goal.class, id);
    Integer previousSequenceNumber = goalToBeChanged.getSequenceNumber();
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
return redirect(Home.class);
  }
}
