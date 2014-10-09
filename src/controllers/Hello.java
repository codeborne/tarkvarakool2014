package controllers;

import framework.Controller;

import java.util.List;

import static java.util.Arrays.asList;

public class Hello extends Controller {
  public String name;

  public List<String> getNames() {
    return asList("Foo", "Bar", "Baz");
  }
}
