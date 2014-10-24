package controllers.admin.budgets;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;
import org.hibernate.criterion.Restrictions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Modify extends UserAwareController {

  public Long goalId;
  public Integer year;
  public Long yearlyBudget;

  public Set<String> errorsList = new HashSet<>();

  @Override
  @Role("admin")
  public Result post() {
    checkErrors();
    if (errorsList.isEmpty()) {
      List goalList = hibernate.createCriteria(Goal.class).add(Restrictions.eq("id", goalId)).list();
      if (goalList.size()==1) {
        Goal goal = (Goal) goalList.get(0);
        goal.getYearlyBudgets().put(year, yearlyBudget);
        hibernate.update(goal);
        hibernate.flush();
      } else {
        errorsList.add("Tekkis viga.");
      }

    }

    return render();
  }

  public void checkErrors() {
    checkGoalId();
    checkYearlyBudget();
    checkYear();

  }

  public void checkGoalId() {
    if (errors.containsKey("goalId") || goalId==null)
      errorsList.add("Tekkis viga.");
  }


  public void checkYear() {
    if (errors.containsKey("year") || year==null || year>MAXIMUM_YEAR || year<MINIMUM_YEAR)
      errorsList.add("Tekkis viga.");
  }

  public void checkYearlyBudget() {
    if (errors.containsKey("yearlyBudget") || yearlyBudget==null || yearlyBudget < 0)
      errorsList.add("Sisestage korrektne väärtus.");
  }
}
