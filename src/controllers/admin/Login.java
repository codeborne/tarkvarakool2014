package controllers.admin;

import controllers.UserAwareController;
import controllers.admin.goals.Home;
import framework.Result;
import framework.Role;
import model.User;
import org.apache.commons.codec.DecoderException;
import org.hibernate.criterion.Restrictions;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import static helpers.Password.validatePassword;

public class Login extends UserAwareController {

  public String username;
  public String password;
  public String error;

  @Override @Role("anonymous")
  public Result get() {
    return render();
  }

  @Override @Role("anonymous")
  public Result post() throws InvalidKeySpecException, NoSuchAlgorithmException, DecoderException {
    List<User> userList = (ArrayList<User>) hibernate.createCriteria(User.class).add(Restrictions.eq("username", username)).list();
    if (userList.isEmpty() || !validatePassword(password, userList.get(0).getPassword())) {
      error = (messages.get("errorUsernameAndPassword"));
      return render();
    } else {
      session.setAttribute("username", username);
      return redirect(Home.class);
    }
  }
}
