package controllers;

import framework.Controller;
import framework.Result;
import model.Goal;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class Modify extends Controller {
  public Long id;
  public String name;
  public Integer budget;

  public List<String> errorsList = new ArrayList<>();

  @Override
  public Result get() {
    Goal goal = (Goal) hibernate.get(Goal.class, id);
    if (goal == null) {
      return redirect(Add.class);
    } else {
      name = goal.getName();
      budget = goal.getBudget();
    }
    return render();
  }

  @Override
  public Result post() {
    if (name != null)
      name = name.trim();

    if (name == null || name.length() == 0){
      errorsList.add("Sisestage eesmärk.");
    }
    if (budget == null || budget<=0 || errors.get("budget") instanceof NumberFormatException)
      errorsList.add("Sisestage korrektne eelarve.");

    if (errors.containsKey("name") || (errors.containsKey("budget") && !(errors.get("budget") instanceof NumberFormatException)))
      errorsList.add("Tekkis viga.");

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
      return redirect(Goals.class);

    return render();
  }
}
