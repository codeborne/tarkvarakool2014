package framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;

public class Launcher {

  private final static Logger LOG = LogManager.getLogger();

  public static void main(String[] args) throws Exception {
    freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
    LOG.info("starting");
    Server server = new Server(8080);
    server.setHandler(new Handler());
    server.start();
    server.join();
  }
}
