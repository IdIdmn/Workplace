package BaumansGate.Output;

import java.util.logging.*;
import java.io.IOException;

public class GameLogger {

    private static Logger logger = Logger.getLogger("BGlogger");
    private static String logFilePath = "Save";

    static public void setLogFilePath(String newLogFilePath){
        if(logFilePath.equals("Save")) {
            try {
                FileHandler fh = new FileHandler(newLogFilePath, true);
                fh.setFormatter(new SimpleFormatter());
                logger.addHandler(fh);
                logger.setUseParentHandlers(false);
                logFilePath = newLogFilePath;
            } catch (SecurityException | IOException e) {
                System.out.println("Мдаа.... Беда.");
            }
        }
    }

    public static void logInfo(String message){
        logger.info(message);
    }

    public static void logSevere(String message){
        logger.severe(message);
    }

    public static void logWarning(String message){
        logger.warning(message);
    }

}
