package model;

import javax.persistence.*;

@Entity
public class Metric {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
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

  private Metric(){
  }

  public Metric(Goal goal, String name, String publicDescription, String privateDescription, Integer startLevel,
                String commentOnStartLevel, Integer targetLevel, String commentOnTargetLevel,
                String infoSource, String institutionToReport) {
    this.goal = goal;
    this.name = name;
    this.publicDescription = publicDescription;
    this.privateDescription = privateDescription;
    this.startLevel = startLevel;
    this.commentOnStartLevel = commentOnStartLevel;
    this.targetLevel = targetLevel;
    this.commentOnTargetLevel = commentOnTargetLevel;
    this.infoSource = infoSource;
    this.institutionToReport = institutionToReport;
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
}
