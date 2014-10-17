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
  private String template;

  public Render(Object model) {
    this.model = model;
  }

  public Render(Object model, String template) {
    this(model);
    this.template = template;
  }

  @Override
  void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Template template = freemarker.getTemplate(getTemplateName(request.getPathInfo()));
    response.setContentType("text/html");
    response.setCharacterEncoding(THE_ENCODING);
    template.process(model, new OutputStreamWriter(response.getOutputStream(), THE_ENCODING));
  }

  String getTemplateName(String path) {
    return (template != null ? template : path.substring(1)) + ".ftl";
  }
}
