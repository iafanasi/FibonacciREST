package com.emc.fibonacci.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper to log exepction stack traces.
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public class StackTraceAsString {
	private String ss;
	private String msg;
    public StackTraceAsString(Exception e) {
        if (e == null) {
        	ss = "";
            return;
        }
        StringWriter sw = new StringWriter(1024);
        final PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();
        ss = sw.toString();
        pw.close();
    }
    
    public void logStackTrace(Logger l) {
    	l.severe(msg);
    	l.log(Level.INFO, ss);
    }
}
