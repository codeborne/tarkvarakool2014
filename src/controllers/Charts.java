package controllers;

import framework.Result;
import framework.Role;

public class Charts extends AnonymousChart {

  @Override @Role("anonymous")
  public Result get(){
    return render();
  }
}
