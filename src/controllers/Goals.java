package controllers;

import framework.Controller;
import framework.Redirect;
import model.Goal;

import java.util.List;
import java.util.Map;

public class Goals extends Controller {

  public List<Goal> goals;

  @Override
  public void get() {
    goals = hibernate.createCriteria(Goal.class).list();
  }

  @Override
  public void post() {
    Map<String, String[]> map = request.getParameterMap();
    String[] sums = map.get("sum");
    Goal goal = new Goal(map.get("goal")[0], Integer.parseInt(sums[0]));
    hibernate.saveOrUpdate(goal);
    throw new Redirect("goals");
  }
}
