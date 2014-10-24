package controllers.admin.goals;

import framework.Result;
import framework.Role;
import model.Goal;

public class Modify extends Save {
  public Long id;

  public Modify() {
    title = "Muuda eesmärk";
    buttonTitle = "Muuda";
  }

  @Override
  @Role("admin")
  public Result get() {
    Goal goal = (Goal) hibernate.get(Goal.class, id);
    if (goal == null) {
      return redirect(Add.class);
    } else {
      name = goal.getName();
      comment = goal.getComment();
      budget = goal.getBudget();
    }
    return render("admin/goals/form");
  }

  @Override
  protected void save() {
    Goal goal = (Goal) hibernate.get(Goal.class, id);
    goal.setBudget(budget);
    goal.setName(name);
    goal.setComment(comment);
    hibernate.update(goal);
  }
}
