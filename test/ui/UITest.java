package ui;

import com.codeborne.selenide.Selenide;
import framework.HibernateHelper;
import framework.Launcher;
import framework.Messages;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

import static framework.HibernateHelper.createTestSessionFactory;
import static framework.Messages.DEFAULT;
import static org.hibernate.FlushMode.ALWAYS;

public class UITest {
  private static final int PORT = 8361;
  protected Session hibernate;
  protected Messages.Resolver messages = new Messages(false).getResolverFor(DEFAULT);

  private static SessionFactory sessionFactory;

  static {
    try {
      Launcher.prepareLogging();
      sessionFactory = createTestSessionFactory();
      Launcher
        .createServer(PORT, true)
        .start();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void open(String relativePath) {
    Selenide.open("http://localhost:" + PORT + relativePath);
  }

  @Before
  public void before() throws IOException {
    HibernateHelper.dropAndCreateSchema();
    hibernate = sessionFactory.openSession();
    hibernate.setFlushMode(ALWAYS);
  }

  @After
  public void after() {
    hibernate.close();
  }
}
