package com.emc.fibonacci.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom logging machinery for Fibonacci REST service and internals
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public class CustomLogger {
    public static String        logFileName    = "FibonacciREST.log";
    private static final Logger logger         = Logger.getLogger("FibonREST.log");
    private static FileHandler  logFileHandler = null;
    static {
    	try {
    		logFileHandler = new FileHandler(logFileName, false);
    	} catch(IOException e) {
    		e.printStackTrace(System.err);
    	}
		logger.addHandler(logFileHandler);
		logger.setLevel(Level.ALL);
		logFileHandler.setLevel(Level.ALL);
		logFileHandler.setFormatter(new CustomFormatter());
    }
    
    public static Logger getInstance() {
    	return logger;
    }
}
