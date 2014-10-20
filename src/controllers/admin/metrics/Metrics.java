package controllers.admin.metrics;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;

public class Metrics extends UserAwareController{
  public Long goalId;
  public Goal goal;

 @Override  @Role("admin")
  public Result get(){
   goal = (Goal) hibernate.get(Goal.class, goalId);
   return render();
 }
}
