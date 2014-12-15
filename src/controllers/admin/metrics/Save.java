package controllers.admin.metrics;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Save extends UserAwareController {

  public Long goalId;
  public String name;
  public String unit;
  public String publicDescription;
  public String privateDescription;
  public String startLevel;
  public String commentOnStartLevel;
  public String targetLevel;
  public String commentOnTargetLevel;
  public String infoSource;
  public String institutionToReport;
  public Double orderNumber;
  public Set<String> errorsList = new HashSet<>();

  public Long metricId;
  public Goal goal;
  public Boolean isPublic;
  public Boolean isStatusUpdateOnly;
  public Boolean isDecreasing;

  public Double startLevelAsNumber;
  public Double targetLevelAsNumber;

  @Override
  @Role("admin")
  public Result post() {
    if(isStatusUpdateOnly==null){
      isStatusUpdateOnly=false;
    }
    goal = (Goal) hibernate.get(Goal.class, goalId);
    if(!isStatusUpdateOnly) {
      checkErrors();

      convertLevelsToNumbers();
    }
    if (errorsList.isEmpty()) {
      try {
        trimAndPrepareAllInput();
        save();
      } catch (ConstraintViolationException e) {
        hibernate.getTransaction().rollback();
        errorsList.add(messages.get("errorInsertedMetric"));

      }
    }
    return render("admin/metrics/errors");
  }

  private void trimAndPrepareAllInput() {
    if (isPublic == null) {
      isPublic = false;
    }
    if(isDecreasing==null){
      isDecreasing = false;
    }
    if (name != null)
    name = name.trim();
    if (unit != null)
      unit = unit.trim();
    if (publicDescription != null)
      publicDescription = publicDescription.trim();
    if (privateDescription != null)
      privateDescription = privateDescription.trim();
    if (commentOnStartLevel != null)
      commentOnStartLevel = commentOnStartLevel.trim();
    if (commentOnTargetLevel != null)
      commentOnTargetLevel = commentOnTargetLevel.trim();
    if (infoSource != null)
      infoSource = infoSource.trim();
    if (institutionToReport != null)
      institutionToReport = institutionToReport.trim();
  }



  private void save() {
    if (metricId != null) {
      Metric metric = (Metric) hibernate.get(Metric.class, metricId);
      if (metric == null && !isStatusUpdateOnly) {
        addSave();
      } else {
        modifySave(metric);
      }
    } else if (!isStatusUpdateOnly) {
      addSave();
    }
  }

  private void modifySave(Metric metric) {
    if(!isStatusUpdateOnly) {
    metric.setName(name);
    metric.setUnit(unit);
    metric.setPublicDescription(publicDescription);
    metric.setPrivateDescription(privateDescription);
    metric.setStartLevel(startLevelAsNumber);
    metric.setTargetLevel(targetLevelAsNumber);
    metric.setCommentOnStartLevel(commentOnStartLevel);
    metric.setCommentOnTargetLevel(commentOnTargetLevel);
    metric.setInfoSource(infoSource);
    metric.setInstitutionToReport(institutionToReport);
    metric.setOrderNumber(orderNumber);
    metric.setIsDecreasing(isDecreasing);
    }
    else {
      metric.setIsPublic(isPublic);
    }
    hibernate.update(metric);
    hibernate.flush();
  }

  private void addSave() {
    goal = (Goal) hibernate.get(Goal.class, goalId);
    orderNumber = (Double) hibernate.createCriteria(Metric.class)
      .add(Restrictions.eq("goal", goal)).setProjection(Projections.max("orderNumber")).uniqueResult();
    orderNumber = Math.ceil(orderNumber == null ? 0 : orderNumber) + 1;
    Metric metric = new Metric(goal, name, unit, publicDescription, privateDescription, startLevelAsNumber, commentOnStartLevel,
      targetLevelAsNumber, commentOnTargetLevel, infoSource, institutionToReport, orderNumber, isPublic);
    metric.setIsDecreasing(isDecreasing);
    hibernate.save(metric);
    hibernate.flush();
  }

  private void convertLevelsToNumbers(){

    if (!"".equals(startLevel) && startLevel != null) {
      startLevel=startLevel.replace(',','.');
      try {
        startLevelAsNumber = Double.parseDouble(startLevel);
      } catch (NumberFormatException e) {
        errorsList.add(messages.get("errorStartLevel"));
      }
    }
    if (!"".equals(targetLevel) && targetLevel != null) {
      targetLevel=targetLevel.replace(',','.');
      try {
        targetLevelAsNumber = Double.parseDouble(targetLevel);
      } catch (NumberFormatException e) {
        errorsList.add(messages.get("errorTargetLevel"));
      }
    }
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
    checkOrderNumber();
    checkIsPublic();
    checkIsDecreasing();
  }

  private void checkName() {
    if (errors.containsKey("name") || isBlank(name))
      errorsList.add(messages.get("errorInsertMetric"));
  }

  private void checkPublicDescription() {
    if (errors.containsKey("publicDescription"))
      errorsList.add(messages.get("error"));
  }

  private void checkPrivateDescription() {
    if (errors.containsKey("privateDescription"))
      errorsList.add(messages.get("error"));
  }

  private void checkStartLevel() {
     if (errors.containsKey("startLevel"))
      errorsList.add(messages.get("error"));

  }

  private void checkCommentOnStartLevel() {
    if (errors.containsKey("commentOnStartLevel"))
      errorsList.add(messages.get("error"));
  }

  private void checkTargetLevel() {
    if (errors.containsKey("targetLevel"))
      errorsList.add(messages.get("error"));
  }

  private void checkCommentOnTargetLevel() {
    if (errors.containsKey("commentOnTargetLevel"))
      errorsList.add(messages.get("error"));
  }

  private void checkInfoSource() {
    if (errors.containsKey("infoSource")) {
      errorsList.add(messages.get("error"));
    }
  }

  private void checkInstitutionToReport() {
    if (errors.containsKey("institutionToReport"))
      errorsList.add(messages.get("error"));
  }

  private void checkOrderNumber() {
    if (errors.containsKey("orderNumber"))
      errorsList.add(messages.get("error"));
  }

  private void checkIsPublic() {
    if (errors.containsKey("isPublic"))
      errorsList.add(messages.get("error"));
  }

  private void checkIsDecreasing() {
    if (errors.containsKey("setIsDecreasing"))
      errorsList.add(messages.get("error"));
  }
}
