package controllers.admin;

import controllers.UserAwareController;
import controllers.admin.goals.Home;
import framework.Result;
import framework.Role;
import model.User;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Adduser extends UserAwareController {
  public List userNames = new ArrayList<>();
  public String username;
  public String passwordFirst;
  public String passwordSecond;
  public Set<String> errorsList = new HashSet<>();

  @Override
  @Role("admin")
  public Result get() {
    userNames = hibernate.createCriteria(User.class).setProjection(Projections.property("username")).list();
    return render();
  }

  @Override @Role("admin")
  public Result post() throws InvalidKeySpecException, NoSuchAlgorithmException {
    checkErrors();
    if (errorsList.isEmpty()){
      trySave();
    }
    if(errorsList.isEmpty()){
      return redirect(Home.class);
    }
    else {
      return render();
    }
  }

  private void trySave() throws InvalidKeySpecException, NoSuchAlgorithmException {
    try {
      User user = new User();
      user.setUsername(username);
      user.setPassword(passwordFirst);
      hibernate.save(user);
    } catch (ConstraintViolationException e) {
      hibernate.getTransaction().rollback();
      errorsList.add(messages.get("errorUserExists"));
    }
  }

  private void checkErrors() {
    checkErrorsMap();
    checkAllFieldsAreFilled();
    if(errorsList.isEmpty()) {
      validatePasswords();
    }
  }

  private void validatePasswords() {
    if (!passwordFirst.equals(passwordSecond)){
      errorsList.add(messages.get("errorPasswordsDiffer"));
    }
    else if(passwordFirst.length()< PASSWORD_MIN_LENGTH){
      errorsList.add(messages.get("passwordTooShort"));
    }
  }

  private void checkErrorsMap(){
    if(errors.containsKey("username") || errors.containsKey("passwordFirst") || errors.containsKey("passwordSecond")){
      errorsList.add(messages.get("error"));
    }
  }

  private void checkAllFieldsAreFilled(){
    if(isBlank(username) || isBlank(passwordFirst)|| isBlank(passwordSecond)){
      errorsList.add(messages.get("errorAllFieldsMustBeFilledIn"));
    }
  }
}
