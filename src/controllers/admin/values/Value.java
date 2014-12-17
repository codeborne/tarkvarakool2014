package controllers.admin.values;

import controllers.UserAwareController;
import framework.NoBind;
import framework.Result;
import framework.Role;
import model.Goal;

import static org.hibernate.criterion.Order.asc;

public class Value extends UserAwareController{
  @NoBind
  public java.util.List<Goal> goals;
  @NoBind
  public Integer minimumYear = MINIMUM_YEAR;
  @NoBind
  public Integer maximumYear = MAXIMUM_YEAR;

  @Override
  @Role("admin")
  public Result get() {
    goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();
    return render();
  }

}
