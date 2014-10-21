package framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.SessionHandler;

public class Launcher {

  private final static Logger LOG = LogManager.getLogger();

  public static void main(String... args) throws Exception {
    prepareLogging();
    Server server = createServer(8080);
    server.start();
    server.join();
  }

  public static Server createServer(int port) throws Exception {
    LOG.info("starting");

    ResourceHandler resourceHandler = new ResourceHandler();
    resourceHandler.setDirectoriesListed(false);
    resourceHandler.setResourceBase("resources");

    HandlerList handlers = new HandlerList();
    handlers.addHandler(new SessionHandler());
    Handler handler = new Handler();
    handler.initialize();
    handlers.addHandler(handler);
    handlers.addHandler(resourceHandler);

    Server server = new Server(port);
    server.setHandler(handlers);
    return server;
  }

  public static void prepareLogging() throws ClassNotFoundException {
    freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
    System.setProperty("org.jboss.logging.provider", "slf4j");
  }
}
