package controllers;

import framework.Redirect;
import model.Goal;
import org.hibernate.Session;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DeleteTest {
  @Test
  public void postSuccess() {
    Delete delete = new Delete();
    delete.id = 5L;
    delete.hibernate = mock(Session.class);

    Goal expectedGoal = new Goal("name", 300);
    when(delete.hibernate.get(Goal.class, 5L)).thenReturn(expectedGoal);

    try {
      delete.post();
      fail();
    } catch (Redirect e) {
      assertEquals("goals", e.getMessage());

      ArgumentCaptor<Goal> captor = ArgumentCaptor.forClass(Goal.class);
      verify(delete.hibernate).delete(captor.capture());

      Goal realGoal = captor.getValue();
      assertSame(expectedGoal, realGoal);
    }
  }

  @Test
  public void postFailure() {
    Delete delete = new Delete();
    delete.id = 5L;
    delete.hibernate = mock(Session.class);

    Goal expectedGoal = null;
    when(delete.hibernate.get(Goal.class, 5L)).thenReturn(expectedGoal);

    try {
      delete.post();
      fail();
    }
    catch (Redirect e) {
      assertEquals("goals", e.getMessage());
    }

    try {
      delete.post();
      fail();
    }
    catch (Exception e) {
      assertEquals("Goal id not found.", e.getMessage());
    }
  }

}
