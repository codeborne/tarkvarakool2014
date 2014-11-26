package controllers.admin;

import framework.Result;
import framework.Role;

public class Chart extends AdminChart {

  @Override @Role("admin")
  public Result post(){
    prepareJsonResponse();
    return render();
  }
}
