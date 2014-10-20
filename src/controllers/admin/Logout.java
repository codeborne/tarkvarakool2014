package controllers.admin;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;

public class Logout extends UserAwareController{

  @Override @Role("admin")
  public Result get(){
    session.removeAttribute("username");
    return redirect(Login.class);
  }
}
