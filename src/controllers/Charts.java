package controllers;

import framework.Result;
import framework.Role;
import model.Goal;

public class Charts extends UserAwareController {

  public Long goalId;
  public Goal goal;


  @Override @Role("anonymous")
  public Result get(){

    goal = (Goal) hibernate.get(Goal.class, goalId);

    return render();
  }



}
