package framework;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Instant;
import java.util.*;

import static java.util.Collections.unmodifiableMap;

public class Messages  {
  private final String DEFAULT = "";
  private final File confDir = new File("conf");
  public Map<String, ResourceBundle> bundles;
  boolean devMode;
  Instant lastReloaded;

  public Messages(boolean devMode) {
    this.devMode = devMode;
    reloadBundles();
  }

  private synchronized void reloadBundles() {
    Map<String, ResourceBundle> bundles = new HashMap<>();
    for (File file : confDir.listFiles(f -> f.getName().startsWith("messages"))) {
      String locale = file.getName().replaceAll("messages_?(\\w*)\\.properties", "$1");
      bundles.put(locale, loadBundle(file));
    }
    this.bundles = unmodifiableMap(bundles);
    lastReloaded = Instant.now();
  }

  private ResourceBundle loadBundle(File file) {
    try (Reader reader = new FileReader(file)) {
      return new PropertyResourceBundle(reader);
    }
    catch (IOException e) {
      throw new RuntimeException("Cannot load resource bundle from " + file, e);
    }
  }

  private Instant lastModified() {
    return Instant.ofEpochMilli(confDir.lastModified());
  }

  public String get(String locale, String key) {
    try {
      ResourceBundle bundle = bundles.get(locale);
      if (bundle == null) bundle = bundles.get(DEFAULT);
      return bundle.getString(key);
    }
    catch (MissingResourceException e) {
      return locale.equals(DEFAULT) ? null : get(DEFAULT, key);
    }
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

    Resolver(String locale) {
      this.locale = locale;

      if (devMode && lastReloaded.isBefore(lastModified()))
        reloadBundles();
    }

    public String get(String key) {
      return Messages.this.get(locale, key);
    }
  }
}
