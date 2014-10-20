package controllers.admin;

import controllers.UserAwareController;
import framework.Result;

public class Logout extends UserAwareController{

  @Override
  public Result get(){
    session.removeAttribute("username");
    return redirect(Login.class);
  }
}
