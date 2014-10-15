import org.hibernate.Session;

import java.util.List;

public class Example {

  String name;
  Session hibernate;
  List<Integer> values;

  public int compute(int a, int b) {
    return a * b;
  }

  public String getGreeting(String message) {
    return message + " " + name;
  }

  public void addName(List<String> list) {
    list.add(name);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void storeValue(Integer value) {
    values.add(value);
  }

  public void storeData(String data) {
    hibernate.save(data);
  }
}
