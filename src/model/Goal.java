package model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Goal {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private Integer budget;


  @OrderBy("name")
  @OneToMany(mappedBy = "goal")
  private Set<Metric> metrics;

  private Goal() {
  }

  public Goal(String name, Integer budget) {
    this.name = name;
    this.budget = budget;
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

  public void setName(String name) {
    this.name = name;
  }

  public void setBudget(Integer budget) {
    this.budget = budget;
  }

}
