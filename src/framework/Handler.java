package framework;

import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static freemarker.template.TemplateExceptionHandler.RETHROW_HANDLER;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class Handler extends AbstractHandler {

  private final static Logger LOG = LogManager.getLogger();

  private Configuration freemarker = new Configuration();

  public Handler() throws IOException {
    initializeFreemarker();
  }

  @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    long t = -System.currentTimeMillis();
    try {
      String className = getClassName(target);
      Class<?> controllerClass = Class.forName(className);
      Method method = controllerClass.getMethod(baseRequest.getMethod().toLowerCase());
      Object controller = controllerClass.newInstance();
      bindRequest(controller, request);
      method.invoke(controller);

      Template template = freemarker.getTemplate(getTemplateName(target));
      response.setContentType("text/html");
      template.process(controller, new OutputStreamWriter(response.getOutputStream(), "utf-8"));
      baseRequest.setHandled(true);
    }
    catch (ClassNotFoundException ignored) {
      redirectIfPossible(target, response);
    }
    catch (InstantiationException|IllegalAccessException|NoSuchMethodException e) {
      LOG.warn("Failed to create controller: " + e);
      response.sendError(SC_INTERNAL_SERVER_ERROR);
    }
    catch (InvocationTargetException e) {
      handleException(e, response);
    }
    catch (FileNotFoundException e) {
      LOG.warn(e.toString());
      response.sendError(SC_INTERNAL_SERVER_ERROR);
    }
    catch (TemplateException e) {
      LOG.warn("Template failure: " + e);
      response.sendError(SC_INTERNAL_SERVER_ERROR);
    }
    finally {
      t += System.currentTimeMillis();
      LOG.info(baseRequest.getMethod() + " " + target + " " + t + " ms");
    }
  }

  void handleException(InvocationTargetException exception, HttpServletResponse response) throws IOException {
    Throwable cause = exception.getCause();
    if (cause instanceof Redirect) {
      response.sendRedirect(cause.getMessage());
    }
    else {
      LOG.warn("Controller failure: " + cause);
      response.sendError(SC_INTERNAL_SERVER_ERROR);
    }
  }

  void redirectIfPossible(String target, HttpServletResponse response) throws IOException {
    if (target.endsWith("/")) {
      if (!redirectIfExists(target + "home", response))
        redirectIfExists(target.substring(0, target.length() - 1), response);
    }
    else
      redirectIfExists(target + "/home", response);
  }

  private boolean redirectIfExists(String withHomeSuffix, HttpServletResponse response) throws IOException {
    try {
      Class.forName(getClassName(withHomeSuffix));
      response.sendRedirect(withHomeSuffix);
      return true;
    }
    catch (ClassNotFoundException e) {
      return false;
    }
  }

  void bindRequest(Object controller, HttpServletRequest request) {
    try {
      for (Field field : controller.getClass().getFields()) {
        if (!field.getType().equals(HttpServletRequest.class)) continue;
        field.set(controller, request);
        break;
      }
    }
    catch (IllegalAccessException ignored) {
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
    return "controllers." + packagePrefix + asList(path.split("-")).stream().map(StringUtils::capitalize).collect(joining());
  }

  private void initializeFreemarker() throws IOException {
    freemarker.setDirectoryForTemplateLoading(new File("templates"));
    freemarker.setObjectWrapper(new DefaultObjectWrapper());
    freemarker.setDefaultEncoding("UTF-8");
    freemarker.setTemplateExceptionHandler(RETHROW_HANDLER);
    freemarker.setIncompatibleImprovements(new Version(2, 3, 20));
  }
}
