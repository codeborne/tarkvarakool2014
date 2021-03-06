package ui;

import model.Goal;
import model.Metric;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static junit.framework.TestCase.assertEquals;

public class EnforceAuthentication extends UITest {

  @Before
  public void logIn() throws Exception {

    Goal goal = new Goal("Sisestatud eesmark","", 100, 1);
    hibernate.save(goal);
    hibernate.save(new Metric(goal, "Some metric", "", "", "", 0.0, "", 0.0, "", "tt", "", 2.2, true));
    hibernate.save(new Metric(goal, "Other metric", "", "", "", 2.0, "", 0.0, "", "", "dd", 2.3, true));
    hibernate.save(new Metric(goal, "Third metric", "", "", "jj", 8.0, "", 0.0, "", "", "dd", 2.4, true));
  }

  @Test
  public void UnauthorizedUserGetsRedirectedWhenTryingToAccessAdminPAges() throws MalformedURLException {
    assertEquals("/home", getFinalUrl("/admin/goals"));
    assertEquals("/home", getFinalUrl("/admin/metrics/metrics?goalId=1"));
  }

  private String getFinalUrl(String url) throws MalformedURLException {
    open(url);
    String finalUrl = getWebDriver().getCurrentUrl();
    return new URL(finalUrl).getPath();
  }
}
