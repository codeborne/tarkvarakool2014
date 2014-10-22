package controllers;

import com.google.common.collect.ImmutableSet;
import framework.Controller;

import java.util.Set;

public abstract class UserAwareController extends Controller {

  private static final ImmutableSet<String> ADMIN_ROLES = ImmutableSet.of("admin", "anonymous");
  private static final ImmutableSet<String> ANONYMOUS_ROLES = ImmutableSet.of("anonymous");
  public static final Integer MINIMUM_YEAR = 2014;
  public static final Integer MAXIMUM_YEAR = 2020;

  public String getLoggedInUsername() {
    return (String) session.getAttribute("username");
  }

  @Override
  protected Set<String> getRoles() {
    return getLoggedInUsername() == null ? ANONYMOUS_ROLES : ADMIN_ROLES;
  }
}
