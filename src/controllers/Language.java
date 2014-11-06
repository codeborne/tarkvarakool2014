package controllers;

import framework.Redirect;
import framework.Result;
import framework.Role;

public class Language extends UserAwareController {

  public String locale;

  @Override
  @Role("anonymous")
  public Result get() {
    session.setAttribute("locale", locale);
    return new Redirect(request.getHeader("Referer"));
  }
}
