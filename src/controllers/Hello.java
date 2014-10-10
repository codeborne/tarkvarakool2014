package controllers;

import framework.Controller;

public class Hello extends Controller {
  public String firstName;
  public String lastName;
  public String fullName;

  @Override
  public void post() {
    fullName = firstName + " " + lastName;
  }
}
