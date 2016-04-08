package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.emc.fibonacci.engine.PrintableFibonacciSequence;

/**
 * Performance test to assure that 1st 10000 fibonacci numbers are
 * getting calculated correct <b>and getting printed out</b>
 * in less than 0.7 seconds
 * (actually, 0.35 seconds is average for this test)
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public class PerfCheck10KSeq implements IPerfTest {
	private static final String REF_FNAME   = "10000.wholeSeq.txt";
	private static final String INIT_FAILED = "Golden data initialization failed";
	
	private static String GOLDEN_DATA = "";
	private long runTimeMs;
	
	private static boolean isGoldenDataReady = false;
	static {
		try {
			byte[] encoded = FileReadHelper.readAllBytes(REF_FNAME);
			GOLDEN_DATA = new String(encoded);
			isGoldenDataReady = true;
		} catch (IOException e) {
			isGoldenDataReady = false;
			e.printStackTrace();
		}
	}

	public String testName() {
		return getClass().getSimpleName();
	}

	public boolean execute() {
		if(!isGoldenDataReady) {
			System.out.println(INIT_FAILED);
			return false;
		}
		try {
			PrintableFibonacciSequence seq = new PrintableFibonacciSequence();
			seq.setLimits(0, 10000);
	    	StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			long tStart = System.currentTimeMillis(); 
			seq.directPrint(pw);
			runTimeMs = System.currentTimeMillis() - tStart; 
	        pw.close();
	        return sw.toString().compareTo(GOLDEN_DATA) == 0;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isPerfOk() {
		// Both calculation of 0 to 10000 and printout run time is counted
		return runTimeMs < 700;
	}

	public long getRunTime() {
		return runTimeMs;
	}
	
	public static void main(String[] args) throws Exception {
		PerfCheck10KSeq test = new PerfCheck10KSeq();
		boolean chk = test.execute();
		System.out.println("chk = " + chk +", run time: " + test.getRunTime());
	}

}
