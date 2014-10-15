package controllers;

import framework.Redirect;
import model.Goal;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import static framework.HibernateMockHelper.getDeletedEntities;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
      assertSame(expectedGoal, getDeletedEntities(hibernate).get(0));
    }
  }

  @Test (expected = HibernateException.class)
  public void postDeleteFailure() {
    Session hibernate = mock(Session.class);

    Delete delete = new Delete();
    delete.id = 5L;
    delete.hibernate = hibernate;

    Goal goal = new Goal("", 1);
    when(hibernate.get(Goal.class, 5L)).thenReturn(goal);
    doThrow(new HibernateException("")).when(hibernate).delete(goal);

    delete.post();
  }

  @Test
  public void postFailure() {
    Delete delete = new Delete();
    delete.id = 5L;
    delete.hibernate = mock(Session.class);

    when(delete.hibernate.get(Goal.class, 5L)).thenReturn(null);

    try {
      delete.post();
      fail();
    }
    catch (Redirect e) {
      assertEquals("goals", e.getMessage());
    }
  }

}
