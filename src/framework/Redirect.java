package framework;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Redirect extends RuntimeException {
  public Redirect(String path) {
    super(path);
  }

  public Redirect(String path, String paramName, String paramValue) {
    this(path + "?" + paramName + "=" + urlEncode(paramValue));
  }

  public String getPath() {
    return getMessage();
  }

  private static String urlEncode(String paramValue) {
    try {
      return URLEncoder.encode(paramValue, Handler.THE_ENCODING);
    }
    catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
