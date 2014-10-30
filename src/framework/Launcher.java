package framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.SessionHandler;

import java.lang.management.ManagementFactory;
import java.net.BindException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Launcher {

  private final static Logger LOG = LogManager.getLogger();

  public static void main(String... args) throws Exception {
    int port = 8080;
    if (args.length == 1) port = Integer.parseInt(args[0]);

    String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    Files.write(Paths.get("server.pid"), pid.getBytes());

    prepareLogging();
    Server server = createServer(port);
    try {
      server.start();
      server.join();
    } catch (BindException e) {
      System.err.println("Using port " + port);
      System.err.println(e.toString());
      System.exit(-1);
    }
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
