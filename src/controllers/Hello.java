package controllers;

import java.util.List;

import static java.util.Arrays.asList;

public class Hello extends Controller {
  public String name;

  @Override
  public void post() {
    name = request.getParameter("name");
  }

  public List<String> getNames() {
    return asList("Foo", "Bar", "Baz");
  }
}
