package controllers;

import com.google.gson.Gson;
import model.Metric;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMetricChart extends UserAwareController {
  public Integer minimumYear = UserAwareController.MINIMUM_YEAR;
  public Integer maximumYear = UserAwareController.MAXIMUM_YEAR;
  public String jsonResponse;
  public Long metricId;
  public Metric metric;

  public void prepareJsonResponse(){
    metric = (Metric) hibernate.get(Metric.class, metricId);
    List<String> row = new ArrayList<>();

    List<String> header = createHeader();
    row.add(new Gson().toJson(header));
    for (int year = minimumYear; year <= maximumYear; year++) {
      String values = createValuesRowByYear(year);
      row.add(values);
    }
    jsonResponse =  row.toString();
  }

  String createValuesRowByYear(int year) {
    String tooltip = "";
    BigDecimal value;
    if(year == minimumYear && metric.getValues().get(minimumYear) == null){
      value = new BigDecimal(0);
    }
    else {
      value = metric.getValues().get(year);
      if (value != null){
        tooltip = year+" "+metric.getValues().get(year)+metric.getUnit();
      }
    }
    return "[" + "\"" + year + "\"," +value+",\""+tooltip+"\"]";
  }

  List<String> createHeader() {
    List<String> header = new ArrayList<>();
    header.add(messages.get("vertAxisValue") +" ("+metric.getUnitDependingOnLanguage(getLanguage())+")");
    header.add(metric.getMetricNameDependingOnLanguage(getLanguage()));
    header.add("null");
    return header;
  }
}
