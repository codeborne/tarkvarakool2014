package controllers.admin;

import controllers.UserAwareController;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import static org.hibernate.criterion.Order.asc;

public class AllData extends UserAwareController {

  @Override @Role("admin")
  public Result get() throws Exception {
    StringBuilder data = new StringBuilder();
    data.append("Nimetus,Algtase,2014,2015,2016,2017,2018,2019,2020,Sihttase,Ühik,Kirjeldus,Allikas,Kuhu\r\n");
    List<Goal> goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();
    for (Goal goal:goals){
      data.append(goal.getName());
      data.append(",");
      data.append(goal.getComment()==null?"":goal.getComment());
      data.append(",");
      data.append(goal.getBudget());
      data.append("\r\n");
      Set<Metric> metrics = goal.getMetrics();
      for (Metric metric:metrics){
        data.append(metric.getName());
        data.append(",");
        data.append(metric.getStartLevel()==null?"":metric.getStartLevel());
        data.append(",");
        for (int year = MINIMUM_YEAR; year <= MAXIMUM_YEAR; year++){
          data.append(metric.getValues().get(year)==null?"":metric.getValues().get(year));
          data.append(",");
        }
        data.append(metric.getTargetLevel()==null?"":metric.getTargetLevel());
        data.append(",");
        data.append(metric.getUnit()==null?"":metric.getUnit());
        data.append(",");
        data.append(metric.getPublicDescription()==null?"":metric.getPublicDescription());
        data.append(",");
        data.append(metric.getInfoSource()==null?"":metric.getInfoSource());
        data.append(",");
        data.append(metric.getInstitutionToReport()==null?"":metric.getInstitutionToReport());
        data.append("\r\n");
        data.append("Prognoos,");
        data.append(metric.getCommentOnStartLevel()==null?"":metric.getCommentOnStartLevel());
        data.append(",");
        for (int year = MINIMUM_YEAR; year <= MAXIMUM_YEAR; year++){
          data.append(metric.getForecasts().get(year)==null?"":metric.getForecasts().get(year));
          data.append(",");
        }
        data.append(metric.getCommentOnTargetLevel()==null?"":metric.getCommentOnTargetLevel());
        data.append(", ,");
        data.append(metric.getPrivateDescription()==null?"":metric.getPrivateDescription());
        data.append("\r\n");
      }
    }
    return attachment(data.toString(), Charset.forName("utf8"), "text/csv", "andmed.csv");
  }
}
