package projectTwo.KVStore;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * ServerLogger - class to log server messages.
 */
public class ServerLogger {
  private static ServerLogger instance;
  static FileHandler fh;
  private static Logger logger;

  /**
   * Default Constructor of Server Logger.
   */
  public ServerLogger() {
    try {
       // Create and format logger
      logger = Logger.getLogger("Server Logger");
      fh = new FileHandler("ServerLogger.txt", true);
      SimpleFormatter formatter = new SimpleFormatter();
      fh.setFormatter(formatter);
      logger.addHandler(fh);

       // Log errors that occur while instantiating logger
    } catch (Exception e) {
      logger.log(Level.WARNING,"Exception while instantiating logger: ", e);
    }
  }

  /**
   * logError - method to log errors.
   * @param errorMessage - Error message to log.
   */
  public static void logError(String errorMessage) {
      logger.log(Level.WARNING, errorMessage);
  }

  /**
   * logInfo - method to log general commands.
   * @param msg - Message to log.
   */
  public static void logInfo(String msg) {
    logger.info(msg);
  }

  /**
   * Getter method for instance.
   * @return - Return instance of logger.
   */
  public static ServerLogger getInstance() {
    if(instance == null) {
      instance = new ServerLogger();
    }
    return instance;
  }

  /**
   * closeLogFile - method to close filehandler.
   */
  public void closeLogFile() {
    fh.close();
  }
}
