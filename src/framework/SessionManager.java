package framework;

import org.eclipse.jetty.server.session.HashSessionManager;


public class SessionManager extends HashSessionManager {

  public SessionManager(boolean isDevMode) {
    super();
    _httpOnly = true;
    _secureCookies = !isDevMode;
  }
}
