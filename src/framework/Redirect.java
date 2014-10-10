package framework;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static framework.Handler.THE_ENCODING;

public class Redirect extends RuntimeException {
  private String path;

  public Redirect(String path) {
    super(path);
    this.path = path;
  }

  /** @deprecated use withParam() instead */
  public Redirect(String path, String paramName, String paramValue) {
    this(path);
    withParam(paramName, paramValue);
  }

  public Redirect withParam(String name, String value) {
    path += (path.contains("?") ? "&" : "?") + name + "=" + urlEncode(value);
    return this;
  }

  public String getPath() {
    return path;
  }

  private static String urlEncode(String paramValue) {
    try {
      return URLEncoder.encode(paramValue, THE_ENCODING);
    }
    catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
