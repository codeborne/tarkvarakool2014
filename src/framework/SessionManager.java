package framework;

import org.eclipse.jetty.server.session.HashSessionManager;


public class SessionManager extends HashSessionManager {

  public SessionManager() {
    super();
    _httpOnly = true;
  }
}
