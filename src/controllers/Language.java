package controllers;

import controllers.admin.goals.Home;
import framework.Redirect;
import framework.Result;
import framework.Role;

public class Language extends UserAwareController {

  public String locale;

  @Override
  @Role("anonymous")
  public Result get() {

      if("changeLanguage".equals(locale)) {
        session.setAttribute("locale", "et");
        return redirect(Home.class);
      }
      else {
        session.setAttribute("locale", locale);
        return new Redirect(request.getHeader("Referer"));
      }
  }
}
