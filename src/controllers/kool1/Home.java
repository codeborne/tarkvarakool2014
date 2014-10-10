package controllers.kool1;

import framework.Controller;
import framework.Redirect;

public class Home extends Controller {


  public String warning;

  public void post() {
    String userInputPassword = request.getParameter("password");
    String userInputUsername = request.getParameter("username");
    String password = "kasutaja";
    String username = "parool";

    if (userInputPassword.equals(password) && userInputUsername.equals(username)) throw new Redirect("/kool1/nextpage");
      else if (!userInputPassword.equals(password) && !userInputUsername.equals(username))
      warning="Palun sisesta õige salasõna ja kasutajanimi";
      else if (!userInputPassword.equals(password) && userInputUsername.equals(username))
      warning="Palun sisesta õige salasõna!";
      else
      warning="Palun sisesta õige kasutajanimi!";


  }
}