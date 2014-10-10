package controllers;

import framework.Controller;

public class Localize extends Controller {
  public String hello;

  @Override
  public void get() {
    hello = messages.get("hello");
  }
}
