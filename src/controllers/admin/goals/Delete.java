package controllers.admin.goals;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;


public class Delete extends UserAwareController {

  public Long id;

  @Override
  @Role("admin")
  public Result post() {
    Goal goal = (Goal) hibernate.get(Goal.class, id);
    if (goal != null) {
      hibernate.delete(goal);
    }
    return redirect(Home.class);

  }

}
