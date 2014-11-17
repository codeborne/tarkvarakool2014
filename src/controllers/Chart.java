package controllers;

import com.google.gson.Gson;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hibernate.criterion.Order.asc;

public class Chart extends UserAwareController {

  public Integer minimumYear = UserAwareController.MINIMUM_YEAR;
  public Integer maximumYear = UserAwareController.MAXIMUM_YEAR;

  public String jsonResponse;

  public List<Goal> goals = new ArrayList<>();




  @Override @Role("anonymous")
  public Result post(){
    goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();

    Goal goal = goals.get(0);
    Set<Metric> metrics = goal.getMetrics();


    List<String> header = asList("year","metric1","metric2");

    List<String> row = new ArrayList<>();
    row.add( new Gson().toJson(header));
    for (int year = minimumYear; year<=maximumYear;year++){

      String values = "["+year ;
      for (Metric metric:metrics){
        BigDecimal value = metric.getValues().get(year);
        values = values +  "," +value;
      }
      values = values + "]";

      row.add(values);

    }

    jsonResponse =  row.toString();
    System.out.println(jsonResponse);
    return render();
  }



}
