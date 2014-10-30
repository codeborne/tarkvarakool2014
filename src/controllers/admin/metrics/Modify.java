package controllers.admin.metrics;

import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

public class Modify extends Save {
  public Long metricId;

  public Modify() {
    title = "Muuda mõõdik";
    buttonTitle = "Muuda";
  }

  @Override @Role("admin")
  public Result get() {
    goal = (Goal)hibernate.get(Goal.class, goalId);
    Metric metric = (Metric)hibernate.get(Metric.class, metricId);

    if(metric == null) {
      return redirect(Metrics.class).withParam("goalId", goalId);
    }
    else {
      name = metric.getName();
      unit = metric.getUnit();
      publicDescription = metric.getPublicDescription();
      privateDescription = metric.getPrivateDescription();
      startLevel = metric.getStartLevel();
      targetLevel = metric.getTargetLevel();
      commentOnStartLevel = metric.getCommentOnStartLevel();
      commentOnTargetLevel = metric.getCommentOnTargetLevel();
      infoSource = metric.getInfoSource();
      institutionToReport = metric.getInstitutionToReport();
      orderNumber = metric.getOrderNumber();
    }
    return render("admin/metrics/form");
  }

  @Override
  protected void save(){
    Metric metric = (Metric)hibernate.get(Metric.class, metricId);
    metric.setName(name);
    metric.setUnit(unit);
    metric.setPublicDescription(publicDescription);
    metric.setPrivateDescription(privateDescription);
    metric.setStartLevel(startLevel);
    metric.setTargetLevel(targetLevel);
    metric.setCommentOnStartLevel(commentOnStartLevel);
    metric.setCommentOnTargetLevel(commentOnTargetLevel);
    metric.setInfoSource(infoSource);
    metric.setInstitutionToReport(institutionToReport);
    metric.setOrderNumber(orderNumber);
    hibernate.update(metric);
    hibernate.flush();
  }
}
