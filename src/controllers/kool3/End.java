package controllers.kool3;

import framework.Controller;
import framework.Redirect;

public class End extends Controller {
  public String name;
  public String email;


  @Override
  public void post() {
    if (name == null || name.isEmpty()) {
      throw new Redirect("/kool3/hello", "warning", "Please enter your name");
    }

  }
}
