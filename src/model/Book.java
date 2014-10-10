package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Book {

  @Id
  @GeneratedValue
  private Long id;

  private String title;

  private String author;

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }
}