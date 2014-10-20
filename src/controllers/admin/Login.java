package controllers.admin;

import framework.Controller;
import framework.Result;
import model.User;
import org.apache.commons.codec.DecoderException;
import org.hibernate.criterion.Restrictions;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

import static controllers.admin.goals.Password.*;

public class Login extends Controller {

  public String username;
  public String password;
  public String action;
  private String error;
  private String loggedInUsername;

  public Login() throws InvalidKeySpecException, NoSuchAlgorithmException {
    if (hibernate.createCriteria(User.class).list().isEmpty())
      hibernate.save(new User("Delia","pass"));
  }

  public String getLoggedInUsername() {
    return loggedInUsername;
  }

  public String getError() {
    return error;
  }

  @Override
  public Result get() {
    loggedInUsername = (String) session.getAttribute("username");
    return render();
  }

  @Override
  public Result post() throws InvalidKeySpecException, NoSuchAlgorithmException, DecoderException {
    if(action.equals("login")) {
      List<User> userList = (ArrayList<User>) hibernate.createCriteria(User.class).add(Restrictions.eq("username", username)).list();
      if (userList.isEmpty() || !validatePassword(password, userList.get(0).getPassword())) {
        error = "Vale kasutajanimi voi parool";
        return render();
      } else {
        session.setAttribute("username", username);
        return redirect(Login.class);
      }
    } else {
      session.removeAttribute("username");
      return redirect(Login.class);
    }
  }
}
