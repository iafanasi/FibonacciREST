package test;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.emc.fibonacci.engine.PrintableFibonacciSequence;

/**
 * Trivial test checking the response of requested sequence
 * starting from 0th and ending at 0th fibonacci number.
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public class SeqLimits2 implements IUnitTest {
	
	private static final String GOLDEN_DATA = "[0]";
	
	public String testName() {
		return getClass().getSimpleName();
	}
	
	public boolean execute() {
		try {
			//NB: item# starts from 0
			PrintableFibonacciSequence seq = new PrintableFibonacciSequence();
			seq.setLimits(0, 0);
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
		SeqLimits2 test = new SeqLimits2();
		test.execute();
	}
	

}
