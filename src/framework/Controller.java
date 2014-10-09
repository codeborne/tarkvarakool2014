package framework;

import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Controller {

  protected HttpServletRequest request;
  protected HttpServletResponse response;
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
