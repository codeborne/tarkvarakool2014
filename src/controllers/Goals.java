package controllers;

import framework.Controller;
import framework.Redirect;
import model.Goal;

import java.util.List;

public class Goals extends Controller {

  public List<Goal> goals;
  public String goal;
  public Integer sum;
  public String warning;

  @Override
  public void get() {
    goals = hibernate.createCriteria(Goal.class).list();
  }

  @Override
  public void post() {

    if (goal == null || sum == null) {
      warning= "Palun sisesta nõutud väljad";
    }
    else {
      hibernate.saveOrUpdate(new Goal(goal, sum));
      throw new Redirect("goals");
    }
  }
}
