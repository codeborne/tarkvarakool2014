package framework;

public class Redirect extends RuntimeException {
  public Redirect(String path) {
    super(path);
  }
}
