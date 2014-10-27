package controllers.admin.goals;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import org.hibernate.exception.ConstraintViolationException;

import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

public abstract class Save extends UserAwareController {
  public String name;
  public String comment;
  public Integer budget;
  public Set<String> errorsList = new HashSet<>();
  public String title;
  public String buttonTitle;

  @Override
  @Role("admin")
  public Result post() {
    checkErrors();

    if (errorsList.isEmpty()) {
      try {
        doSave();
      } catch (ConstraintViolationException e) {
        hibernate.getTransaction().rollback();
        errorsList.add("See eesmärk on juba sisestatud.");
      }
    }

    return render("/admin/goals/form");
  }

  private void doSave() {
    name = name.trim();
    if (comment != null) {
      comment = comment.trim();
      if (comment.equals(""))
        comment = null;
    }

    save();
    hibernate.flush();
  }

  private void checkErrors() {
    checkName();
    checkComment();
    checkBudget();
  }
  private void checkName() {
    if (errors.containsKey("name") || isBlank(name))
      errorsList.add("Sisestage eesmärk.");
  }
  private void checkComment() {
    if (errors.containsKey("comment"))
      errorsList.add("Sisestage kommentaar.");
  }
  private void checkBudget() {
    if (errors.containsKey("budget") || budget == null || budget <= 0)
      errorsList.add("Sisestage korrektne eelarve (1 - 2 147 483 647).");
  }

  protected abstract void save();
}
