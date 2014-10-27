package controllers.admin.goals;

import controllers.ControllerTest;
import model.Goal;
import org.hibernate.Criteria;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AddTest extends ControllerTest<Add> {

  @Test
  public void saveSuccess() {
    controller.name = "ab cd";
    controller.budget = 111;
    controller.comment = "rioaerioaje";
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Goal.class)).thenReturn(criteria);
    when(criteria.list()).thenReturn(Arrays.asList(new Goal("some goal", 1000),new Goal("goal", 100)));

    controller.post();

    Goal savedGoal = (Goal) getSavedEntity();

    assertEquals("ab cd", savedGoal.getName());
    assertEquals(111, (int) savedGoal.getBudget());
    assertEquals("rioaerioaje", savedGoal.getComment());
    assertEquals(3, (int)savedGoal.getSequenceNumber());

    verify(hibernate).save(savedGoal);
  }

  @Test
  public void saveEmptyCommentSuccess() {
    controller.name = "ab cd";
    controller.budget = 111;
    controller.comment = "";
    Criteria criteria = mock(Criteria.class);
    when(hibernate.createCriteria(Goal.class)).thenReturn(criteria);
    when(criteria.list()).thenReturn(Arrays.asList(new Goal("some goal", 1000)));


    controller.post();

    Goal savedGoal = (Goal) getSavedEntity();

    assertEquals("ab cd", savedGoal.getName());
    assertEquals(111, (int) savedGoal.getBudget());
    assertEquals(null, savedGoal.getComment());
    assertEquals(2, (int)savedGoal.getSequenceNumber());

    verify(hibernate).save(savedGoal);
  }

  @Test
  public void get() {
    assertRender(controller.get());
  }

}
