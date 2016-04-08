package test;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.emc.fibonacci.engine.PrintableFibonacciSequence;

/**
 * Trivial test checking the response of requested sequence
 * starting from 1st and ending at 4th fibonacci number.
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public class SeqLimits1 implements IUnitTest {
	
	private static final String GOLDEN_DATA = "[1,1,2,3]";
	
	public String testName() {
		return getClass().getSimpleName();
	}
	
	public boolean execute() {
		try {
			//NB: item# starts from 0
			PrintableFibonacciSequence seq = new PrintableFibonacciSequence();
			seq.setLimits(1, 4);
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

}
