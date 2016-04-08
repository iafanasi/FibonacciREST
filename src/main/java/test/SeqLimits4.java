package test;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.emc.fibonacci.engine.PrintableFibonacciSequence;

/**
 * Almost a duplicate for SeqLimits3 -- except it checks against single
 * 100th fibonacci number value.
 * Consider it as /fib/item (not fib/seq or fib/subseq) check 
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public class SeqLimits4 implements IUnitTest {

	private static String GOLDEN_DATA = "[354224848179261915075]";
	
	public String testName() {
		return getClass().getSimpleName();
	}
	
	public boolean execute() {
		try {
			//NB: item# starts from 0
			PrintableFibonacciSequence seq = new PrintableFibonacciSequence();
			seq.setLimits(100, 100);
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
		SeqLimits4 test = new SeqLimits4();
		test.execute();
	}
	

}
