package controllers;

import controllers.admin.values.Value;
import framework.Result;
import framework.Role;
import model.Goal;
import model.Metric;

//TODO remove the whole class when done with testing
public class Test extends UserAwareController {

  @Override
  @Role("anonymous")
  public Result get() {
    session.setAttribute("username", "test");

    Goal goal1 = new Goal("Sisestatud eesmark", 100);
    hibernate.save(goal1);
    hibernate.flush();
    hibernate.save(new Metric(goal1, "Some metric", "", "", 0, "", 33, "", "", "aa"));
    hibernate.flush();
    hibernate.save(new Metric(goal1, "Some other metric", "", "abc", 777, "", 0, "", "", ""));
    hibernate.flush();

    Goal goal2 = new Goal("Second goal", 100);
    hibernate.save(goal2);
    hibernate.flush();
    hibernate.save(new Metric(goal2, "A metric", "", "", 8, "", 33, "", "cnf tgh", "aa"));
    hibernate.flush();
    hibernate.save(new Metric(goal2, "ABC metric", "", "abc", 0, "", 0, "", "dfghdtgfy iutyuio", ""));
    hibernate.flush();
    hibernate.save(new Metric(goal2, "DEF metric", "", "def", 0, "", 0, "", "dsfhgf iutyuio", "dsfhgjk"));
    hibernate.flush();

    return redirect(Value.class);
  }
}
