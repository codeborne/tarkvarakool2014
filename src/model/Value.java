package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Value {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private Long goalId;

  @Column(nullable = false)
  private Long metricId;

  @Column(nullable = false)
  private Short year;

  private Long value;

  public Value(Long goalId, Long metricId, Short year, Long value) {
    this.goalId = goalId;
    this.metricId = metricId;
    this.year = year;
    this.value = value;
  }

  public Long getId() {
    return id;
  }

  public Long getGoalId() {
    return goalId;
  }

  public Long getMetricId() {
    return metricId;
  }

  public Short getYear() {
    return year;
  }

  public Long getValue() {
    return value;
  }

  public void setGoalId(Long goalId) {
    this.goalId = goalId;
  }

  public void setMetricId(Long metricId) {
    this.metricId = metricId;
  }

  public void setYear(Short year) {
    this.year = year;
  }

  public void setValue(Long value) {
    this.value = value;
  }
}
