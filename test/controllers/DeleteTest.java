package controllers;

import framework.Redirect;
import model.Goal;
import org.hibernate.Session;
import org.junit.Test;

import static framework.HibernateMockHelper.getDeletedEntities;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteTest {

  @Test
  public void postSuccess() {
    Session hibernate = mock(Session.class);

    Delete delete = new Delete();
    delete.id = 5L;
    delete.hibernate = hibernate;

    Goal expectedGoal = new Goal("name", 300);
    when(hibernate.get(Goal.class, 5L)).thenReturn(expectedGoal);

    try {
      delete.post();
      fail();
    } catch (Redirect e) {
      assertEquals("goals", e.getMessage());
      assertEquals(expectedGoal, getDeletedEntities(hibernate).get(0));
    }
  }
}
