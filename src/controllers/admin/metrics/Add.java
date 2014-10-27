package controllers.admin.metrics;

import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class Add extends Save {

  public Add() {
    title = "Lisage uus mõõdik";
    buttonTitle = "Lisa";
  }

  @Override
  @Role("admin")
  public Result get() {
    goal = (Goal) hibernate.get(Goal.class, goalId);
    orderNumber = (Double) hibernate.createCriteria(Metric.class)
                                    .add(Restrictions.eq("goal", goal))
                                    .setProjection(Projections.max("orderNumber")).uniqueResult();
    orderNumber = Math.ceil(orderNumber == null ? 0 : orderNumber) + 1;
    return render("admin/metrics/form");
  }


  @Override
  protected void save() {
    hibernate.save(new Metric(goal, name, unit, publicDescription, privateDescription, startLevel, commentOnStartLevel,
      targetLevel, commentOnTargetLevel, infoSource, institutionToReport, orderNumber));
    hibernate.flush();
  }
}
