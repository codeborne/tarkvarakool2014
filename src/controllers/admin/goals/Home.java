package controllers.admin.goals;

import controllers.UserAwareController;
import controllers.admin.Login;
import framework.Result;
import model.Goal;

import java.util.ArrayList;

public class Home extends UserAwareController {
  public java.util.List<Goal> goals = new ArrayList<>();

  @Override
  public Result get() {
    if (!isLoggedIn()) return redirect(Login.class);
    goals = hibernate.createCriteria(Goal.class).list();
    return render();
  }

}
