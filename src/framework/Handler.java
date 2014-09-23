package framework;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class Handler extends AbstractHandler {

  @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String className = getClassName(target);
    try {
      Object controller = Class.forName(className).newInstance();

    }
    catch (ClassNotFoundException e) {
      return;
    }
    catch (Exception e) {
      System.err.println("Failed to create controller: " + e);
      response.sendError(SC_INTERNAL_SERVER_ERROR);
      return;
    }


    System.out.println(target);
    response.getWriter().print("foo");
    baseRequest.setHandled(true);
  }

  public String getClassName(String path) {
    path = path.substring(1);
    int i = path.lastIndexOf('/');
    String packagePrefix = (i == -1) ? "" : path.substring(0, i).replace('/', '.') + ".";
    path = path.substring(i + 1);
    return "controllers." + packagePrefix + asList(path.split("-")).stream().map(StringUtils::capitalize).collect(joining());
  }
}
