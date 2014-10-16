package controllers;

import framework.Controller;
import framework.Result;
import model.Goal;

import java.util.ArrayList;
import java.util.List;

public class Goals extends Controller {
  public List<Goal> goals = new ArrayList<>();

  @Override
  public Result get() {
    goals = hibernate.createCriteria(Goal.class).list();
    return render();
  }

}
