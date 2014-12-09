package controllers;

import framework.Result;
import framework.Role;

public class Chart extends AnonymousChart {

  @Override @Role("anonymous")
  public Result post(){
    prepareJsonResponse();
    return json();
  }
}
