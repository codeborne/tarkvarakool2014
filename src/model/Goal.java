package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Goal {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private Integer budget;

  private Goal() {
  }

  public Goal(String name, Integer budget) {
    this.name = name;
    this.budget = budget;
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

}
