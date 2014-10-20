package controllers;

import com.google.common.collect.ImmutableSet;
import framework.Controller;

import java.util.Set;

public abstract class UserAwareController extends Controller {

  private static final ImmutableSet<String> ADMIN_ROLES = ImmutableSet.of("admin");
  private static final ImmutableSet<String> ANONYMOUS_ROLES = ImmutableSet.of("anonymous");

  public String getLoggedInUsername() {
    return (String) session.getAttribute("username");
  }

  @Override
  protected Set<String> getRoles() {
    return getLoggedInUsername() == null ? ANONYMOUS_ROLES : ADMIN_ROLES;
  }
}
