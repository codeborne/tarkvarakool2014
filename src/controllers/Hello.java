package controllers;

import java.util.List;

import static java.util.Arrays.asList;

public class Hello extends Controller {

    public void get() {
        System.out.println("Anna /hello");
    }

    public void post() {
        System.out.println("Vota hello");
        System.out.println(request.getParameter("name"));
    }

    public String getMegaName() {
        return request.getParameter("name");
    }

    public List<String> getNames() {
    return asList("Foo", "Bar", "Baz");
  }
}
