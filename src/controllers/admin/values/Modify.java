package controllers.admin.values;

import controllers.UserAwareController;
import framework.Result;
import model.Value;

import java.util.HashSet;
import java.util.Set;

public class Modify extends UserAwareController {
  Long goalId;
  Long metricId;
  Short year;
  Long value;

  public Set<String> errorsList = new HashSet<>();

  @Override
  public Result post() {
    checkErrors();
    if (errorsList.isEmpty())
      hibernate.save(new Value(goalId, metricId, year, value));

    return render();

  }

  public void checkErrors() {
    checkGoalId();
    checkMetricId();
  }

  public void checkGoalId() {
    if (errors.containsKey("goalId") || goalId==null)
      errorsList.add("Tekkis viga.");
  }
  public void checkMetricId() {
    if (errors.containsKey("metricId") || metricId ==null)
      errorsList.add("Tekkis viga.");
  }
  public void checkYear() {

  }
  public void checkValue() {

  }
}
