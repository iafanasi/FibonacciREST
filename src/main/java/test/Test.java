package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Test
 */
@WebServlet(description = "Servlet used for testing purposes", urlPatterns = { "/test" })
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		Suite suite = new Suite();
		PrintWriter pw = response.getWriter();
		pw.println("*** Self-check suite for Fibonacci REST service ***");
		pw.println(); pw.println();
		boolean success = suite.runUtests(pw);
		success &= suite.runPerfTests(pw);
		String status = success ? "Self-check suite ran SUCCESSFULY" : "Suite FAILED";
		pw.println(); pw.println();
		pw.println(status);
		pw.close();
	}

}
