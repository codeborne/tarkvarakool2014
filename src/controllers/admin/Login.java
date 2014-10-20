package controllers.admin;

import controllers.UserAwareController;
import controllers.admin.goals.Home;
import framework.Result;
import model.User;
import org.apache.commons.codec.DecoderException;
import org.hibernate.criterion.Restrictions;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

import static controllers.admin.goals.Password.*;

public class Login extends UserAwareController {

  public String username;
  public String password;
  public String error;

  public Login() throws InvalidKeySpecException, NoSuchAlgorithmException {
    if (hibernate.createCriteria(User.class).list().isEmpty())
      hibernate.save(new User("Delia","pass"));
  }

  @Override
  public Result get() {
    return render();
  }

  @Override
  public Result post() throws InvalidKeySpecException, NoSuchAlgorithmException, DecoderException {
    List<User> userList = (ArrayList<User>) hibernate.createCriteria(User.class).add(Restrictions.eq("username", username)).list();
    if (userList.isEmpty() || !validatePassword(password, userList.get(0).getPassword())) {
      error = "Vale kasutajanimi voi parool";
      return render();
    } else {
      session.setAttribute("username", username);
      return redirect(Home.class);
    }
  }
}
