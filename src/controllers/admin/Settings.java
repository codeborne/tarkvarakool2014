package controllers.admin;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;

public class Settings extends UserAwareController {

  @Override
  @Role("admin")
  public Result get() {
    return render();
  }
}
