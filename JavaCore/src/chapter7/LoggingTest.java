package chapter7;

import java.util.logging.*;

public class LoggingTest {
    private static final Logger myLogger = Logger.getLogger("my.test.logger");
    public static void main(String[] args) {
//        myLogger.entering("chapter7.LoggingTest", "main");
        myLogger.setLevel(Level.WARNING);
        int i;
        myLogger.info("Below i");
        i = 5;
        myLogger.log(Level.INFO, "We have set i.");
        System.out.println(i);
        myLogger.log(Level.WARNING, "End program.");
        myLogger.log(Level.SEVERE, "SEVERE test.");
//        myLogger.exiting("chapter7.LoggingTest", "main");
    }
}
