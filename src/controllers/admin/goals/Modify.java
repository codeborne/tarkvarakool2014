package controllers.admin.goals;

import model.Goal;

public class Modify extends Save {
  public Long id;

  @Override
  protected void save() {
    Goal goal = (Goal) hibernate.get(Goal.class, id);
    goal.setBudget(budget);
    goal.setName(name);
    goal.setComment(comment);
    hibernate.update(goal);
  }
}
