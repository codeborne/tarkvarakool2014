package liquibase.ext;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.logging.core.AbstractLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LiquibaseLog4j2Logger extends AbstractLogger {
  private Logger logger = LogManager.getLogger();

  @Override
  public void setName(String name) {
    logger = LogManager.getLogger(name);
  }

  @Override
  public void severe(String message) {
    logger.error(message);
  }

  @Override
  public void severe(String message, Throwable e) {
    logger.error(message, e);
  }

  @Override
  public void warning(String message) {
    logger.warn(message);
  }

  @Override
  public void warning(String message, Throwable e) {
    logger.warn(message, e);
  }

  @Override
  public void info(String message) {
    logger.info(message);
  }

  @Override
  public void info(String message, Throwable e) {
    logger.info(message, e);
  }

  @Override
  public void debug(String message) {
    logger.debug(message);
  }

  @Override
  public void debug(String message, Throwable e) {
    logger.debug(message, e);
  }

  @Override
  public void setLogLevel(String logLevel, String logFile) {
  }

  @Override
  public void setChangeLog(DatabaseChangeLog databaseChangeLog) {
  }

  @Override
  public void setChangeSet(ChangeSet changeSet) {
  }

  @Override
  public int getPriority() {
    return Integer.MAX_VALUE;
  }
}