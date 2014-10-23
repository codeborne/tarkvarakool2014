package framework;

import freemarker.cache.FileTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Version;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springsource.loaded.ReloadEventProcessorPlugin;
import org.springsource.loaded.agent.SpringLoadedPreProcessor;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static framework.Handler.THE_ENCODING;
import static freemarker.template.TemplateExceptionHandler.HTML_DEBUG_HANDLER;
import static freemarker.template.TemplateExceptionHandler.RETHROW_HANDLER;

public class FreemarkerHelper {

  private final static Logger LOG = LogManager.getLogger();

  public static Configuration initializeFreemarker(boolean devMode) throws IOException {
    Configuration freemarker = new Configuration();
    freemarker.setDefaultEncoding(THE_ENCODING);
    freemarker.setTemplateExceptionHandler(devMode ? HTML_DEBUG_HANDLER : RETHROW_HANDLER);
    freemarker.setIncompatibleImprovements(new Version(2, 3, 20));
    freemarker.setTemplateLoader(new EscapingTemplateLoader());
    freemarker.addAutoInclude("decorator.ftl");
    freemarker.addAutoInclude("macros.ftl");
    initFreemarkerObjectWrapper(freemarker);

    if (devMode)
      SpringLoadedPreProcessor.registerGlobalPlugin(new FreemarkerObjectWrapperReloadingPlugin(freemarker));
    return freemarker;
  }

  private static void initFreemarkerObjectWrapper(Configuration freemarker) {
//    DefaultObjectWrapper wrapper = new DefaultObjectWrapper();
    BeansWrapper wrapper = new BeansWrapper();
    wrapper.setExposeFields(true);
    freemarker.setObjectWrapper(wrapper);
  }

  private static class EscapingTemplateLoader extends FileTemplateLoader {
    public EscapingTemplateLoader() throws IOException {
      super(new File("views"));
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
      Reader reader = super.getReader(templateSource, encoding);
      String template = IOUtils.toString(reader);
      reader.close();
      return new StringReader("<#escape value as value?html>" + template + "</#escape>");
    }
  }

  private static class FreemarkerObjectWrapperReloadingPlugin implements ReloadEventProcessorPlugin {
    private final Configuration freemarker;

    public FreemarkerObjectWrapperReloadingPlugin(Configuration freemarker) {
      this.freemarker = freemarker;
    }

    @Override
    public void reloadEvent(String className, Class<?> clazz, String id) {
      if (!className.startsWith("controllers.")) return;
      LOG.info(className + " recompiled, reinitializing freemarker");
      initFreemarkerObjectWrapper(freemarker);
    }

    @Override
    public boolean shouldRerunStaticInitializer(String className, Class<?> clazz, String id) {
      return false;
    }
  }
}
