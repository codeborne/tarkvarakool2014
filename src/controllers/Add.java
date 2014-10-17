package controllers;

import model.Goal;

public class Add extends Save {

  protected void save() {
    hibernate.save(new Goal(name, budget));
  }
}
