package test;

import java.io.PrintWriter;

/**
 * Unit & performance test suite for 
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public class Suite {
	
	public boolean runUtest(IUnitTest test, PrintWriter pw) {
		boolean st = test.execute();
		String status = st ? "PASS" : "FAIL";
		pw.printf("%-20s :   %s", test.testName(), status);
		pw.println();
		pw.flush();
		return st;
	}
	
	public boolean runPerfTest(IPerfTest test, PrintWriter pw) {
		boolean st = test.execute();
		long runTime = test.getRunTime();
		String execStatus = st ? "PASS" : "FAIL";
		String perfStatus = test.isPerfOk() ? "OK" : "DEGRADATION!";
		pw.printf("%-20s :   %s, time elapsed : %s ms (%s)", test.testName(), execStatus, runTime, perfStatus);
		pw.println();
		pw.flush();
		return st;
	}
	
	public boolean runUtests(PrintWriter pwStatus) {
		boolean ret = true;
		ret &= runUtest(new SeqLimits0(), pwStatus);
		ret &= runUtest(new SeqLimits1(), pwStatus);
		ret &= runUtest(new SeqLimits2(), pwStatus);
		ret &= runUtest(new SeqLimits3(), pwStatus);
		ret &= runUtest(new SeqLimits4(), pwStatus);
		ret &= runUtest(new SeqLimits5(), pwStatus);
		ret &= runUtest(new SeqLimits6(), pwStatus);
		return ret;
	}

	public boolean runPerfTests(PrintWriter pwStatus) {
		boolean ret = true;
		ret &= runPerfTest(new PerfCheck10KSeq(), pwStatus);
		ret &= runPerfTest(new PerfCheck50K(), pwStatus);
		ret &= runPerfTest(new PerfCheck100K(), pwStatus);
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		//NB: item# starts from 0
		Suite suite = new Suite();
		PrintWriter defaultPrintWriter = new PrintWriter(System.out);
		boolean success = suite.runUtests(defaultPrintWriter);
		success &= suite.runPerfTests(defaultPrintWriter);
		String status = success ? "Suite ran SUCCESSFULY" : "Suite FAILED";
		defaultPrintWriter.println();
		defaultPrintWriter.println(status);
		defaultPrintWriter.close();
	}

}
