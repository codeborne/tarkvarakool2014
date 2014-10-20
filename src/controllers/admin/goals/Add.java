package controllers.admin.goals;

import framework.Result;
import framework.Role;
import model.Goal;

public class Add extends Save {

  public Add() {
    title = "Lisage uus eesmärk";
    buttonTitle = "Lisa";
  }

  @Override
  @Role("admin")
  public Result get(){
    return render("admin/goals/form");
  }

  @Override
  protected void save() {
    hibernate.save(new Goal(name, budget));
  }
}
