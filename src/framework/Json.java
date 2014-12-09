package framework;

public class Json extends Render {
  public Json(Object model) {
    super(model);
  }

  public Json(Object model, String template) {
    super(model, template);
  }

  @Override
  String getTemplateName(String path) {
    return "json.ftl";
  }
}
