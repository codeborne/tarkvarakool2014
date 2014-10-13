package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Goal {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private BigDecimal budget;

  public String getName() {
    return name;
  }

  public BigDecimal getBudget() {
    return budget;
  }

  public Goal() {
  }

  public Goal(String name, BigDecimal budget) {
    this.name = name;
    this.budget = budget;
  }


}
