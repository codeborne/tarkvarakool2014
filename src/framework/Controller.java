package framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Controller {
  protected HttpServletRequest request;
  protected HttpServletResponse response;
  protected Map<String, Throwable> errors = new LinkedHashMap<>();

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
