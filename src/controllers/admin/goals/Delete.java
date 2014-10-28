package controllers.admin.goals;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;

import java.util.List;


public class Delete extends UserAwareController {

  public Long id;


  @Override
  @Role("admin")
  public Result post() {
    Goal deletableGoal = (Goal) hibernate.get(Goal.class, id);
    if (deletableGoal != null) {
      hibernate.delete(deletableGoal);
      hibernate.flush();

        List<Goal> goals = hibernate.createCriteria(Goal.class).list();
        for (Goal goal : goals) {
          Integer sequenceNumber = goal.getSequenceNumber();
          if (sequenceNumber > deletableGoal.getSequenceNumber()) {
            goal.setSequenceNumber(sequenceNumber - 1);
            hibernate.update(goal);
            hibernate.flush();
        }
      }
    }
    return redirect(Home.class);

  }

}
