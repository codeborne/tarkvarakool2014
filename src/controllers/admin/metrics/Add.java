package controllers.admin.metrics;

import framework.Controller;
import framework.Result;
import model.Goal;
import model.Metric;

import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Add extends Controller {

  public Long goalId;
  public String name;
  public String publicDescription;
  public String privateDescription;
  public Integer startLevel = 0;
  public String commentOnStartLevel;
  public Integer targetLevel = 0;
  public String commentOnTargetLevel;
  public String infoSource;
  public String institutionToReport;
  public Set<String> errorsList = new HashSet<>();

  public Goal goal;


  @Override
  public Result get() throws Exception {
    goal = (Goal) hibernate.get(Goal.class, goalId);
    return render();
  }

  @Override
  public Result post() {
    goal = (Goal) hibernate.get(Goal.class, goalId);
    checkErrors();
    if (errorsList.isEmpty()) {
      try {
        name.trim();
        hibernate.save(new Metric(goal, name, publicDescription, privateDescription, startLevel, commentOnStartLevel,
          targetLevel, commentOnTargetLevel, infoSource, institutionToReport));

      } catch (Exception e) {
        errorsList.add("Tekkis viga.");
      }
    }

    return redirect(Metrics.class);
  }

  private void checkErrors() {
    checkName();
    checkPublicDescription();
    checkPrivateDescription();
    checkStartLevel();
    checkCommentOnStartLevel();
    checkTargetLevel();
    checkCommentOnTargetLevel();
    checkInfoSource();
    checkInstitutionToReport();
  }

  private void checkName() {
    if (errors.containsKey("name") || isBlank(name))
      errorsList.add("Sisestage mõõdik.");
  }

  private void checkPublicDescription() {
    if (errors.containsKey("publicDescription"))
      errorsList.add("Viga avalikus kirjelduses");
  }

  private void checkPrivateDescription() {
    if (errors.containsKey("privateDescription"))
      errorsList.add("Viga mitteavalikus kirjelduses");

  }

  private void checkStartLevel() {
    if (errors.containsKey("startLevel"))
      errorsList.add("Algtase peab olema number");

  }

  private void checkCommentOnStartLevel() {
    if (errors.containsKey("commentOnStartLevel"))
      errorsList.add("Viga algtaseme kommentaaris");
  }

  private void checkTargetLevel() {
    if (errors.containsKey("targetLevel"))
      errorsList.add("Sihttase peab olema number");
  }

  private void checkCommentOnTargetLevel() {
    if (errors.containsKey("commentOnTargetLevel"))
      errorsList.add("Viga sihttaseme kommentaaris");
  }

  private void checkInfoSource() {
    if (errors.containsKey("infoSource"))
      errorsList.add("Viga infoallika sisestamisel");
  }

  private void checkInstitutionToReport() {
    if (errors.containsKey(institutionToReport))
      errorsList.add("Viga raporteeritava asutuse sisestamisel");
  }

}
