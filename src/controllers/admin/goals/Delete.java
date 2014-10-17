package controllers.admin.goals;

import framework.Controller;
import framework.Result;
import model.Goal;


public class Delete extends Controller {

  public Long id;

  @Override
  public Result post() {
    Goal goal = (Goal) hibernate.get(Goal.class, id);
    if (goal != null) {
      hibernate.delete(goal);
    }
    return redirect(Home.class);
  }

}
