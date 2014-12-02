package controllers.admin.user;

import controllers.UserAwareController;
import controllers.admin.goals.Home;
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

public class Changepassword extends UserAwareController {

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
    if (errorsList.isEmpty() && validatePasswords(user.getPassword())){
      tryUpdate(user);
    }
    if(errorsList.isEmpty()){
      session.setAttribute("message",(messages.get("passwordChangeSuccess")));
      return redirect(Home.class);
    }
    else {
      return render();
    }
  }

  private void tryUpdate(User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
    try {
      user.setPassword(newPasswordFirst);
      hibernate.update(user);
    } catch (ConstraintViolationException e) {
      hibernate.getTransaction().rollback();
      errorsList.add(messages.get("errorFailedToChangePassword"));
    }
  }

  private boolean validatePasswords(String password) throws NoSuchAlgorithmException, DecoderException, InvalidKeySpecException {
    if(!validatePassword(oldPassword, password)){
      errorsList.add(messages.get("errorWrongOldPassword"));
    }
    else if (!newPasswordFirst.equals(newPasswordSecond)){
      errorsList.add(messages.get("errorNewPasswordsDiffer"));
    }
    else if (oldPassword.equals(newPasswordFirst)){
      errorsList.add(messages.get("errorNewAndOldPasswordEqual"));
    }
    else if(newPasswordFirst.length()< PASSWORD_MIN_LENGTH){
      errorsList.add(messages.get("passwordTooShort"));
    }
    return errorsList.isEmpty();
  }

  private void checkErrors() {
    checkErrorsMap();
    checkAllFieldsAreFilled();
  }

  private void checkErrorsMap(){
    if(errors.containsKey("oldPassword") || errors.containsKey("newPasswordFirst") || errors.containsKey("newPasswordSecond")){
      errorsList.add(messages.get("error"));
    }
  }

  private void checkAllFieldsAreFilled(){
    if(isBlank(oldPassword) || isBlank(newPasswordFirst)|| isBlank(newPasswordSecond)){
      errorsList.add(messages.get("errorAllFieldsMustBeFilledIn"));
    }
  }


}
