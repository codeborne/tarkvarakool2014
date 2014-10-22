package controllers.admin.metrics;

import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

public class Add extends Save {

  @Override @Role("admin")
  public Result get()  {

    goal = (Goal) hibernate.get(Goal.class, goalId);
    return render();
  }


  @Override
  protected void save() {
    hibernate.save(new Metric(goal, name, publicDescription, privateDescription, startLevel, commentOnStartLevel,
      targetLevel, commentOnTargetLevel, infoSource, institutionToReport));
  }
}
