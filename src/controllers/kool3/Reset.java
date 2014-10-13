package controllers.kool3;

import framework.Controller;
import framework.Redirect;

public class Reset extends Controller {

  @Override
  public void get() {
    session.removeAttribute("tere");
    throw new Redirect("calc");
  }
}
