package controllers;

import java.util.List;

import static java.util.Arrays.asList;

public class Hello extends Controller {
  public void get() {
  }

  public List<String> getNames() {
    return asList("Foo", "Bar", "Baz");
  }
}
