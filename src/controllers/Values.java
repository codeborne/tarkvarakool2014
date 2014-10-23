package controllers;

import framework.Result;
import framework.Role;
import model.Goal;

import java.util.ArrayList;

public class Values extends UserAwareController{

  public java.util.List<Goal> goals = new ArrayList<>();
  public Integer minimumYear = UserAwareController.MINIMUM_YEAR;
  public Integer maximumYear = UserAwareController.MAXIMUM_YEAR;

  @Override
  @Role("anonymous")
  public Result get() {
    goals = hibernate.createCriteria(Goal.class).list();
    return render();
  }

}
