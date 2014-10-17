package controllers.admin.goals;

import framework.Result;
import model.Goal;

public class Modify extends Save {
  public Long id;


  @Override
  public Result get() {
    title = "Muuda eesmärk";
    buttonTitle = "Muuda";
    Goal goal = (Goal) hibernate.get(Goal.class, id);
    if (goal == null) {
      return redirect(Add.class);
    } else {
      name = goal.getName();
      budget = goal.getBudget();
    }
    return render("admin/goals/form");
  }

  @Override
  protected void save() {
    Goal goal = (Goal) hibernate.get(Goal.class, id);
    goal.setBudget(budget);
    goal.setName(name);
    hibernate.update(goal);
  }
}