package framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static framework.Handler.THE_ENCODING;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.splitByCharacterTypeCamelCase;

public class Redirect extends Result {
  private String path;

  public Redirect(Class<? extends Controller> target) {
    this.path = createPath(target);
  }

  public Redirect(String path) {
    this.path = path;
  }

  public Redirect withParam(String name, String value) {
    path += (path.contains("?") ? "&" : "?") + name + "=" + urlEncode(value);
    return this;
  }

  public Redirect withParam(String name, Long value) {
    return withParam(name, String.valueOf(value));
  }

  @Override
  void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.sendRedirect(path);
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

  public static String createPath(Class<? extends Controller> targetClass) {
    String path = targetClass.getPackage().getName().replace("controllers", "").replace('.', '/');
    return path + "/" + join(splitByCharacterTypeCamelCase(targetClass.getSimpleName()), '-').toLowerCase();
  }
}
