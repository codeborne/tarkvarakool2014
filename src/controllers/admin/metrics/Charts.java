package controllers.admin.metrics;

import controllers.UserAwareController;
import framework.NoBind;
import framework.Result;
import framework.Role;
import model.Metric;

public class Charts extends UserAwareController {
  public Long metricId;
  @NoBind
  public Metric metric;

  @Override @Role("admin")
  public Result get(){
    metric=(Metric) hibernate.get(Metric.class,metricId);
    return render();
  }
}
