package controllers.admin.goals;

import framework.Result;
import framework.Role;
import model.Goal;

public class Add extends Save {

  @Override
  @Role("admin")
  public Result get(){
    title = "Lisage uus eesmärk";
    buttonTitle = "Lisa";
    return render("admin/goals/form");
  }

  @Override
  protected void save() {
    hibernate.save(new Goal(name, budget));
  }
}
