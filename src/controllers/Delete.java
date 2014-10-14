package controllers;

import framework.Controller;
import framework.Redirect;
import model.Goal;


public class Delete extends Controller {

  public Long id;

  @Override
  public void post() {
    Goal goal = (Goal) hibernate.get(Goal.class, id);
    if (goal != null) {
      hibernate.delete(goal);
    }
    throw new Redirect("goals");
  }

}
