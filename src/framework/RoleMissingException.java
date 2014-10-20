package framework;

import java.lang.reflect.Method;

public class RoleMissingException extends NotAuthorizedException {

  public RoleMissingException(Method method) {
    super("@Role annotation missing from method " + method);
  }
}
