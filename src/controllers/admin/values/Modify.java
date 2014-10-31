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

  public Set<String> errorsList = new HashSet<>();

  private class JsonResponse {
    public Set<String> errorsList;
    public String value;

    private JsonResponse(Set<String> errorsList, String value) {
      this.errorsList = errorsList;
      this.value = value;
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

        if(isForecast)
          metric.getForecasts().put(year, value);
        else
          metric.getValues().put(year, value);

        hibernate.update(metric);
        hibernate.flush();
      } else {
        errorsList.add("Tekkis viga.");
      }

    }

    jsonResponse = new Gson().toJson(new JsonResponse(errorsList, value == null ? "" : value.toString()));
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
    if (errors.containsKey("value")) {
      errorsList.add("Sisestage korrektne väärtus.");
    } else if (value != null) {
      value = value.setScale(1, HALF_UP);
      if(value.toString().length()>38)
        errorsList.add("Väärtus on liiga suur või väike.");
    }
  }
}
