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
  public List<String> errorsList = new ArrayList<>();

  @Override
  public void post() {
      if (name == null || name.length() == 0)
          errorsList.add("Sisestage eesmärk.");

      if (budget == null || budget<=0 || errors.get("budget") instanceof NumberFormatException)
          errorsList.add("Sisestage korrektne eelarve.");

      if (errors.containsKey("name") || (errors.containsKey("budget") && !(errors.get("budget") instanceof NumberFormatException)))
          errorsList.add("Tekkis viga.");

      try {
          if (errorsList.isEmpty()) {
              hibernate.save(new Goal(name, budget));
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