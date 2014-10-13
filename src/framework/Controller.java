package framework;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Controller extends RequestState {
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

  @SuppressWarnings("unchecked")
  protected  <T> T fromSession(Class<T> clazz) {
    T value = (T) session.getAttribute(clazz.getName());
    if (value == null) {
      try {
        value = clazz.newInstance();
        session.setAttribute(clazz.getName(), value);
      }
      catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return value;
  }
}
