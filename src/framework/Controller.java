package framework;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class Controller extends RequestState {
  public Map<String, Throwable> errors = new LinkedHashMap<>();

  public Result get() throws Exception {
    return render();
  }

  public Result post() throws Exception {
    return render();
  }

  public Result put() throws Exception {
    return render();
  }

  public Result delete() throws Exception {
    return render();
  }

  public Result options() throws Exception {
    return render();
  }

  protected Redirect redirect(Class<? extends Controller> target) {
    return new Redirect(target);
  }

  protected Render render() {
    return new Render(this);
  }

  protected Render json() {
    return new Json(this);
  }

  protected Render render(String template) {
    return new Render(this, template);
  }

  protected Result attachment(InputStream stream, String contentType, String filename) {
    return new Stream(stream, contentType, filename);
  }

  protected Result attachment(String text, Charset charset, String contentType, String filename) {
    return attachment(new ByteArrayInputStream(text.getBytes(charset)), contentType, filename);
  }

  protected Result inline(InputStream stream, String contentType) {
    return new Stream(stream, contentType);
  }

  protected Result inline(String text, Charset charset, String contentType) {
    return inline(new ByteArrayInputStream(text.getBytes(charset)), contentType);
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

  protected Set<String> getRoles() {
    throw new UnsupportedOperationException("This controller has not implemented getRole() method");
  }
}
