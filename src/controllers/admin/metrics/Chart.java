package controllers.admin.metrics;

import com.google.gson.Gson;
import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Metric;

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
    header.add(messages.get("year"));
    header.add(metric.getName());

    List<String> row = new ArrayList<>();
    row.add( new Gson().toJson(header));
    for (int year = minimumYear; year<=maximumYear;year++) {

      String values = "[" + "\"" + year + "\"," +metric.getValues().get(year)+"]";
      row.add(values);
    }

    jsonResponse =  row.toString();
    return render();
  }


}
