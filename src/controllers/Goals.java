package controllers;

import framework.Controller;
import framework.Redirect;
import model.Goal;
import java.math.BigDecimal;
import java.util.List;

public class Goals extends Controller {

  public String name;
  public BigDecimal budget;
  public List<Goal> goals;

  @Override
  public void get() {
    goals = hibernate.createCriteria(Goal.class).list();
  }

  @Override
  public void post() {
    hibernate.saveOrUpdate(new Goal(name, budget));
    throw new Redirect("goals");
  }
}