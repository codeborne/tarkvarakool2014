package controllers;

import framework.Controller;
import framework.Redirect;
import model.Goal;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class Modify extends Controller {
  public Long id;
  public String name;
  public Integer budget;

  public List<Goal> goals = new ArrayList<>();
  public List<String> errorsList = new ArrayList<>();

  @Override
  public void get() {
    Goal goal = (Goal) hibernate.get(Goal.class, id);
    if (goal != null) {
      name = goal.getName();
      budget = goal.getBudget();
    } else {
      throw new Redirect("add");
    }
  }

  @Override

  public void post() {
    if (name == null || name.length() == 0){
      errorsList.add("Sisestage eesmärk.");
    }
    if (budget == null || budget<=0 || errors.get("budget") instanceof NumberFormatException){
      errorsList.add("Sisestage korrektne eelarve.");
    }
    else if (errors.containsKey("name") || errors.containsKey("budget")) {
      errorsList.add("Tekkis viga.");
    }

    try {
      if (errorsList.isEmpty()) {
        Goal goal = (Goal) hibernate.get(Goal.class, id);
        goal.setBudget(budget);
        goal.setName(name);
        hibernate.update(goal);
      }
    }
    catch (ConstraintViolationException e) {
      errorsList.add("See eesmärk on juba sisestatud.");
    }
    catch (Exception e) {
      errorsList.add("Tekkis viga.");
    }

    if (errorsList.isEmpty())
      throw new Redirect("goals");
  }
}
