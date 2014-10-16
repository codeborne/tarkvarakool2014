package framework;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;

import static framework.Handler.THE_ENCODING;

public class Render extends Result {

  static Configuration freemarker;
  private Object model;

  public Render(Object model) {
    this.model = model;
  }

  @Override
  void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Template template = freemarker.getTemplate(getTemplateName(request.getPathInfo()));
    response.setContentType("text/html");
    response.setCharacterEncoding(THE_ENCODING);
    template.process(model, new OutputStreamWriter(response.getOutputStream(), THE_ENCODING));
  }

  String getTemplateName(String path) {
    return path.substring(1) + ".ftl";
  }
}
