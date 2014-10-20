package model;

import static controllers.admin.goals.Password.*;

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

  public User(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
    setUsername(username);
    setPassword(password);
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
    this.password = generateStrongPasswordHash(password);
  }

}
