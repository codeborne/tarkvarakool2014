package model;

import controllers.admin.Login;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Entity
public class User {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  public User() {
  }

  public User(String login, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
    this.username = login;
    setPassword(password);
  }

  public Long getId() {
    return id;
  }

  public String getLogin() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setLogin(String login) {
    this.username = login;
  }

  public void setPassword(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
    this.password = Login.generateStrongPasswordHash(password);
  }

}
