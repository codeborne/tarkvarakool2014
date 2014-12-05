package model;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MetricTest {

  Metric metric = new Metric();

  @Test
  public void levelsAreNotValidWhenEitherIsEmpty() throws Exception {
    assertFalse(metric.levelsAreValid());

    metric.setStartLevel(12.0);
    assertFalse(metric.levelsAreValid());

    metric.setStartLevel(null);
    metric.setTargetLevel(44.3);
    assertFalse(metric.levelsAreValid());

    metric.setStartLevel(22.2);
    assertTrue(metric.levelsAreValid());
  }

  @Test
  public void levelsAreNotValidWhenEqual() throws Exception {
    metric.setStartLevel(11.1);
    metric.setTargetLevel(11.1);
    assertFalse(metric.levelsAreValid());
  }

  @Test
  public void getInfoSourceAsListOfWordsAndLinks() throws Exception {
    metric.setInfoSource("2014 Statistikaamet http://www.stat.ee/\n 2015\n 2016");
    List<String> infoSourceContent = metric.getInfoSourceAsListOfWordsAndLinks("est");
    assertEquals(5, infoSourceContent.size() );
    assertEquals("2014", infoSourceContent.get(0));
    assertEquals("Statistikaamet", infoSourceContent.get(1));
    assertEquals("http://www.stat.ee/\n", infoSourceContent.get(2));
    assertEquals("2015\n", infoSourceContent.get(3));
    assertEquals("2016", infoSourceContent.get(4));
  }
}