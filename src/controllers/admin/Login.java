package controllers.admin;

import framework.Controller;
import framework.Result;
import model.User;
import org.hibernate.criterion.Restrictions;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class Login extends Controller {

  public String username;
  public String password;
  public String error;
  public String action;
  public String loggedInUsername;

  public Login() throws InvalidKeySpecException, NoSuchAlgorithmException {
    if (hibernate.createCriteria(User.class).list().isEmpty())
      hibernate.save(new User("Delia","pass"));
  }

  @Override
  public Result get() {
    loggedInUsername = (String) session.getAttribute("username");
    return render();
  }

  @Override
  public Result post() throws InvalidKeySpecException, NoSuchAlgorithmException {
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

  public static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
    int iterations = 1000;
    char[] chars = password.toCharArray();
    byte[] salt = getSalt().getBytes();

    PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] hash = skf.generateSecret(spec).getEncoded();
    return iterations + ":" + toHex(salt) + ":" + toHex(hash);
  }

  private static String getSalt() throws NoSuchAlgorithmException {
    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
    byte[] salt = new byte[16];
    sr.nextBytes(salt);
    return salt.toString();
  }

  private static String toHex(byte[] array) throws NoSuchAlgorithmException {
    BigInteger bi = new BigInteger(1, array);
    String hex = bi.toString(16);
    int paddingLength = (array.length * 2) - hex.length();
    if(paddingLength > 0)
    {
      return String.format("%0"  +paddingLength + "d", 0) + hex;
    }else{
      return hex;
    }
  }

  public static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
    String[] parts = storedPassword.split(":");
    int iterations = Integer.parseInt(parts[0]);
    byte[] salt = fromHex(parts[1]);
    byte[] hash = fromHex(parts[2]);

    PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] testHash = skf.generateSecret(spec).getEncoded();

    int diff = hash.length ^ testHash.length;
    for(int i = 0; i < hash.length && i < testHash.length; i++)
    {
      diff |= hash[i] ^ testHash[i];
    }
    return diff == 0;
  }

  private static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
    byte[] bytes = new byte[hex.length() / 2];
    for(int i = 0; i<bytes.length ;i++)
    {
      bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }
    return bytes;
  }
}
