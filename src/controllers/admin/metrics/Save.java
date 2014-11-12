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
  public Double startLevel;
  public String commentOnStartLevel;
  public Double targetLevel;
  public String commentOnTargetLevel;
  public String infoSource;
  public String institutionToReport;
  public Double orderNumber;
  public Set<String> errorsList = new HashSet<>();
  public Long metricId;
  public Goal goal;
  public Boolean isPublic;

  @Override
  @Role("admin")
  public Result post() {
    goal = (Goal) hibernate.get(Goal.class, goalId);
    checkErrors();
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
    if (isPublic == null){
      isPublic = false;
    }
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
      if (metric == null) {
        addSave();
      } else {
        modifySave(metric);
      }
    } else {
      addSave();
    }
  }

  private void modifySave(Metric metric) {
    metric.setName(name);
    metric.setUnit(unit);
    metric.setPublicDescription(publicDescription);
    metric.setPrivateDescription(privateDescription);
    metric.setStartLevel(startLevel);
    metric.setTargetLevel(targetLevel);
    metric.setCommentOnStartLevel(commentOnStartLevel);
    metric.setCommentOnTargetLevel(commentOnTargetLevel);
    metric.setInfoSource(infoSource);
    metric.setInstitutionToReport(institutionToReport);
    metric.setOrderNumber(orderNumber);
    metric.setIsPublic(isPublic);
    hibernate.update(metric);
    hibernate.flush();
  }

  private void addSave() {
    goal = (Goal) hibernate.get(Goal.class, goalId);
    orderNumber = (Double) hibernate.createCriteria(Metric.class)
      .add(Restrictions.eq("goal", goal)).setProjection(Projections.max("orderNumber")).uniqueResult();
    orderNumber = Math.ceil(orderNumber == null ? 0 : orderNumber) + 1;
    hibernate.save(new Metric(goal, name, unit, publicDescription, privateDescription, startLevel, commentOnStartLevel,
      targetLevel, commentOnTargetLevel, infoSource, institutionToReport, orderNumber, isPublic));
    hibernate.flush();
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
  }

  private void checkName() {
    if (errors.containsKey("name") || isBlank(name))
      errorsList.add(messages.get("errorInsertMetric"));
  }

  private void checkPublicDescription() {
    if (errors.containsKey("publicDescription"))
      errorsList.add(messages.get("errorPublicDescription"));
  }

  private void checkPrivateDescription() {
    if (errors.containsKey("privateDescription"))
      errorsList.add(messages.get("errorPrivateDescription"));

  }

  private void checkStartLevel() {
    if (errors.containsKey("startLevel"))
      errorsList.add(messages.get("errorStartLevel"));

  }

  private void checkCommentOnStartLevel() {
    if (errors.containsKey("commentOnStartLevel"))
      errorsList.add(messages.get("errorStartLevelComment"));
  }

  private void checkTargetLevel() {
    if (errors.containsKey("targetLevel"))
      errorsList.add(messages.get("errorTargetLevel"));
  }

  private void checkCommentOnTargetLevel() {
    if (errors.containsKey("commentOnTargetLevel"))
      errorsList.add(messages.get("errorTargetLevelComment"));
  }

  private void checkInfoSource() {
    if (infoSource!=null && !infoSource.equals("") && !infoSource.contains("http://")&&!infoSource.contains("https://")) {
      errorsList.add(messages.get("errorInfoSource"));
    }
    if (errors.containsKey("infoSource")) {
      errorsList.add(messages.get("errorInfoSource"));
  }}

  private void checkInstitutionToReport() {
    if (errors.containsKey("institutionToReport"))
      errorsList.add(messages.get("errorInstitutionReport"));
  }

  private void checkOrderNumber() {
    if (errors.containsKey("orderNumber"))
      errorsList.add(messages.get("errorSequenceNumber"));

  }
}
