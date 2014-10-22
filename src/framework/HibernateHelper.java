package framework;

import com.google.common.reflect.ClassPath;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import javax.persistence.Entity;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.hibernate.cfg.AvailableSettings.*;

public class HibernateHelper {
  private static Configuration configuration = new Configuration();
  private static SessionFactory sessionFactory;

  static {
    configuration.setProperty(URL, "jdbc:h2:mem:tarkvarakool;DB_CLOSE_DELAY=-1");
    configuration.setProperty(USER, "sa");
    configuration.setProperty(PASS, "sa");
    configuration.setProperty(DRIVER, "org.h2.Driver");
    configuration.setProperty(DIALECT, "org.hibernate.dialect.H2Dialect");
  }

  public static SessionFactory createSessionFactory() throws IOException {
    return sessionFactory != null ? sessionFactory : buildSessionFactory();
  }

  public static SessionFactory createTestSessionFactory() throws IOException {
    configuration.setProperty(AUTOCOMMIT, "true");
    configuration.setProperty(URL, "jdbc:h2:mem:tarkvarakool_test;DB_CLOSE_DELAY=-1");
    sessionFactory = buildSessionFactory();
    return sessionFactory;
  }

  public static void initDatabase(SessionFactory sessionFactory) {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    Scanner scanner = new Scanner(HibernateHelper.class.getResourceAsStream("/init.sql"));
    List<String> initCommands = getInitSQL(scanner);
    scanner.close();
    for (String command : initCommands) session.createSQLQuery(command).executeUpdate();
    transaction.commit();
    session.close();
  }

  public static void dropAndCreateSchema() {
    new SchemaExport(configuration).create(true, true);
  }

  private static SessionFactory buildSessionFactory() throws IOException {
    prepareDatabase();
    addMappedClasses(configuration);
    new SchemaUpdate(configuration).execute(true, true);
    //noinspection deprecation
    return configuration.buildSessionFactory();
  }

  private static void prepareDatabase() {
    try {
      Connection connection = DriverManager.getConnection(configuration.getProperty(URL), configuration.getProperty(USER), configuration.getProperty(PASS));
      connection.createStatement().execute("SET DATABASE COLLATION ESTONIAN");
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private static void addMappedClasses(Configuration configuration1) throws IOException {
    ClassPath.from(HibernateHelper.class.getClassLoader()).getTopLevelClassesRecursive("model").stream()
      .map(ClassPath.ClassInfo::load)
      .filter(modelClass -> modelClass.isAnnotationPresent(Entity.class))
      .forEach(configuration1::addAnnotatedClass);
  }

  private static List<String> getInitSQL(Scanner scanner) {
    List<String> commands = new ArrayList<>();
    String command = "";
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      command += " " + line;
      if (line.endsWith(";")) {
        commands.add(command.trim());
        command = "";
      }
    }
    return commands;
  }
}
