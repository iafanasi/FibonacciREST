package com.emc.fibonacci.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.LogRecord;

/**
 * Custom log record formatter for Fibonacci REST service and internals
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public class CustomFormatter extends java.util.logging.Formatter {
	private static SimpleDateFormat  dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS ", Locale.US);
	private static GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("MSK"));
	
    @Override
    public String format(LogRecord record) {
    	calendar.setTimeInMillis(record.getMillis());
    	String threadId = String.valueOf(record.getThreadID());
    	String msg = record.getMessage();
    	String method = record.getSourceMethodName();
    	String cname = record.getSourceClassName();
    	String ts = dateFmt.format(calendar.getTime());
    	StringWriter swIn  = new StringWriter(512);
    	StringWriter swOut = new StringWriter(512);
    	swIn.append(ts).append(" thr__").append(threadId).append(" ").append(cname).append(".").append(method);
    	PrintWriter pw = new PrintWriter(swOut);
    	pw.printf(Locale.US, "%-100s", swIn.toString());
    	pw.printf(Locale.US, "%s%n", msg);
    	pw.close();
    	return swOut.toString() ;
    }
}
