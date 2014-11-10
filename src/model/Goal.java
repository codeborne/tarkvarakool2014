package model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Goal {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;
  private String engName;

  private String comment;
  private String engComment;

  @Column(nullable = false)
  private Integer budget;

  @Column(nullable = false, unique = true)
  private Integer sequenceNumber;



  @OrderBy("orderNumber")
  @OneToMany(mappedBy = "goal")
  private Set<Metric> metrics;


  @ElementCollection
  @JoinTable(name = "YearlyBudget", joinColumns = @JoinColumn(name = "goal_id"))
  @MapKeyColumn(name="year")
  @Column(name="yearlyBudget")
  private Map<Integer, Long> yearlyBudgets = new HashMap<>();

  private Goal() {
  }

  public Goal(String name, Integer budget) {
    this.name = name;
    this.budget = budget;
  }


  public Goal(String name, String comment, Integer budget, Integer sequenceNumber) {
    this.comment = comment;
    this.name = name;
    this.budget = budget;
    this.sequenceNumber = sequenceNumber;
  }

  public Set<Metric> getMetrics() {
    return metrics;
  }
  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getBudget() {
    return budget;
  }

  public Map<Integer, Long> getYearlyBudgets() {
    return yearlyBudgets;
  }

  public String getComment() {
    return comment;
  }

  public Integer getSequenceNumber() {
    return sequenceNumber;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setBudget(Integer budget) {
    this.budget = budget;
  }

  public void setYearlyBudgets(Map<Integer, Long> yearlyBudgets) {
    this.yearlyBudgets = yearlyBudgets;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public void setSequenceNumber(Integer sequenceNumber) {
    this.sequenceNumber = sequenceNumber;
  }

  public String getEngComment() {
    return engComment;
  }

  public void setEngComment(String engComment) {
    this.engComment = engComment;
  }

  public String getEngName() {
    return engName;
  }

  public void setEngName(String engName) {
    this.engName = engName;
  }

  public void setMetrics(Set<Metric> metrics) {
    this.metrics = metrics;
  }
}
