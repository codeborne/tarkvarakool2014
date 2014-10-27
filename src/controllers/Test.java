package controllers;

import controllers.admin.values.Value;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

import java.math.BigDecimal;
import java.util.HashMap;

//TODO remove the whole class when done with testing
public class Test extends UserAwareController {

  @Override
  @Role("anonymous")
  public Result get() {
    session.setAttribute("username", "test");

    Goal goal1 = new Goal("Sisestatud eesmark", 100);
    hibernate.save(goal1);
    hibernate.flush();
    hibernate.save(new Metric(goal1, "Some metric", "", "", "", 0, "", 33, "", "", "aa", 1.0));
    hibernate.flush();
    hibernate.save(new Metric(goal1, "Some other metric", "", "", "abc", 777, "", 0, "", "", "", 2.0));
    hibernate.flush();

    Goal goal2 = new Goal("Second goal", 100);
    hibernate.save(goal2);
    hibernate.flush();
    hibernate.save(new Metric(goal2, "A metric", "", "", "", 8, "", 33, "", "cnf tgh", "aa", 1.0));
    hibernate.flush();

    Metric metric = new Metric(goal2, "ABC metric", "", "", "abc", 0, "", 0, "", "dfghdtgfy iutyuio", "", 2.0);
    metric.setValues(new HashMap<Integer, BigDecimal>() {{
      put(2014, new BigDecimal(55555));
      put(2015, new BigDecimal(888888));
      put(2016, new BigDecimal(12));
      put(2017, new BigDecimal(222222));
      put(2018, new BigDecimal(20000));
    }});
    hibernate.save(metric);
    hibernate.flush();

    metric = new Metric(goal2, "DEF metric", "", "", "def", 0, "", 0, "", "dsfhgf iutyuio", "dsfhgjk", 3.0);
    metric.setValues(new HashMap<Integer, BigDecimal>() {{
      put(2015, new BigDecimal(555));
      put(2016, new BigDecimal(200));
      put(2018, new BigDecimal(1233));
      put(2019, new BigDecimal(9999));
      put(2020, new BigDecimal(12));
    }});
    hibernate.save(metric);
    hibernate.flush();

    return redirect(Value.class);
  }
}