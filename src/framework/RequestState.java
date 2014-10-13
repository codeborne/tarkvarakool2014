package framework;

import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class RequestState {
  protected HttpServletRequest request;
  protected HttpServletResponse response;
  protected HttpSession session;
  protected Session hibernate;
  public Messages.Resolver messages;

  static ThreadLocal<RequestState> threadLocal = new ThreadLocal<RequestState>() {
    @Override protected RequestState initialValue() {
      return new RequestState() {
        @Override void initFieldsBeforeSubclasses() {
        }
      };
    }
  };

  public RequestState() {
    initFieldsBeforeSubclasses();
  }

  void initFieldsBeforeSubclasses() {
    request = threadLocal.get().request;
    response = threadLocal.get().response;
    session = threadLocal.get().session;
    hibernate = threadLocal.get().hibernate;
    messages = threadLocal.get().messages;
  }
}
