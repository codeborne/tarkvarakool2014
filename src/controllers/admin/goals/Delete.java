package controllers.admin.goals;

import controllers.UserAwareController;
import controllers.admin.Login;
import framework.Result;
import model.Goal;


public class Delete extends UserAwareController {

  public Long id;

  @Override
  public Result post() {
    if (!isLoggedIn()) return redirect(Login.class);
    Goal goal = (Goal) hibernate.get(Goal.class, id);
    if (goal != null) {
      hibernate.delete(goal);
    }
    return redirect(Home.class);
  }

}
