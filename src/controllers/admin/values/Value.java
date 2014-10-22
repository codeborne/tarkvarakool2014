package controllers.admin.values;

import controllers.UserAwareController;
import framework.Controller;
import framework.Result;
import framework.Role;
import model.Goal;

import java.util.ArrayList;

public class Value extends UserAwareController{

  public java.util.List<Goal> goals = new ArrayList<>();

  @Override
  @Role("admin")
  public Result get() {
    goals = hibernate.createCriteria(Goal.class).list();
    return render();
  }

}
