package controllers;

import com.google.gson.Gson;
import model.Goal;
import model.Metric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class AbstractChart extends UserAwareController {

  public static final List<String> CHART_COLORS = Arrays.asList("#1abc9c", "#3498db", "#9b59b6", "#34495e", "#f1c40f", "#e67e22", "#e74c3c", "#95a5a6", "#d35400", "#2980b9", "#16a085");

  public List<String> graphColors = CHART_COLORS;
  public String jsonResponse;
  public List<Goal> goals = new ArrayList<>();
  public Long goalId;

  private Goal goal;

  public void prepareJsonResponse() {
    Double availableBudget = getGoal().getBudget().doubleValue();

    List<Metric> metricsWithValidLevels = getMetricsWithValidLevels();

    List<String> header = createHeader(metricsWithValidLevels);

    List<String> row = new ArrayList<>();
    row.add(new Gson().toJson(header));

    for (int year = MINIMUM_YEAR-1; year <= MAXIMUM_YEAR+1; year++) {

      String values = "["  + year;
      values += createJsonForValuesOfYear(metricsWithValidLevels, year);

      if (getGoal().getYearlyBudgets().get(year) != null) {
        availableBudget = availableBudget - getGoal().getYearlyBudgets().get(year);
        values = values + "," + availableBudget/getGoal().getBudget().doubleValue() +","+ 1+ "]";
      } else if (year == MINIMUM_YEAR-1) {
        values = values + "," + availableBudget/getGoal().getBudget().doubleValue() + ","+1+ "]";
      } else {
        values = values + "," + null +","+ 1 + "]";
      }

      row.add(values);
    }
    jsonResponse = row.toString();
    System.out.println(jsonResponse);
  }

  private String createJsonForValuesOfYear(List<Metric> metrics, int year) {
    String values = "";
    for (Metric metric : metrics) {
      Double value = null;
      if (metric.hasValueForYear(year)) {
        value = (metric.getValues().get(year).doubleValue() - metric.getStartLevel()) / (metric.getTargetLevel() - metric.getStartLevel());
        value = Math.round(value * 1000.0) / 1000.0;
      } else if (year == 2014 && !metric.hasValueForYear(2014)) {
        value = 0.0;
      }
      values = values + "," + value;
    }
    return values;
  }

  private List<String> createHeader(List<Metric> metrics) {
    List<String> header = new ArrayList<>();
    header.add(messages.get("year"));
    for (Metric metric : metrics) {
      header.add(metric.getMetricNameDependingOnLanguage(getLanguage()));
    }
    header.add(messages.get("budgetLeft"));
    header.add("referenceLineForHundredPercent");
    return header;
  }

  public List<Metric> getMetricsWithValidLevels() {
    List<Metric> metricsWithValidLevels = new ArrayList<>();
    for (Metric metric : getMetrics(getGoal())) {
      if (metric.levelsAreValid()) {
        metricsWithValidLevels.add(metric);
      }
      if (metricsWithValidLevels.size() == CHART_COLORS.size()) {
        break;
      }
    }
    return metricsWithValidLevels;
  }

  public Goal getGoal() {
    if (goal == null) {
      goal = (Goal) hibernate.get(Goal.class, goalId);
    }
    return goal;
  }

  protected abstract Collection<Metric> getMetrics(Goal goal);
}
