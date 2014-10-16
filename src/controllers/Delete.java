package controllers;

import framework.Controller;
import framework.Result;
import model.Goal;


public class Delete extends Controller {

  public Long id;

  @Override
  public Result post() {
    Goal goal = (Goal) hibernate.get(Goal.class, id);
    Goal goal1 = new Goal(goal.getName(), goal.getBudget());
    if (goal != null) {
      hibernate.delete(goal1);
    }
    return redirect(Goals.class);
  }

}
