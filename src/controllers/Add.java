package controllers;

import framework.Result;
import model.Goal;

public class Add extends Save {

  @Override
  public Result get(){
    title = "Muuda eesmärk";
    buttonTitle = "Muuda";
    return render("form");
  }

  @Override
  protected void save() {
    hibernate.save(new Goal(name, budget));
  }
}
