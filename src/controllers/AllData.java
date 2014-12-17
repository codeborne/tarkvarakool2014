package controllers;

import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.nio.charset.Charset;
import java.util.List;

import static org.hibernate.criterion.Order.asc;

public class AllData extends UserAwareController {

  private StringBuilder data = new StringBuilder();

  @Override @Role("anonymous")
  public Result get() throws Exception {

    data.append("Nimetus,Algtase,2014,2015,2016,2017,2018,2019,2020,Sihttase,Ühik,Kirjeldus,Allikas,Kuhu\r\n");
    List<Goal> goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();
    for (Goal goal:goals){
      createGoalRow(goal);
      List<Metric> metrics = goal.getPublicMetrics();
      for (Metric metric:metrics){
        createMetricsValuesRow(metric);
        createMetricsForecastsRow(metric);
      }
    }
    return attachment(data.toString(), Charset.forName("utf8"), "text/csv", "andmed.csv");
  }

  private void createMetricsForecastsRow(Metric metric) {
    data.append("Prognoos,");
    data.append(metric.getCommentOnStartLevel()==null?"":metric.getCommentOnStartLevel());
    data.append(",");
    addValues(metric);
    data.append(metric.getCommentOnTargetLevel()==null?"":metric.getCommentOnTargetLevel());
    data.append(", ,");
    data.append(metric.getPrivateDescription()==null?"":metric.getPrivateDescription());
    data.append("\r\n");
  }

  private void addValues(Metric metric) {
    for (int year = MINIMUM_YEAR; year <= MAXIMUM_YEAR; year++){
      data.append(metric.getForecasts().get(year)==null?"":metric.getForecasts().get(year));
      data.append(",");
    }
  }

  private void createMetricsValuesRow(Metric metric) {
    data.append(metric.getName());
    data.append(",");
    data.append(metric.getStartLevel()==null?"":metric.getStartLevel());
    data.append(",");
    addForecasts(metric);
    data.append(metric.getTargetLevel()==null?"":metric.getTargetLevel());
    data.append(",");
    data.append(metric.getUnit()==null?"":metric.getUnit());
    data.append(",");
    data.append(metric.getPublicDescription()==null?"":metric.getPublicDescription());
    data.append(",");
    data.append(metric.getInfoSource()==null?"":metric.getInfoSource());
    data.append(",");
    data.append(metric.getInstitutionToReport()==null?"":metric.getInstitutionToReport());
    data.append("\r\n");
  }

  private void addForecasts(Metric metric) {
    for (int year = MINIMUM_YEAR; year <= MAXIMUM_YEAR; year++){
      data.append(metric.getValues().get(year)==null?"":metric.getValues().get(year));
      data.append(",");
    }
  }

  private void createGoalRow(Goal goal) {
    data.append(goal.getName());
    data.append(",");
    data.append(goal.getComment()==null?"":goal.getComment());
    data.append(",");
    data.append(goal.getBudget());
    data.append("\r\n");
  }
}
