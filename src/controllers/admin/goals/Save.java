package controllers.admin.goals;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;
import org.hibernate.exception.ConstraintViolationException;

import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Save extends UserAwareController {
  public String name;
  public String comment;
  public Integer budget;
  public Long id;

  public Set<String> errorsList = new HashSet<>();

  @Override
  @Role("admin")
  public Result post() {
    checkErrors();
    if (errorsList.isEmpty()) {
      try {
        trimAllInput();
        save();
      } catch (ConstraintViolationException e) {
        hibernate.getTransaction().rollback();
        errorsList.add(messages.get("errorInsertedGoal"));
      }
    }
    return render("/admin/goals/errors");
  }

  private void trimAllInput() {
    name = name.trim();
    if (comment != null) {
      comment = comment.trim();
    }
  }

  private void save() {
    if (id != null) {
      Goal goal = (Goal) hibernate.get(Goal.class, id);
      if (goal != null) {
        modifySave(goal);
      } else {
        addSave();
      }
    } else {
      addSave();
    }
  }

  private void modifySave(Goal goal) {
    goal.setBudget(budget);
    goal.setName(name);
    goal.setComment(comment);
    hibernate.update(goal);
    hibernate.flush();
  }

  private void addSave() {
    Integer sequenceNumber = hibernate.createCriteria(Goal.class).list().size() + 1;
    hibernate.save(new Goal(name, comment, budget, sequenceNumber));
    hibernate.flush();
  }

  private void checkErrors() {
    checkName();
    checkComment();
    checkBudget();
  }

  private void checkName() {
    if (errors.containsKey("name") || isBlank(name))
      errorsList.add(messages.get("errorInsertGoal"));
  }

  private void checkComment() {
    if (errors.containsKey("comment"))
      errorsList.add(messages.get("errorInsertComment"));
  }

  private void checkBudget() {
    if (errors.containsKey("budget") || budget == null || budget <= 0)
      errorsList.add(messages.get("errorInsertBudget"));
  }
}
