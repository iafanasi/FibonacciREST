package com.emc.fibonacci.rest;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import com.emc.fibonacci.logging.CustomLogger;
import com.emc.fibonacci.logging.StackTraceAsString;

/**
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public class RestRequest {

	// valid request URL expressions  
    private static final Pattern pattSequenceN_   = Pattern.compile("/seq/([0-9]+)");
    private static final Pattern pattNthItem_     = Pattern.compile("/item/([0-9]+)");
    private static final Pattern pattNthNum_      = Pattern.compile("/num/([0-9]+)");
    private static final Pattern pattSubsequence_ = Pattern.compile("/subseq/([0-9]+)/([0-9]+)");

    // logger
	private static Logger logger        = CustomLogger.getInstance();
    
    
    public static final String USAGE;
    
    static {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Error: unexpected URI format.\n\n\tValid requests must have the following format:\n");
    	sb.append("\t\t" + pattSequenceN_ + "\n");
    	sb.append("\t\t" + pattNthItem_ + "\n");
    	sb.append("\t\t" + pattNthNum_ + "\n");
    	sb.append("\t\t" + pattSubsequence_ + "\n");
    	sb.append("\n\n\tOnly Non-negative integers are expected as part of request.");
    	USAGE = sb.toString();
    }

	// to be used for "subject" identification in REST URL (request)
	public enum Subject { SEQUENCE, ITEM, SUBSEQUENCE }
    
    // request's subject itself
    private Subject subject_;
    
    // request's values
    private ArrayList<Integer> values_;
    
    // REST request construction based on HttpServletRequest.getPathInfo() value
    public RestRequest(String pathInfo) throws ServletException {
        Matcher matcher;
        values_ = new ArrayList<Integer>();
        matcher = pattSequenceN_.matcher(pathInfo);
        if (matcher.find()) {
        	subject_ = Subject.SEQUENCE;
        	values_.add(Integer.parseInt(matcher.group(1)));
        	return;
        }
        matcher = pattNthItem_.matcher(pathInfo);
        if (matcher.find()) {
        	subject_ = Subject.ITEM;
        	values_.add(Integer.parseInt(matcher.group(1)));
        	return;
        }
        matcher = pattNthNum_.matcher(pathInfo);
        if (matcher.find()) {
        	subject_ = Subject.ITEM;
        	values_.add(Integer.parseInt(matcher.group(1)));
        	return;
        }
        matcher = pattSubsequence_.matcher(pathInfo);
        if (matcher.find()) {
        	subject_ = Subject.SUBSEQUENCE;
        	values_.add(Integer.parseInt(matcher.group(1)));
        	values_.add(Integer.parseInt(matcher.group(2)));
        	return;
        }
        throw new ServletException("Invalid URI");
    }    

    // subject of REST URL (see also: RestRequest.Subject)  
    public Subject getSubject() {
    	return subject_;
    }
    
    // number of slash-separated int values in REST URL  
    public int numIntVals() {
    	return values_.size();
    }
    
    // i-th slash-separated int value in REST URL, starting from 0;
    // may throw exception upon out of bounds
    public Integer getValue(int i) {
    	int ret = -1;
    	try {
    		ret = values_.get(i);
    	} catch(IndexOutOfBoundsException e) {
    		StackTraceAsString stas = new StackTraceAsString(e);
	        stas.logStackTrace(logger);
    	}
    	return ret;
    }
    
}
