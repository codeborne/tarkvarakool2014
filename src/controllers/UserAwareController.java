package controllers;

import controllers.admin.Login;
import framework.Controller;
import framework.Result;
import org.apache.commons.codec.DecoderException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public abstract class UserAwareController extends Controller {

  public String getLoggedInUsername() {
    return (String) session.getAttribute("username");
  }
  public boolean isLoggedIn(){
    return getLoggedInUsername()!=null;
  }

  @Override
  public Result get() {
    if (!isLoggedIn()) return redirect(Login.class);
    return render();
  }

  @Override
  public Result post() throws InvalidKeySpecException, NoSuchAlgorithmException, DecoderException {
    if (!isLoggedIn()) return redirect(Login.class);
    return render();
  }
}
