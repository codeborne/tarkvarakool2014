package controllers;

import framework.NoBind;
import framework.Result;
import framework.Role;
import model.Goal;

import java.util.ArrayList;
import java.util.Calendar;

import static org.hibernate.criterion.Order.asc;

public class Values extends UserAwareController{
  @NoBind
  public java.util.List<Goal> goals = new ArrayList<>();
  @NoBind
  public Integer minimumYear = MINIMUM_YEAR;
  @NoBind
  public Integer maximumYear = MAXIMUM_YEAR;
  @NoBind
  public int currentYear;

  @Override
  @Role("anonymous")
  public Result get() {
    goals = hibernate.createCriteria(Goal.class).addOrder(asc("sequenceNumber")).list();
    currentYear =  Calendar.getInstance().get(Calendar.YEAR);
    return render();
  }

}
