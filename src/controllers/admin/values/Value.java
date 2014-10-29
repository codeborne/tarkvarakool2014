package controllers.admin.values;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;

import java.util.ArrayList;

import static org.hibernate.criterion.Order.asc;

public class Value extends UserAwareController{

  public java.util.List<Goal> goals = new ArrayList<>();
  public Integer minimumYear = UserAwareController.MINIMUM_YEAR;
  public Integer maximumYear = UserAwareController.MAXIMUM_YEAR;

  @Override
  @Role("admin")
  public Result get() {
    goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();
    return render();
  }

}
