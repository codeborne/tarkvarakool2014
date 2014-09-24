package controllers;

import javax.servlet.http.HttpServletRequest;

public class Index {

  public HttpServletRequest request;

  public void get() {
  }

  public int getParameterCount() {
    return request.getParameterMap().size();
  }
}
