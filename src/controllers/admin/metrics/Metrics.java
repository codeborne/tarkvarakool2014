package controllers.admin.metrics;

import framework.Controller;
import framework.Result;
import model.Metric;

import java.util.ArrayList;
import java.util.List;

public class Metrics extends Controller {
  public List<Metric> metrics = new ArrayList<>();
  public Long goalId;

 @Override
  public Result get(){
   List<Metric> allMetrics = new ArrayList<>();
   allMetrics = hibernate.createCriteria(Metric.class).list();
   for(Metric metric:allMetrics){
     if(metric.getId() == goalId){
       metrics.add(metric);
     }
   }
   return render();
 }
}
