package controllers.admin;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.User;
import org.apache.commons.codec.DecoderException;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Set;

import static helpers.Password.validatePassword;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Settings extends UserAwareController {

  public String username;
  public String oldPassword;
  public String newPasswordFirst;
  public String newPasswordSecond;
  public Set<String> errorsList = new HashSet<>();

  @Override @Role("admin")
  public Result get(){
    return render();
  }
  @Override @Role("admin")
  public Result post() throws NoSuchAlgorithmException, DecoderException, InvalidKeySpecException {
    username = (String) session.getAttribute("username");
    User user = (User) hibernate.createCriteria(User.class).add(Restrictions.eq("username", username)).uniqueResult();
    checkErrors();
    validatePasswords(user.getPassword());
    if (errorsList.isEmpty()){
    try {
      user.setPassword(newPasswordFirst);
      hibernate.update(user);
    } catch (ConstraintViolationException e) {
      hibernate.getTransaction().rollback();
      errorsList.add(messages.get("errorFailedToChangePassword"));
    }
    }
    return render();
  }

  private void validatePasswords(String password) throws NoSuchAlgorithmException, DecoderException, InvalidKeySpecException {
    if(!validatePassword(oldPassword, password)){
      errorsList.add(messages.get("errorWrongOldPassword"));
    }
    else if (!newPasswordFirst.equals(newPasswordSecond)){
      errorsList.add(messages.get("errorNewPasswordsDiffer"));
    }
  }

  private void checkErrors() {
    checkOldPassword();
    checkNewPasswordFirst();
    checkNewPasswordSecond();
  }

  private void checkOldPassword() {
    if(errors.containsKey("oldPassword") || isBlank(oldPassword)){
      errorsList.add(messages.get("errorEnteringOldPassword"));
    }
  }
  private void checkNewPasswordFirst(){
    if(errors.containsKey("newPasswordFirst") || isBlank(newPasswordFirst)){
      errorsList.add(messages.get("errorEnteringNewPasswordFirst"));
    }
  }

  private void checkNewPasswordSecond() {
    if (errors.containsKey("newPasswordSecond") || isBlank(newPasswordSecond)) {
      errorsList.add(messages.get("errorEnteringNewPasswordSecond"));
    }
  }
}
