package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Entity
public class Metric {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne
  private Goal goal;

  private String unit;
  private String publicDescription;
  private String privateDescription;
  private Double startLevel;
  private String commentOnStartLevel;
  private Double targetLevel;
  private String commentOnTargetLevel;
  private String infoSource;
  private String institutionToReport;
  private Boolean isPublic = false;
  @Column(nullable = false)
  private Double orderNumber;


  private String engName;
  private String engUnit;
  private String engPublicDescription;
  private String engInfoSource;

  @ElementCollection
  @JoinTable(name = "MetricValue", joinColumns = @JoinColumn(name = "metric_id"))
  @MapKeyColumn(name="year")
  @Column(name="value", precision = 38, scale = 1)
  private Map<Integer, BigDecimal> values = new HashMap<>();

  @ElementCollection
  @JoinTable(name = "MetricForecast", joinColumns = @JoinColumn(name = "metric_id"))
  @MapKeyColumn(name = "year")
  @Column(name = "comparableValue", precision = 38, scale = 1)
  private Map<Integer, BigDecimal> forecasts = new HashMap<>();

  Metric() {
  }

  public Metric(Goal goal, String name, String unit, String publicDescription, String privateDescription, Double startLevel,
                String commentOnStartLevel, Double targetLevel, String commentOnTargetLevel,
                String infoSource, String institutionToReport, Double orderNumber, Boolean isPublic) {
    this.goal = goal;
    this.name = name;
    this.unit = unit;
    this.publicDescription = publicDescription;
    this.privateDescription = privateDescription;
    this.startLevel = startLevel;
    this.commentOnStartLevel = commentOnStartLevel;
    this.targetLevel = targetLevel;
    this.commentOnTargetLevel = commentOnTargetLevel;
    this.infoSource = infoSource;
    this.institutionToReport = institutionToReport;
    this.orderNumber = orderNumber;
    this.isPublic = isPublic;
  }

  public Goal getGoal() {
    return goal;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPublicDescription() {
    return publicDescription;
  }

  public String getPrivateDescription() {
    return privateDescription;
  }

  public Double getStartLevel() {
    return startLevel;
  }

  public String getCommentOnStartLevel() {
    return commentOnStartLevel;
  }

  public Double getTargetLevel() {
    return targetLevel;
  }

  public String getCommentOnTargetLevel() {
    return commentOnTargetLevel;
  }

  public String getInfoSource() {
    return infoSource;
  }

  public String getInstitutionToReport() {
    return institutionToReport;
  }

  public Boolean getIsPublic() {
    return isPublic;
  }

  public Map<Integer, BigDecimal> getValues() {
    return values;
  }

  public Map<Integer, BigDecimal> getForecasts() {
    return forecasts;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPublicDescription(String publicDescription) {
    this.publicDescription = publicDescription;
  }

  public void setPrivateDescription(String privateDescription) {
    this.privateDescription = privateDescription;
  }

  public void setStartLevel(Double startLevel) {
    this.startLevel = startLevel;
  }

  public void setCommentOnStartLevel(String commentOnStartLevel) {
    this.commentOnStartLevel = commentOnStartLevel;
  }

  public void setTargetLevel(Double targetLevel) {
    this.targetLevel = targetLevel;
  }

  public void setCommentOnTargetLevel(String commentOnTargetLevel) {
    this.commentOnTargetLevel = commentOnTargetLevel;
  }

  public void setInfoSource(String infoSource) {
    this.infoSource = infoSource;
  }

  public void setInstitutionToReport(String institutionToReport) {
    this.institutionToReport = institutionToReport;
  }

  public void setGoal(Goal goal) {
    this.goal = goal;
  }

  public void setValues(Map<Integer, BigDecimal> values) {
    this.values = values;
  }

  public void setForecasts(Map<Integer, BigDecimal> forecasts) {
    this.forecasts = forecasts;
  }

  public void setIsPublic(Boolean isPublic) {
    this.isPublic = isPublic;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Double getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(Double orderNumber) {
    this.orderNumber = orderNumber;
  }


  public String getEngName() {
    return engName;
  }

  public void setEngName(String engName) {
    this.engName = engName;
  }

  public String getEngUnit() {
    return engUnit;
  }

  public void setEngUnit(String engUnit) {
    this.engUnit = engUnit;
  }

  public String getEngPublicDescription() {
    return engPublicDescription;
  }

  public void setEngPublicDescription(String engPublicDescription) {
    this.engPublicDescription = engPublicDescription;
  }
  public String getEngInfoSource() {
    return engInfoSource;
  }

  public void setEngInfoSource(String engInfoSource) {
    this.engInfoSource = engInfoSource;
  }

  public boolean hasValueForYear(int year){
    return getValues().get(year) != null;
  }

  public boolean levelsAreValid() {
    if (startLevel == null || targetLevel == null) return false;
    if (startLevel.equals(targetLevel)) return false;
    return true;
  }

  public List<String> getInfoSourceAsListOfWordsAndLinks(String language){
      List<String> infoSourceList = new ArrayList<>();
      String infoSource = getInfoSourceDependingOnLanguage(language);

      infoSource = infoSource.replace("\n","*thisIsALineBreak*");
      StringTokenizer stringTokenizer = new StringTokenizer(infoSource);
      while (stringTokenizer.hasMoreElements()) {
        String nextString = stringTokenizer.nextElement().toString();
        if(nextString.contains("*thisIsALineBreak*")){
          nextString = nextString.replace("*thisIsALineBreak*","\n");
        }
        infoSourceList.add(nextString);
      }
      return infoSourceList;
  }

  protected String getInfoSourceDependingOnLanguage(String language){
  if("en".equals(language)&&!isBlank(this.getEngInfoSource())) {
    return this.getEngInfoSource();
  }
  else{
    return this.getInfoSource();
  }
  }
}
