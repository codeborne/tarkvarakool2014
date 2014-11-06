package controllers.admin.goals;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;

public class Translation extends UserAwareController {

  public Long goalId;
  public Long metricId;

  public Goal goal;


  @Override
  @Role("admin")
  public Result get() {
    goal = (Goal) hibernate.get(Goal.class, goalId);
    return render();
  }


}