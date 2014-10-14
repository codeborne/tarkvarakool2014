package controllers;

import framework.Controller;
import framework.Redirect;
import model.Goal;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class Add extends Controller {
  public String name;
  public Integer budget;

  public List<Goal> goals = new ArrayList<>();
  public List<String> errorsList = new ArrayList<>();

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

      if (errorsList.isEmpty())
        hibernate.saveOrUpdate(new Goal(name, budget));
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
