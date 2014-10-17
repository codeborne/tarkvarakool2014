package controllers;

import framework.Result;
import model.Goal;

public class Add extends Save {

  @Override
  public Result get(){
    title = "Lisage uus eesmärk";
    buttonTitle = "Lisa";
    return render("form");
  }

  @Override
  protected void save() {
    hibernate.save(new Goal(name, budget));
  }

  @Override
  public Result get() {
    title="Lisa uus eesmärk";
    buttonTitle="Lisa";
    return render("form");
  }
}
