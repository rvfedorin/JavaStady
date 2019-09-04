package other;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
    public static void main(String[] args) {
        String fileName = "./test.log";
        Logger logger = Logger.getLogger("TestLogger");
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler(fileName, true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info("[*] Logger INFO string 1");

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        logger.info("[*] Logger INFO string 2");
    }
}
