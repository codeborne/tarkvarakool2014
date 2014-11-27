package controllers.admin.metrics;

import com.google.gson.Gson;
import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Metric;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Chart extends UserAwareController {
  public Integer minimumYear = UserAwareController.MINIMUM_YEAR;
  public Integer maximumYear = UserAwareController.MAXIMUM_YEAR;

  public String jsonResponse;

  public Long metricId;
  public Metric metric;


  @Override @Role("admin")
public Result post() {
  metric = (Metric) hibernate.get(Metric.class, metricId);

  List<String> header = new ArrayList<>();
    header.add("Mõõdiku väärtus ("+metric.getUnit()+")");
  header.add(metric.getName());
  header.add("null");

  List<String> row = new ArrayList<>();
  row.add( new Gson().toJson(header));
  for (int year = minimumYear; year<=maximumYear;year++) {
    String tooltip = year+" "+metric.getValues().get(year)+metric.getUnit();
    BigDecimal value;
    if(year == minimumYear && metric.getValues().get(minimumYear) == null){
      value = new BigDecimal(0);
    }
    else {
      value = metric.getValues().get(year);
    }

    String values = "[" + "\"" + year + "\"," +value+",\""+tooltip+"\"]";
    row.add(values);
  }

  jsonResponse =  row.toString();
  return render();
}

}
