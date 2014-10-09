package controllers;

import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;

public abstract class Controller {
  public HttpServletRequest request;
  public Session hibernate;

  public void get() {
  }

  public void post() {
  }

  public void put() {
  }

  public void delete() {
  }

  public void options() {
  }
}
