package controllers.admin.goals;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hibernate.criterion.Order.asc;

public class Home extends UserAwareController {
  public java.util.List<Goal> goals = new ArrayList<>();
  public Long id;
  public Integer sequenceNumber;
  public Set<String> errorsList = new HashSet<>();

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
    if (previousSequenceNumber!=sequenceNumber) {
      goalToBeChanged.setSequenceNumber(0);
      tryUpdate(goalToBeChanged);

      if (previousSequenceNumber < sequenceNumber) {
        for (Goal goal : goals) {
          Integer currentSequenceNumber = goal.getSequenceNumber();
          if (currentSequenceNumber <= sequenceNumber && currentSequenceNumber > previousSequenceNumber) {
            goal.setSequenceNumber(currentSequenceNumber - 1);
            tryUpdate(goal);
          }
        }
      }
      if (previousSequenceNumber > sequenceNumber) {
        Collections.reverse(goals);
        for (Goal goal : goals) {
          Integer currentSequenceNumber = goal.getSequenceNumber();
          if (currentSequenceNumber >= sequenceNumber && currentSequenceNumber < previousSequenceNumber) {
            goal.setSequenceNumber(currentSequenceNumber + 1);
            tryUpdate(goal);
          }
        }
      }
      goalToBeChanged.setSequenceNumber(sequenceNumber);
      tryUpdate(goalToBeChanged);
    }
    return redirect(Home.class);
  }

  private void tryUpdate(Goal goal) {
    try {
      hibernate.update(goal);
      hibernate.flush();
    }
    catch (ConstraintViolationException e){
      errorsList.add("Muutmine ebaõnnestus.");
      //render("");
    }
  }
}
