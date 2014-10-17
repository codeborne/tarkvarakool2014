package controllers;

import model.Goal;

public class Add extends Save {

  @Override
  protected void save() {
    hibernate.save(new Goal(name, budget));
  }
}
