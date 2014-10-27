package controllers.admin.goals;

import framework.Result;
import framework.Role;
import model.Goal;

public class Add extends Save {

  Integer sequenceNumber;


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
    sequenceNumber = hibernate.createCriteria(Goal.class).list().size()+1;
    hibernate.save(new Goal(name, comment, budget, sequenceNumber));
  }
}
