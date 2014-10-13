package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Goal {

  @Id
  @GeneratedValue
  private Long id;

 private String goal;
  private int sum;

  public Goal(String goal, int sum) {
    this.goal = goal;
    this.sum = sum;
  }

  public Goal() {
  }


  public String getGoal(){
    return goal;
  }
  public int getSum(){
    return sum;
  }

}
