package controllers.admin.goals;

import framework.Result;
import model.Goal;

public class Add extends Save {

  @Override
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
