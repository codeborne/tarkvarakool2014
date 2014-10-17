package framework;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Controller extends RequestState {
  public Map<String, Throwable> errors = new LinkedHashMap<>();

  public Result get() {
    return render();
  }

  public Result post() {
    return render();
  }

  public Result put() {
    return render();
  }

  public Result delete() {
    return render();
  }

  public Result options() {
    return render();
  }

  protected Redirect redirect(Class<? extends Controller> target) {
    return new Redirect(target);
  }

  protected Render render() {
    return new Render(this);
  }

  protected Render render(String template) {
    return new Render(this, template);
  }

  @SuppressWarnings("unchecked")
  protected  <T> T fromSession(Class<T> clazz) {
    T value = (T) session.getAttribute(clazz.getName());
    if (value == null) {
      try {
        value = clazz.newInstance();
        session.setAttribute(clazz.getName(), value);
      }
      catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return value;
  }
}
