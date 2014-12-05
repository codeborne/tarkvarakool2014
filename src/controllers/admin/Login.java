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
import java.util.List;

import static helpers.Password.generateSalt;
import static helpers.Password.validatePassword;
import static org.apache.commons.codec.binary.Hex.encodeHexString;

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
    List<User> userList = loadUsersFromDatabase();
    if (userList.isEmpty() || !validatePassword(password, userList.get(0).getPassword())) {
      error = (messages.get("errorUsernameAndPassword"));
      return render();
    } else {
      session.setAttribute("username", username);
      session.setAttribute("csrfToken", generateCSRFToken());
      session.setAttribute("locale", "et");
      return redirect(Home.class);
    }
  }

  String generateCSRFToken() throws NoSuchAlgorithmException {
    return encodeHexString(generateSalt());
  }

  List<User> loadUsersFromDatabase() {
    return (List<User>) hibernate.createCriteria(User.class).add(Restrictions.eq("username", username)).list();
  }
}
