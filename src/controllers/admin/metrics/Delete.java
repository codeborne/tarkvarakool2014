package controllers.admin.metrics;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Metric;

public class Delete extends UserAwareController {
  public Long id;
  public Long goalId;

  @Override
  @Role("admin")
  public Result post() throws Exception {

    Metric metric = (Metric) hibernate.get(Metric.class, id);
    if (metric != null) {
      hibernate.delete(metric);
    }

    return redirect(Metrics.class).withParam("goalId", goalId);

  }
}
