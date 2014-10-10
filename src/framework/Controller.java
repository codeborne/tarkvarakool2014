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
  public Messages.Resolver messages;

  protected Map<String, Throwable> errors = new LinkedHashMap<>();

  static ThreadLocal<Controller> requestState = new ThreadLocal<Controller>() {
    @Override protected Controller initialValue() {
      return new Controller() {
        @Override void initFieldsBeforeSubclasses() {
        }
      };
    }
  };

  public Controller() {
    initFieldsBeforeSubclasses();
  }

  void initFieldsBeforeSubclasses() {
    request = requestState.get().request;
    response = requestState.get().response;
    session = requestState.get().session;
    hibernate = requestState.get().hibernate;
    messages = requestState.get().messages;
  }

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
