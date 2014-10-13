package controllers;

import framework.Controller;
import model.Goal;

import java.util.List;

public class Goals extends Controller {
  public String insertedGoal;
  public Integer insertedBudget;
  public List<Goal> allGoals;
  public String message;

  @Override
  public void post(){
    if ("".equals(insertedGoal) && insertedBudget == null){
      message = "Palun sisestage eesmärk ja eelarve.";
    }
    else if ("".equals(insertedGoal)){
      message = "Palun sisestage eesmärk.";
    }
    else if (insertedBudget == null){
      message = "Palun sisestage eelarve.";
    }
    else {
      Goal goal = new Goal();
      goal.setGoalText(insertedGoal);
      goal.setBudget(insertedBudget);
      hibernate.save(goal);
      insertedGoal="";
      insertedBudget=null;
    }
    allGoals = hibernate.createCriteria(Goal.class).list();
  }


}
