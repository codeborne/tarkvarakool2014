package controllers.admin.metrics;

import framework.Controller;
import framework.Result;

import java.util.HashSet;
import java.util.Set;

public class Add extends Controller {

  private String name;
  private String publicDescription;
  private String privateDescription;
  private Integer startLevel;
  private String commentOnStartLevel;
  private Integer targetLevel;
  private String commentOnTargetLevel;
  private String infoSource;
  private String reportInstitution;
  public Set<String> errorsList = new HashSet<>();


  @Override
  public Result post() {


    return null;

}
}
