package controllers.admin.metrics;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Add extends UserAwareController {

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


  @Override @Role("admin")
  public Result get()  {

    goal = (Goal) hibernate.get(Goal.class, goalId);
    return render();
  }

  @Override @Role("admin")
  public Result post() {
    goal = (Goal) hibernate.get(Goal.class, goalId);
    checkErrors();
    if (errorsList.isEmpty()) {
      try {
        name.trim();
        hibernate.save(new Metric(goal, name, publicDescription, privateDescription, startLevel, commentOnStartLevel,
          targetLevel, commentOnTargetLevel, infoSource, institutionToReport));
        return redirect(Metrics.class).withParam("goalId", goalId);
      } catch (Exception e) {
        errorsList.add("Tekkis viga.");
      }
    }

    return  render();
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
