package framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.SessionHandler;

public class Launcher {

  private final static Logger LOG = LogManager.getLogger();

  public static void main(String[] args) throws Exception {
    freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
    System.setProperty("org.jboss.logging.provider", "slf4j");
    LOG.info("starting");

    ResourceHandler resourceHandler = new ResourceHandler();
    resourceHandler.setDirectoriesListed(false);
    resourceHandler.setResourceBase("resources");

    HandlerList handlers = new HandlerList();
    handlers.addHandler(new SessionHandler());
    handlers.addHandler(new Handler());
    handlers.addHandler(resourceHandler);

    Server server = new Server(8080);
    server.setHandler(handlers);
    server.start();
    server.join();
  }
}
