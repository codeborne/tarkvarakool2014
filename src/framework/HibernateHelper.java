package framework;

import com.google.common.reflect.ClassPath;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hibernate.cfg.AvailableSettings.*;

public class HibernateHelper {
  private static Configuration configuration = new Configuration(     );
  private static SessionFactory sessionFactory;
  private static boolean dbMigrationNeeded = true;

  static {
    try {
      configuration.getProperties().load(HibernateHelper.class.getResourceAsStream("/db.properties"));
    }
    catch (IOException e) {
      throw new RuntimeException("Cannot load db.properties", e);
    }
  }

  public static SessionFactory createSessionFactory(boolean devMode) throws IOException {
    return sessionFactory != null ? sessionFactory : buildSessionFactory(devMode);
  }

  public static SessionFactory createTestSessionFactory() throws IOException {
    dbMigrationNeeded = false;
    configuration.setProperty(AUTOCOMMIT, "true");
    configuration.setProperty(URL, "jdbc:h2:mem:tarkvarakool_test;DB_CLOSE_DELAY=-1");
    return sessionFactory = buildSessionFactory(true);
  }

  public static void migrateDatabase(boolean devMode) {
    if (!dbMigrationNeeded) return;

    try {
      ClassLoaderResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
      Database database = DatabaseFactory.getInstance().openDatabase(configuration.getProperty(URL),
        configuration.getProperty(USER), configuration.getProperty(PASS), configuration.getProperty(DRIVER), null, null, null, resourceAccessor);
      Liquibase liquibase = new Liquibase("db.xml", resourceAccessor, database);
      liquibase.update(devMode ? "dev" : "");
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void dropAndCreateSchema() {
    new SchemaExport(configuration).create(true, true);
  }

  private static SessionFactory buildSessionFactory(boolean devMode) throws IOException {
    prepareDatabase();
    addMappedClasses(configuration);
    migrateDatabase(devMode);
    //noinspection deprecation
    return configuration.buildSessionFactory();
  }

  private static void prepareDatabase() {
    try {
      Connection connection = DriverManager.getConnection(configuration.getProperty(URL), configuration.getProperty(USER), configuration.getProperty(PASS));
      connection.createStatement().execute("SET DATABASE COLLATION ESTONIAN STRENGTH SECONDARY");
      connection.close();
    }
    catch (SQLException e) {
      LoggerFactory.getLogger(HibernateHelper.class).warn(e.toString());
    }
  }

  private static void addMappedClasses(Configuration configuration1) throws IOException {
    ClassPath.from(HibernateHelper.class.getClassLoader()).getTopLevelClassesRecursive("model").stream()
      .map(ClassPath.ClassInfo::load)
      .filter(modelClass -> modelClass.isAnnotationPresent(Entity.class))
      .forEach(configuration1::addAnnotatedClass);
  }
}
