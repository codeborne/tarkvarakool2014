package controllers.admin;

import framework.Result;
import framework.Role;

public class Charts extends AdminChart {

  @Override @Role("admin")
  public Result get(){
    return render();
  }
}
