package controllers.admin.goals;

import model.Goal;

public class Add extends Save {

  Integer sequenceNumber;

  @Override
  protected void save() {
    sequenceNumber = hibernate.createCriteria(Goal.class).list().size()+1;
    hibernate.save(new Goal(name, comment, budget, sequenceNumber));
  }
}
