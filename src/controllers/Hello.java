package controllers;

import java.util.List;

import static java.util.Arrays.asList;

public class Hello {

  public void get() {
  }

  public List<String> getNames() {
    return asList("<b>Foo</b>", "Bar", "Baz");
  }
}
