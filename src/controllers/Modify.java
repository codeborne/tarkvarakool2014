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
    Throwable nameError = errors.get("name");
    if (nameError != null) {
      errorsList.add(nameError.getMessage());
    }

    Throwable budgetError = errors.get("budget");
    if (budgetError != null) {
      if (budgetError instanceof NumberFormatException) {
        errorsList.add("Please use a valid number for budget field");
      } else {
        errorsList.add(budgetError.getMessage());
      }
    }

    try {
      if (name.length() == 0)
        throw new Exception("Goal field cannot be empty");

      if (errorsList.isEmpty()) {
        Goal goal = (Goal) hibernate.get(Goal.class, id);
        goal.setBudget(budget);
        goal.setName(name);
        hibernate.update(goal);
      }
    } catch (ConstraintViolationException e) {
      errorsList.add("This goal already exists");
    } catch (Exception e) {
      errorsList.add(e.getMessage());
    }

    if (errorsList.isEmpty())
      throw new Redirect("goals");
    else
      goals = hibernate.createCriteria(Goal.class).list();
  }
}
