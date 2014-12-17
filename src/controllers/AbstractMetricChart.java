package controllers;

import com.google.gson.Gson;
import framework.NoBind;
import model.Metric;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;
import static org.apache.commons.lang3.StringUtils.isBlank;

public abstract class AbstractMetricChart extends UserAwareController {
  @NoBind
  public String jsonResponse;
  public Long metricId;
  @NoBind
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
    BigDecimal value = metric.getValues().get(year)== null ? new BigDecimal(0) : roundValue(metric.getValues().get(year));
    String forecast = metric.getForecasts().get(year)==null ?"-":roundValue(metric.getForecasts().get(year)).toString()+metric.getUnitDependingOnLanguage(getLanguage());
    String measured = metric.getValues().get(year)==null ?"-":roundValue(metric.getValues().get(year)).toString()+metric.getUnitDependingOnLanguage(getLanguage());
    String tooltip = year +" "+ messages.get("forecast")+": "+forecast +
      " "+messages.get("measuredValue")+": "+measured;
    String annotation = metric.getValues().get(year)== null ?"":" (" + value + metric.getUnitDependingOnLanguage(getLanguage()) + ")";
    return "[" + "\"" + year + annotation + "\"," + value + ",\"" + tooltip + "\"]";
  }

  private BigDecimal roundValue(BigDecimal value) {
    if(value.toString().contains(".0")){
      value = value.setScale(0, HALF_UP);
    }
    return value;
  }

  String createForecastsRowByYear(int year) {
    BigDecimal value = metric.getForecasts().get(year)== null ? new BigDecimal(0) : metric.getForecasts().get(year);
    return "[" + "\"" + year + "\"," +value+", \"\"]";
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
      header.add("null");
    return header;
  }
}
