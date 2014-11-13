package controllers;

import framework.Result;
import framework.Role;
import model.Goal;

import java.util.ArrayList;
import java.util.Calendar;

import static org.hibernate.criterion.Order.asc;

public class Charts extends UserAwareController {


  public java.util.List<Goal> goals = new ArrayList<>();
  public Integer minimumYear = UserAwareController.MINIMUM_YEAR;
  public Integer maximumYear = UserAwareController.MAXIMUM_YEAR;
  public int currentYear;


  @Override
  @Role("anonymous")
  public Result get() {
      goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();
      currentYear = Calendar.getInstance().get(Calendar.YEAR);
      return render();
    }
  }
