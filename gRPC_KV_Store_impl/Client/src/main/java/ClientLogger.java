import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * ClientLogger - Class to log client meessages.
 */
public class ClientLogger {
  private static ClientLogger instance;
  private static FileHandler fh;
  private static Logger logger;

  /**
   * Default Constructor of Client Logger.
   */
  public ClientLogger(String filename) {
    try {
      // Create and format logger
      logger = Logger.getLogger("Client Logger");
      fh = new FileHandler(filename + ".txt", true);
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
   * @throws IOException - Log may throw IOException.
   */
  public static void logError(String errorMessage) throws IOException {
    logger.log(Level.WARNING,  errorMessage);
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
  public static ClientLogger getInstance(String filename) {
    if(instance == null) {
      instance = new ClientLogger(filename);
    }
    return instance;
  }

  /**
   * closeLogFile - method to close filehandler.
   */
  public void closeLogFile(){
    fh.close();
  }
}
