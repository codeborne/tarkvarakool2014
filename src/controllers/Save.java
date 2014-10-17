package controllers;

import framework.Controller;
import framework.Result;
import org.hibernate.exception.ConstraintViolationException;

import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

public abstract class Save extends Controller {
  public String name;
  public Integer budget;
  public Set<String> errorsList = new HashSet<>();

  @Override
  public Result post() {
    checkErrors();

    if (errorsList.isEmpty()) {
      try {
        return saveAndRedirect();
      } catch (ConstraintViolationException e) {
        errorsList.add("See eesmärk on juba sisestatud.");
      } catch (Exception e) {
        errorsList.add("Tekkis viga.");
      }
    }

    return render();
  }

  private Result saveAndRedirect() {
    name = name.trim();
    save();
    return redirect(Goals.class);
  }

  private void checkErrors() {
    checkName();
    checkBudget();
  }
  private void checkName() {
    if (errors.containsKey("name") || isBlank(name))
      errorsList.add("Sisestage eesmärk.");
  }
  private void checkBudget() {
    if (errors.containsKey("budget") || budget == null || budget <= 0)
      errorsList.add("Sisestage korrektne eelarve (1 - 2 147 483 647).");
  }

  protected abstract void save();
}
