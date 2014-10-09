package controllers;

import framework.Controller;

public class Home extends Controller {

  public void get() {
      System.out.println("Get was called");
  }

  public int getParameterCount() {
    return request.getParameterMap().size();
  }
}
