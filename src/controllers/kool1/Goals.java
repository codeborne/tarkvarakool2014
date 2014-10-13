package controllers.kool1;

import framework.Controller;
import model.kool1.Goal;

import java.util.ArrayList;
import java.util.List;

public class Goals extends Controller {
  public String insertedGoal;
  public int insertedBudget;
  public List<Goal> allGoals = new ArrayList();

  @Override
  public void post(){
    Goal goal = new Goal();
    goal.setGoalText(insertedGoal);
    goal.setBudget(insertedBudget);
    hibernate.save(goal);

    allGoals = hibernate.createCriteria(Goal.class).list();
  }


}
