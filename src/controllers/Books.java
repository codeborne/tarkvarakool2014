package controllers;

import model.Book;

import java.util.List;

public class Books extends Controller {

  public List<Book> books;

  @Override
  public void get() {
    books = hibernate.createCriteria(Book.class).list();
  }
}
