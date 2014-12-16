package controllers;

import com.google.gson.Gson;
import model.Metric;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

public abstract class AbstractMetricChart extends UserAwareController {
  public String jsonResponse;
  public Long metricId;
  public Metric metric;


  public void prepareJsonResponse(){
    metric = (Metric) hibernate.get(Metric.class, metricId);
    List<String> valuesRow = new ArrayList<>();
    List<String> forecastsRow = new ArrayList<>();

    List<String> header = createHeader();
    valuesRow.add(new Gson().toJson(header));
    forecastsRow.add(new Gson().toJson(header));
    for (int year = MINIMUM_YEAR; year <= MAXIMUM_YEAR; year++) {
      String values = createValuesRowByYear(year);
      valuesRow.add(values);
      String forecasts = createForecastsRowByYear(year);
      forecastsRow.add(forecasts);
    }
    List<List<String>> result = new ArrayList<>();
    result.add(valuesRow);
    result.add(forecastsRow);
      jsonResponse = result.toString();
  }

  String createValuesRowByYear(int year) {
    BigDecimal value;
    if(year == MINIMUM_YEAR && metric.getValues().get(MINIMUM_YEAR) == null || metric.getValues().get(year) ==null){
      value = new BigDecimal(0);
    }
    else {
      value = metric.getValues().get(year);
    }
    return "[" + "\"" + year +"\"," +value+"]";
  }

  String createForecastsRowByYear(int year) {
    BigDecimal value;
    if(year == MINIMUM_YEAR && metric.getForecasts().get(MINIMUM_YEAR) == null){
      value = new BigDecimal(0);
    }
    else {
      value = metric.getForecasts().get(year);
    }
    return "[" + "\"" + year + "\"," +value+"]";
  }


  List<String> createHeader() {
    List<String> header = new ArrayList<>();
    if(isBlank(metric.getUnit()) && isBlank(metric.getEngUnit())){
      header.add("");
    }
    else {
      header.add(metric.getUnitDependingOnLanguage(getLanguage()));
    }
    header.add(metric.getMetricNameDependingOnLanguage(getLanguage()));
    return header;
  }
}
