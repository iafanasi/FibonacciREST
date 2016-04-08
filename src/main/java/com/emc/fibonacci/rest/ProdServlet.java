package com.emc.fibonacci.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emc.fibonacci.engine.PrintableFibonacciSequence;
import com.emc.fibonacci.logging.CustomLogger;
import com.emc.fibonacci.logging.StackTraceAsString;

/**
 * Servlet implementation class ProdServlet.
 * Implements web interface for fibonacci sequences retrieval.
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
@WebServlet(description = "Fibonnacci sequence retrieval: production servlet", urlPatterns = { "/*" })
public class ProdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = CustomLogger.getInstance();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProdServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.entering("ProdServlet", "doGet");
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
	    try {
	        RestRequest restReq = new RestRequest(request.getPathInfo());
	        RestRequest.Subject subj = restReq.getSubject();
        	Integer i0 = restReq.getValue(0) - 1;
        	Integer i1 = restReq.numIntVals() > 1 ? restReq.getValue(1) - 1 : -1;
        	PrintableFibonacciSequence  seq = new PrintableFibonacciSequence();
	        if (subj == RestRequest.Subject.SEQUENCE) {
	        	seq.setLimits(0, i0);
	        } else if (subj == RestRequest.Subject.ITEM) {
	        	seq.setLimits(i0, i0);
	        } else if (subj == RestRequest.Subject.SUBSEQUENCE) {
	        	seq.setLimits(i0, i1);
	        }
	        seq.directPrint(out);
	    } catch (Exception e) {
	        //response.resetBuffer();
	        out.println(RestRequest.USAGE);
	    	StackTraceAsString sts = new StackTraceAsString(e);
	        sts.logStackTrace(logger);
	    } finally {
		    out.close();
	    }
		logger.exiting("ProdServlet", "doGet");
	}

}
