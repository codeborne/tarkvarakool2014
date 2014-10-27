package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Metric {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne
  private Goal goal;

  private String publicDescription;
  private String privateDescription;
  private Integer startLevel;
  private String commentOnStartLevel;
  private Integer targetLevel;
  private String commentOnTargetLevel;
  private String infoSource;
  private String institutionToReport;

  @ElementCollection
  @JoinTable(name = "MetricValue", joinColumns = @JoinColumn(name = "metric_id"))
  @MapKeyColumn(name="year")
  @Column(name="value", precision = 38, scale = 6)
  private Map<Integer, BigDecimal> values = new HashMap<>();

  @ElementCollection
  @JoinTable(name = "MetricForecast", joinColumns = @JoinColumn(name = "metric_id"))
  @MapKeyColumn(name = "year")
  @Column(name = "forecast", precision = 38, scale = 6)
  private Map<Integer, BigDecimal> forecasts = new HashMap<>();
  private String unit;

  @Column(nullable = false)
  private Double orderNumber;

  private Metric() {
  }

  public Metric(Goal goal, String name, String unit, String publicDescription, String privateDescription, Integer startLevel,
                String commentOnStartLevel, Integer targetLevel, String commentOnTargetLevel,
                String infoSource, String institutionToReport, Double orderNumber) {
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

  public Integer getStartLevel() {
    return startLevel;
  }

  public String getCommentOnStartLevel() {
    return commentOnStartLevel;
  }

  public Integer getTargetLevel() {
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

  public void setStartLevel(Integer startLevel) {
    this.startLevel = startLevel;
  }

  public void setCommentOnStartLevel(String commentOnStartLevel) {
    this.commentOnStartLevel = commentOnStartLevel;
  }

  public void setTargetLevel(Integer targetLevel) {
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
}
