package controllers.admin.values;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Metric;
import org.hibernate.criterion.Restrictions;

import java.util.*;

public class Modify extends UserAwareController {
  public Long goalId;
  public Long metricId;
  public Integer year;
  public Long value;

  public Set<String> errorsList = new HashSet<>();

  @Override
  @Role("admin")
  public Result post() {
    checkErrors();
    if (errorsList.isEmpty()) {
      List metricList = hibernate.createCriteria(Metric.class).add(Restrictions.eq("id", metricId))
                             .createCriteria("goal").add(Restrictions.eq("id", goalId))
                             .list();
      if (metricList.size()==1) {
        Metric metric = (Metric) metricList.get(0);
        metric.getValues().put(year, value);
        hibernate.update(metric);
        hibernate.flush();
      } else {
        errorsList.add("Tekkis viga.");
      }
    }

    return render();
  }

  public void checkErrors() {
    checkGoalId();
    checkMetricId();
    checkYear();
    checkValue();
  }

  public void checkGoalId() {
    if (errors.containsKey("goalId") || goalId==null)
      errorsList.add("Tekkis viga.");
  }

  public void checkMetricId() {
    if (errors.containsKey("metricId") || metricId==null)
      errorsList.add("Tekkis viga.");
  }

  public void checkYear() {
    if (errors.containsKey("year") || year==null || year>MAXIMUM_YEAR || year<MINIMUM_YEAR)
      errorsList.add("Tekkis viga.");
  }

  public void checkValue() {
    if (errors.containsKey("value") || value==null)
      errorsList.add("Sisestage korrektne väärtus.");
  }

}
