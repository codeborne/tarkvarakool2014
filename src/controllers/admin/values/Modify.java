package controllers.admin.values;

import com.google.gson.Gson;
import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Metric;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.math.RoundingMode.HALF_UP;

public class Modify extends UserAwareController {
  public Long goalId;
  public Long metricId;
  public Integer year;
  public BigDecimal value;
  public Boolean isForecast;
  public String jsonResponse;
  public BigDecimal comparableValue;
  public Set<String> errorsList = new HashSet<>();

  private class JsonResponse {
    public Set<String> errorsList;
    public String value;
    public String comparableValue;

    private JsonResponse(Set<String> errorsList, String value, String comparableValue) {
      this.errorsList = errorsList;
      this.value = value;
      this.comparableValue = comparableValue;
    }
  }

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


        if(isForecast) {
          metric.getForecasts().put(year, value);
          comparableValue = metric.getValues().get(year);
        }
        else {
          metric.getValues().put(year, value);
          comparableValue = metric.getForecasts().get(year);
        }
        hibernate.update(metric);
        hibernate.flush();
      } else {
        errorsList.add(messages.get("error"));
      }

    }

    jsonResponse = new Gson().toJson(new JsonResponse(errorsList, value == null ? "" : value.toString(),
      comparableValue == null ? "" : comparableValue.toString()));
    return render();
  }

  public void checkErrors() {
    checkGoalId();
    checkMetricId();
    checkValue();
    checkYear();
  }

  public void checkGoalId() {
    if (errors.containsKey("goalId") || goalId==null)
      errorsList.add(messages.get("error"));
  }

  public void checkMetricId() {
    if (errors.containsKey("metricId") || metricId==null)
      errorsList.add(messages.get("error"));
  }

  public void checkYear() {
    if (errors.containsKey("year") || year==null || year>MAXIMUM_YEAR || year<MINIMUM_YEAR)
      errorsList.add(messages.get("error"));
  }

  public void checkValue() {
    if (errors.containsKey("value")) {
      errorsList.add(messages.get("errorInsertValue"));
    } else if (value != null) {
      if(value.remainder(new BigDecimal(1)).equals(new BigDecimal(0))){
        value = value.setScale(0, HALF_UP);
      }
      else {
        value = value.setScale(1, HALF_UP);
      }
      if(value.toString().length()>38)
        errorsList.add(messages.get("errorValue"));
    }
  }
}
