package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Goal {

  @Id
  @GeneratedValue
  private Long id;

  //not null
  private String goalText;

  private int budget;


  public String getGoalText() {
    return goalText;
  }

  public void setGoalText(String goalText) {
    this.goalText = goalText;
  }

  public int getBudget() {
    return budget;
  }

  public void setBudget(int budget) {
    this.budget = budget;
  }
}
