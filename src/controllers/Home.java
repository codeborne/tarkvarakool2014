package controllers;

import framework.Result;
import framework.Role;

public class Home extends UserAwareController {

  @Override @Role("anonymous")
  public Result get() throws Exception {
    return render();
  }
}
