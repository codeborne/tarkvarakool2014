package framework;

import com.google.common.reflect.ClassPath;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import javax.persistence.Entity;
import java.io.IOException;

import static org.hibernate.cfg.AvailableSettings.*;

public class HibernateHelper {
  private static Configuration configuration = new Configuration();
  private static SessionFactory sessionFactory;
  private static boolean migrateDatabaseNeeded = true;

  static {
    configuration.setProperty(URL, "jdbc:h2:./moodikud");
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
    migrateDatabaseNeeded = false;
    return sessionFactory;
  }

  public static void migrateDatabase() {
    if (!migrateDatabaseNeeded) return;

    // use schema update for now, todo: later uncomment creating scripts in db.xml and keep using only liquibase
    new SchemaUpdate(configuration).execute(true, true);

    try {
      ClassLoaderResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
      Database database = DatabaseFactory.getInstance().openDatabase(configuration.getProperty(URL), configuration.getProperty(USER), configuration.getProperty(PASS), null, resourceAccessor);
      Liquibase liquibase = new Liquibase("db.xml", resourceAccessor, database);
      liquibase.update("");
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void dropAndCreateSchema() {
    new SchemaExport(configuration).create(true, true);
  }

  private static SessionFactory buildSessionFactory() throws IOException {
    addMappedClasses(configuration);
    //noinspection deprecation
    return configuration.buildSessionFactory();
  }

  private static void addMappedClasses(Configuration configuration1) throws IOException {
    ClassPath.from(HibernateHelper.class.getClassLoader()).getTopLevelClassesRecursive("model").stream()
      .map(ClassPath.ClassInfo::load)
      .filter(modelClass -> modelClass.isAnnotationPresent(Entity.class))
      .forEach(configuration1::addAnnotatedClass);
  }
}
