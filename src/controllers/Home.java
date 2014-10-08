package controllers;

import javax.servlet.http.HttpServletRequest;

public class Home extends Controller {

  public void get() {
  }

  public int getParameterCount() {
    return request.getParameterMap().size();
  }
}
