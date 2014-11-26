package model;

import org.junit.Test;

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
}