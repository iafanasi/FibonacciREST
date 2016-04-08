package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.emc.fibonacci.engine.PrintableFibonacciSequence;

/**
 * Performance test to assure that 1st 100000 fibonacci numbers are
 * getting calculated correct in less than 5 seconds
 * (actually, 2.4 seconds is average for this test)
 * <p>
 * NOTICE: full sequence is NOT printer out here, only 100000th number
 * </p>
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public class PerfCheck100K implements IPerfTest {
	
	private static final String REF_FNAME   = "100000.txt";
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
			seq.setLimits(100000, 100000);
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
		// NOTE: this test calculates the whole Fibonacci sequence starting from 1 till 100000,
		// so 5 seconds should be quite acceptable for this amount of calculations
		return runTimeMs < 5000;
	}

	public long getRunTime() {
		return runTimeMs;
	}
	
	public static void main(String[] args) throws Exception {
		PerfCheck100K test = new PerfCheck100K();
		boolean chk = test.execute();
		System.out.println("chk = " + chk +", run time: " + test.getRunTime());
	}
	

}
