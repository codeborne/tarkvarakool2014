package controllers;

import framework.Redirect;

public class Submit extends Controller {

  public void post() {
    throw new Redirect("/admin/");
  }
}
