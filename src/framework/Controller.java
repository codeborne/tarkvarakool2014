package framework;

import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Controller {
  protected HttpServletRequest request;
  protected HttpServletResponse response;
  protected HttpSession session;
  protected Session hibernate;
  protected Map<String, Throwable> errors = new LinkedHashMap<>();
  public Messages.Resolver messages;

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
