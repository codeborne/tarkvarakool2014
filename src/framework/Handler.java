package framework;

import com.google.common.reflect.ClassPath;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.*;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springsource.loaded.ReloadEventProcessorPlugin;
import org.springsource.loaded.agent.SpringLoadedPreProcessor;

import javax.persistence.Entity;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static freemarker.template.TemplateExceptionHandler.HTML_DEBUG_HANDLER;
import static freemarker.template.TemplateExceptionHandler.RETHROW_HANDLER;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.apache.commons.lang3.text.WordUtils.capitalize;

public class Handler extends AbstractHandler {
  static final String THE_ENCODING = "UTF-8";
  private final static Logger LOG = LogManager.getLogger();

  private Configuration freemarker = new Configuration();
  private Messages messages = new Messages();
  private Binder binder = new Binder("dd.MM.yyyy");
  private SessionFactory hibernateSessionFactory;

  private boolean devMode = isRunningInDebugMode();

  public Handler() throws IOException {
    initializeFreemarker();
    initializeHibernate();
  }

  @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    long t = -System.currentTimeMillis();
    Session hibernate = null;
    try {
      request.setCharacterEncoding(THE_ENCODING);
      bindFrameworkFields(Controller.requestState.get(), request, response);
      hibernate = openHibernateSession(Controller.requestState.get());
      Object controller = createController(target);
      binder.bindRequestParameters(controller, request.getParameterMap());
      invokeController(controller, baseRequest);

      Template template = freemarker.getTemplate(getTemplateName(target));
      response.setContentType("text/html");
      response.setCharacterEncoding(THE_ENCODING);
      template.process(controller, new OutputStreamWriter(response.getOutputStream(), THE_ENCODING));
    }
    catch (ClassNotFoundException|NoSuchMethodException ignored) {
      redirectIfPossible(target, baseRequest, response);
    }
    catch (InstantiationException|IllegalAccessException e) {
      LOG.warn("Failed to create controller: " + e);
      response.sendError(SC_INTERNAL_SERVER_ERROR, devMode ? e.toString() : null);
      baseRequest.setHandled(true);
    }
    catch (InvocationTargetException e) {
      handleException(e, response);
    }
    catch (FileNotFoundException e) {
      LOG.warn(e.toString());
      response.sendError(SC_INTERNAL_SERVER_ERROR, devMode ? e.toString() : null);
    }
    catch (TemplateException e) {
      LOG.warn("Template failure: " + e);
      response.sendError(SC_INTERNAL_SERVER_ERROR, devMode ? e.toString() : null);
    }
    finally {
      closeHibernateSession(hibernate);
      t += System.currentTimeMillis();
      LOG.info(request.getMethod() + " " + target + " " + t + " ms");
    }
  }

  private Session openHibernateSession(Controller controller) {
    return controller.hibernate = hibernateSessionFactory.openSession();
  }

  private void closeHibernateSession(Session hibernate) {
      if (hibernate != null)
        hibernate.close();
  }

  void invokeController(Object controller, Request baseRequest) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method method = controller.getClass().getMethod(baseRequest.getMethod().toLowerCase());
    baseRequest.setHandled(true);
    method.invoke(controller);
  }

  Object createController(String target) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    String className = getClassName(target);
    Class controllerClass = Class.forName(className);
    return controllerClass.newInstance();
  }

  void handleException(InvocationTargetException exception, HttpServletResponse response) throws IOException {
    Throwable cause = exception.getCause();
    if (cause instanceof Redirect) {
      response.sendRedirect(cause.getMessage());
    }
    else {
      LOG.warn("Controller failure: " + cause);
      response.sendError(SC_INTERNAL_SERVER_ERROR, devMode ? cause.toString() : null);
    }
  }

  void redirectIfPossible(String target, Request baseRequest, HttpServletResponse response) throws IOException {
    if (target.endsWith("/")) {
      if (!redirectIfExists(target + "home", baseRequest, response))
        redirectIfExists(target.substring(0, target.length() - 1), baseRequest, response);
    }
    else
      redirectIfExists(target + "/home", baseRequest, response);
  }

  private boolean redirectIfExists(String withHomeSuffix, Request baseRequest, HttpServletResponse response) throws IOException {
    try {
      Class.forName(getClassName(withHomeSuffix));
      response.sendRedirect(withHomeSuffix);
      baseRequest.setHandled(true);
      return true;
    }
    catch (ClassNotFoundException e) {
      return false;
    }
  }

  void bindFrameworkFields(Object controller, HttpServletRequest request, HttpServletResponse response) {
    if (controller instanceof Controller) {
      Controller con = (Controller) controller;
      con.request = request;
      con.response = response;
      con.session = request.getSession();
      con.messages = messages.getResolverFor(request);
    }
  }

  String getTemplateName(String path) {
    return path.substring(1) + ".ftl";
  }

  String getClassName(String path) {
    path = path.substring(1);
    int i = path.lastIndexOf('/');
    String packagePrefix = (i == -1) ? "" : path.substring(0, i).replace('/', '.') + ".";
    path = path.substring(i + 1);
    return "controllers." + packagePrefix + capitalize(path, '-').replace("-", "");
  }

  private boolean isRunningInDebugMode() {
    return ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("jdwp");
  }

  private void initializeFreemarker() throws IOException {
    freemarker.setDefaultEncoding(THE_ENCODING);
    freemarker.setTemplateExceptionHandler(devMode ? HTML_DEBUG_HANDLER : RETHROW_HANDLER);
    freemarker.setIncompatibleImprovements(new Version(2, 3, 20));
    freemarker.setTemplateLoader(new FileTemplateLoader(new File("views")) {
      @Override
      public Reader getReader(Object templateSource, String encoding) throws IOException {
        Reader reader = super.getReader(templateSource, encoding);
        String template = IOUtils.toString(reader);
        reader.close();
        return new StringReader("<#escape value as value?html>" + template + "</#escape>");
      }
    });
    freemarker.addAutoInclude("decorator.ftl");
    freemarker.addAutoInclude("macros.ftl");
    initFreemarkerObjectWarpper();

    if (devMode) {
      SpringLoadedPreProcessor.registerGlobalPlugin(new ReloadEventProcessorPlugin() {
        @Override public void reloadEvent(String className, Class<?> clazz, String id) {
          if (!className.startsWith("controllers.")) return;
          LOG.info(className + " recompiled, reinitializing freemarker");
          initFreemarkerObjectWarpper();
        }

        @Override public boolean shouldRerunStaticInitializer(String className, Class<?> clazz, String id) {
          return false;
        }
      });
    }
  }

  private void initFreemarkerObjectWarpper() {
    DefaultObjectWrapper wrapper = new DefaultObjectWrapper();
    wrapper.setExposeFields(true);
    freemarker.setObjectWrapper(wrapper);
  }

  private void initializeHibernate() throws IOException {
    //noinspection deprecation
    org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
    ClassPath.from(this.getClass().getClassLoader()).getTopLevelClassesRecursive("model").stream()
        .map(ClassPath.ClassInfo::load)
        .filter(modelClass -> modelClass.isAnnotationPresent(Entity.class))
        .forEach(configuration::addAnnotatedClass);
    new SchemaUpdate(configuration).execute(true, true);
    hibernateSessionFactory = configuration.buildSessionFactory();

    Session session = hibernateSessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    Scanner scanner = new Scanner(getClass().getResourceAsStream("/init.sql"));
    List<String> initCommands = getInitSQL(scanner);
    scanner.close();
    for (String command : initCommands) session.createSQLQuery(command).executeUpdate();
    transaction.commit();
    session.close();
  }

  private List<String> getInitSQL(Scanner scanner) {
    List<String> commands = new ArrayList<>();
    String command = "";
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      command += " " + line;
      if (line.endsWith(";")) {
        commands.add(command.trim());
        command = "";
      }
    }
    return commands;
  }
}

