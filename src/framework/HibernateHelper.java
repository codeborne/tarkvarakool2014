package framework;

import com.google.common.reflect.ClassPath;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import javax.persistence.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HibernateHelper {

  public static SessionFactory buildSessionFactory() throws IOException {
    Configuration configuration = new Configuration();
    ClassPath.from(HibernateHelper.class.getClassLoader()).getTopLevelClassesRecursive("model").stream()
      .map(ClassPath.ClassInfo::load)
      .filter(modelClass -> modelClass.isAnnotationPresent(Entity.class))
      .forEach(configuration::addAnnotatedClass);
    new SchemaUpdate(configuration).execute(true, true);
    //noinspection deprecation
    return configuration.buildSessionFactory();
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
