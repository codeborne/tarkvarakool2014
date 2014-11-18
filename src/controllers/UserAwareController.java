package controllers;

import com.google.common.collect.ImmutableSet;
import framework.Controller;
import framework.Render;

import java.util.Set;

public abstract class UserAwareController extends Controller {

  private static final ImmutableSet<String> ADMIN_ROLES = ImmutableSet.of("admin", "anonymous");
  private static final ImmutableSet<String> ANONYMOUS_ROLES = ImmutableSet.of("anonymous");
  public static final Integer MINIMUM_YEAR = 2014;
  public static final Integer MAXIMUM_YEAR = 2020;
  public String homeUrl;
  public String valuesUrl;

  public String getLoggedInUsername() {
    return (String) session.getAttribute("username");
  }
  public String getLanguage() {
    if (session.getAttribute("locale") == null)
      session.setAttribute("locale", "et");
    else if (request != null && "admin".equals(request.getPathInfo().substring(1).split("/")[0])){
      session.setAttribute("locale", "et");
    }
    return (String) session.getAttribute("locale");
  }

  @Override
  protected Set<String> getRoles() {
    return getLoggedInUsername() == null ? ANONYMOUS_ROLES : ADMIN_ROLES;
  }

  @Override
  protected Render render() {
    if(request != null) {
      if("admin".equals(request.getPathInfo().substring(1).split("/")[0])) {
        homeUrl = "/admin/goals/home";
        valuesUrl = "/admin/values/value";
      } else {
        homeUrl = "/home";
        valuesUrl = "/values";
      }
    }

    return super.render();
  }

  @Override
  protected Render render(String template) {
    if(request != null) {
      if ("admin".equals(request.getPathInfo().substring(1).split("/")[0])) {
        homeUrl = "/admin/goals/home";
        valuesUrl = "/admin/values/value";
      } else {
        homeUrl = "/home";
        valuesUrl = "/values";
      }
    }

    return super.render(template);
  }

}
