package controllers.admin.metrics;

import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

public class Add extends Save {

  public Add() {
    title = "Lisage uus mõõdik";
    buttonTitle = "Lisa";
  }

  @Override
  @Role("admin")
  public Result get() {

    goal = (Goal) hibernate.get(Goal.class, goalId);
    return render("admin/metrics/form");
  }


  @Override
  protected void save() {
    hibernate.save(new Metric(goal, name, unit, publicDescription, privateDescription, startLevel, commentOnStartLevel,
      targetLevel, commentOnTargetLevel, infoSource, institutionToReport));
    hibernate.flush();
  }
}
