package test;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.emc.fibonacci.engine.PrintableFibonacciSequence;

/**
 * Trivial test checking the response in case if user requested '0' as N in request URL
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 *
 */
public class SeqLimits0 implements IUnitTest {
	
	private static final String GOLDEN_DATA = "[]";
	
	public String testName() {
		return getClass().getSimpleName();
	}
	
	public boolean execute() {
		try {
			//NB: item# starts from 0
			PrintableFibonacciSequence seq = new PrintableFibonacciSequence();
			seq.setLimits(0, -1); //check for sequence of 1st 0 items
	    	StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			seq.directPrint(pw);
	        pw.flush();
	        pw.close();
	        return sw.toString().compareTo(GOLDEN_DATA) == 0;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

	public static void main(String[] args) throws Exception {
		SeqLimits0 test = new SeqLimits0();
		boolean chk = test.execute();
		System.out.println("chk = " + chk);
	}
	

}
