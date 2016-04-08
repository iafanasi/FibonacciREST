package test;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.emc.fibonacci.engine.PrintableFibonacciSequence;

/**
 * Check against 1000th fibonacci number value.
 * /fib/item check
 * but implicitly checks all fibonacci sequence #0 to #1000, since
 * #1000 is recurrent result of all previous number in a sequence calculation 
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public class SeqLimits5 implements IUnitTest {

	private static String GOLDEN_DATA = "[43466557686937456435688527675040625802564660517371780402481729089536555" +
	                                    "417949051890403879840079255169295922593080322634775209689623239873322471" + 
			                            "161642996440906533187938298969649928516003704476137795166849228875]";
	
	public String testName() {
		return getClass().getSimpleName();
	}
	
	public boolean execute() {
		try {
			//NB: item# starts from 0
			PrintableFibonacciSequence seq = new PrintableFibonacciSequence();
			seq.setLimits(1000, 1000);
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
		SeqLimits5 test = new SeqLimits5();
		test.execute();
	}
	

}
