package framework;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static java.util.Collections.unmodifiableMap;

public class Messages  {
  private final String DEFAULT = "";
  public Map<String, ResourceBundle> bundles = new HashMap<>();

  public Messages() {
    this.bundles.put(DEFAULT, ResourceBundle.getBundle("messages", Locale.ENGLISH));
    this.bundles.put("et", ResourceBundle.getBundle("messages", new Locale("et")));
    this.bundles = unmodifiableMap(bundles);
  }

  public String get(String locale, String key) {
    ResourceBundle localizedBundle = bundles.get(locale);
    String value = localizedBundle != null ? localizedBundle.getString(key) : null;
    if (value == null) {
      value = bundles.get(DEFAULT).getString(key);
    }
    return value;
  }

  public Resolver getResolverFor(String locale) {
    return new Resolver(locale);
  }

  public Resolver getResolverFor(HttpServletRequest request) {
    String locale = (String) request.getSession().getAttribute("locale");
    if (locale == null) locale = DEFAULT;
    return new Resolver(locale);
  }

  public class Resolver {
    private String locale;

    public Resolver(String locale) {
      this.locale = locale;
    }

    public String get(String key) {
      return Messages.this.get(locale, key);
    }
  }
}
