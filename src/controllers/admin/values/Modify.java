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
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Modify extends UserAwareController {
  public Long goalId;
  public Long metricId;
  public Integer year;
  public String value;
  public Boolean isForecast;
  public String jsonResponse;
  public BigDecimal comparableValue;
  public Set<String> errorsList = new HashSet<>();

  protected BigDecimal valueAsNumber;

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
    convertValueToBigDecimal();
    if (errorsList.isEmpty()) {
      List metricList = hibernate.createCriteria(Metric.class).add(Restrictions.eq("id", metricId))
                             .createCriteria("goal").add(Restrictions.eq("id", goalId))
                             .list();
      if (metricList.size()==1) {
        Metric metric = (Metric) metricList.get(0);


        if(isForecast) {
          metric.getForecasts().put(year, valueAsNumber);
          comparableValue = metric.getValues().get(year);
        }
        else {
          metric.getValues().put(year, valueAsNumber);
          comparableValue = metric.getForecasts().get(year);
        }
        hibernate.update(metric);
        hibernate.flush();
      } else {
        errorsList.add(messages.get("error"));
      }

    }

    String returnValue = valueAsNumber == null ? "" : valueAsNumber.toString().replace(".", ",");

    jsonResponse = new Gson().toJson(new JsonResponse(errorsList, returnValue,
      comparableValue == null ? "" : comparableValue.toString()));
    return render();
  }

  private void convertValueToBigDecimal() {

    if (!isBlank(value)) {
      value = value.replace(",", ".");
      try{
        valueAsNumber = new BigDecimal(value);
        if(valueAsNumber.remainder(new BigDecimal(1)).equals(new BigDecimal(0))){
          valueAsNumber = valueAsNumber.setScale(0, HALF_UP);
        }
        else {
          valueAsNumber = valueAsNumber.setScale(1, HALF_UP);
        }
      }catch (NumberFormatException e){
        errorsList.add(messages.get("errorInsertValue"));
      }
      if(valueAsNumber.toString().length()>38) {
        errorsList.add(messages.get("errorValue"));
      }
    }
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
      errorsList.add(messages.get("error"));
    }
  }
}
