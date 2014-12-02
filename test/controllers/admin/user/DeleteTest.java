package controllers.admin.user;

import controllers.ControllerTest;
import model.User;
import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class DeleteTest extends ControllerTest<Delete>{
  @Before
  public void setUp() throws Exception {
    when(request.getPathInfo()).thenReturn("admin/");



  }

  @Test
  public void get() throws Exception {
    when(hibernate.createCriteria(User.class).setProjection(any(AggregateProjection.class)).list()).thenReturn(null);
    assertRender(controller.get());
  }

  @Test
  public void postIfUserIsNull() throws Exception {
    controller.username = "user";

    when(hibernate.createCriteria(User.class).setProjection(any(AggregateProjection.class)).uniqueResult()).thenReturn(null);

    assertRender(controller.get());

    verify(hibernate, never()).delete(any(User.class));
  }

  @Test
  public void postDeleteSuccess() throws Exception {
    controller.username = "user";
    User user = new User("user","pass");
    when(hibernate.createCriteria(User.class).add(any(SimpleExpression.class)).uniqueResult()).thenReturn(user);

    assertRedirect(Delete.class, controller.post());

    User deletedUser = getDeletedEntity();

    verify(hibernate).delete(deletedUser);
  }
}