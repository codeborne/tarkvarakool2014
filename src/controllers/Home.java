package controllers;

import framework.Result;
import framework.Role;
import model.Goal;

import java.util.ArrayList;

import static org.hibernate.criterion.Order.asc;

public class Home extends UserAwareController {

  public java.util.List<Goal> goals = new ArrayList<>();

  @Override @Role("anonymous")
  public Result get(){
    goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();
    return render();
  }


}
