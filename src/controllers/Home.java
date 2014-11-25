package controllers;

import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.hibernate.criterion.Order.asc;

public class Home extends UserAwareController {

  public List<Goal> goals = new ArrayList<>();
  public List<List<List<String>>> infoSourceContentList = new ArrayList<>();



  @Override
  @Role("anonymous")
  public Result get() {
    goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();
    for(Goal goal:goals) {
      Set<Metric> metrics = goal.getMetrics();
      List<List<String>> infoSourceListAtMetricLevel = new ArrayList<>();
      for (Metric metric : metrics) {
        infoSourceListAtMetricLevel.add(checkInfoSourceContent(metric));
      }
      infoSourceContentList.add(infoSourceListAtMetricLevel);
    }
    return render();
  }

  private List<String> checkInfoSourceContent(Metric metric) {
    List<String> infoSourceList = new ArrayList<>();
    String infoSource = "";
    if("en".equals(session.getAttribute("locale"))&&!isBlank(metric.getEngInfoSource())) {
      infoSource = metric.getEngInfoSource();
    }
    else{
      infoSource = metric.getInfoSource();
    }
    infoSource = infoSource.replace("\n","*** ");
    StringTokenizer stringTokenizer = new StringTokenizer(infoSource);
    while (stringTokenizer.hasMoreElements()) {
      infoSourceList.add(stringTokenizer.nextElement().toString());
    }
    for(String info : infoSourceList){
      if(info.contains("***")){
        String updatedInfo= info.replace("***","\n");
        int indexOfInfo = infoSourceList.indexOf(info);
        infoSourceList.set(indexOfInfo,updatedInfo);
      }
    }
    return infoSourceList;
  }
}




