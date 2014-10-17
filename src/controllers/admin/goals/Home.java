package controllers.admin.goals;

import framework.Controller;
import framework.Result;
import model.Goal;

import java.util.ArrayList;

public class Home extends Controller {
  public java.util.List<Goal> goals = new ArrayList<>();

  @Override
  public Result get() {
    goals = hibernate.createCriteria(Goal.class).list();
    return render();
  }

}
