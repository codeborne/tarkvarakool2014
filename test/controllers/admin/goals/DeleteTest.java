package controllers.admin.goals;

import controllers.ControllerTest;
import model.Goal;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DeleteTest extends ControllerTest<Delete> {

  @Before
  public void setUp() throws Exception {
    when(session.getAttribute("username")).thenReturn("Some username");
  }

  @Test
  public void postDeletesGoal() {
    controller.id = 5L;
    Goal expectedGoal = new Goal("name", 300);
    when(hibernate.get(Goal.class, 5L)).thenReturn(expectedGoal);

    assertRedirect(Home.class, controller.post());

    verify(hibernate).delete(expectedGoal);
  }

  @Test (expected = HibernateException.class)
  public void postDeleteThrowsHibernateException() {
    controller.id = 5L;
    Goal goal = new Goal("", 1);
    when(hibernate.get(Goal.class, 5L)).thenReturn(goal);
    doThrow(mock(HibernateException.class)).when(hibernate).delete(goal);

  assertRender(controller.post());
  }

  @Test
  public void postNoObjectToDelete() {
    controller.id = 5L;
    when(hibernate.get(Goal.class, 5L)).thenReturn(null);

    assertRedirect(Home.class, controller.post());
  }
}
