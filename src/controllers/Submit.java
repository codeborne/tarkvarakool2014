package controllers;

import framework.Redirect;

public class Submit {

  public void post() {
    throw new Redirect("/admin/");
  }
}
