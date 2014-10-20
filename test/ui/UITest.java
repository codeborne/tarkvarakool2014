package ui;

import com.codeborne.selenide.Selenide;
import framework.HibernateHelper;
import framework.Launcher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

import static framework.HibernateHelper.createSessionFactory;

public class UITest {
  private static final int PORT = 9000;
  protected Session session;

  private static SessionFactory sessionFactory;

  static {
    try {
      sessionFactory = createSessionFactory(true);
      Launcher
        .createServer(PORT)
        .start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void open(String relativePath) {
    Selenide.open("http://localhost:" + PORT + relativePath);
  }

  @Before
  public void before() throws IOException {
    HibernateHelper.dropAndCreateSchema();
    session = sessionFactory.openSession();
  }

  @After
  public void after() {
    session.close();
  }
}
