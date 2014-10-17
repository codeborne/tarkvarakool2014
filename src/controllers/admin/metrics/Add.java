package controllers.admin.metrics;

import framework.Controller;
import framework.Result;
import model.Metric;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Add extends Controller {

  public String name;
  public String publicDescription;
  public String privateDescription;
  public Integer startLevel;
  public String commentOnStartLevel;
  public Integer targetLevel;
  public String commentOnTargetLevel;
  public String infoSource;
  public String reportInstitution;
  public Set<String> errorsList = new HashSet<>();

  public java.util.List<Metric> metrics = new ArrayList<>();


  @Override
  public Result post() {
   checkName();
    if (errorsList.isEmpty()) {
      try {
        name.trim();
        hibernate.save(new Metric(name, publicDescription, privateDescription, startLevel, commentOnStartLevel,
          targetLevel, commentOnTargetLevel, infoSource, reportInstitution));

      } catch (Exception e) {
        errorsList.add("Tekkis viga.");
      }
    }
    metrics = hibernate.createCriteria(Metric.class).list();
    return render();
}
  private void checkName() {
    if (errors.containsKey("name") || isBlank(name))
      errorsList.add("Sisestage mõõdik.");
  }
}
