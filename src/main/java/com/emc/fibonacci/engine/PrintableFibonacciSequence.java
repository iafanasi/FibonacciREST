package com.emc.fibonacci.engine;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import com.emc.fibonacci.logging.CustomLogger;

/**
 * <p>
 * Printable fibonacci sequence. Printout happens during the sequence calculation process.
 * </p>
 * <p>
 * Capability to calculate way long fibonacci numbers is provided by representing
 * a numeric sequence in the form of sequential integer arrays.
 * Each fibonacci number is split onto groups of 18 digits length, and each such
 * group is "lossless packed" into a single <code>long</code> value.
 * </p>
 * <p>
 * Implementation keeps container of 2500 <code>long</code>'s (20K) to hold each fibonacci
 * number. Suggested approach wastes bytes for storing initial fibonacci numbers, for the
 * payoff cost of quite acceptable computation speed for large numbers (10000+). 
 * </p>
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public class PrintableFibonacciSequence {

	private int    partsNum            = 2500; //enough for first 200'000 numbers
	private long   maxPartVal          = 999999999999999999L;
	private long   leastValMaxLength   = 100000000000000000L;
	private long   mod                 = 1000000000000000000L;
	private long[] partsCurr  = new long[partsNum]; 
	private long[] partsPrev  = new long[partsNum]; 
	private long[] partsNew   = new long[partsNum]; 
	
	private int seqFrom;
	private int seqTo;
	private Logger logger = CustomLogger.getInstance();

	/**
	 * <p>
	 * Initializes fibonacci sequence.
	 * </p>
	 * <p>
	 * A classic fibonacci seqience is assumed to exist, with each number in a sequence
	 * having its own <b>ordinal</b>, starting from 0. Parameters below are setting upper & lower
	 * bounds for sequence ordinals.
	 * </p>
	 * @param from ordinal for a fibonacci number from which PrintableFibonacciSequence instance should start 
	 * @param to ordinal for a fibonacci number at which PrintableFibonacciSequence instance should end
	 */
	public void setLimits(int from, int to) {
		seqFrom = from;
		seqTo   = to;
	}
	
	/**
	 * <p>
	 * Prints out a fibonassi sub-sequence.<br>It is assumed that sub-sequence's  start & end were set by
	 * {@link #setLimits setLimits(int from, int to)}.
	 * </p>
	 * 
	 * @param pw
	 * @throws IOException
	 */
	public void directPrint(PrintWriter pw) throws IOException {
		boolean printed;
		logger.entering(getClass().getName(), "directPrint(PrintWriter)");
		resetParts();//partsPrev = 0, partsCur = 1, partsNew = 0
		pw.print('[');
		printed = print(pw, partsPrev, 0);
		if(printed && seqTo > 0) pw.print(',');
		printed = print(pw, partsCurr, 1);
		for(int ordinal = 2; ordinal <= seqTo; ordinal ++ ) {
			if(printed) pw.print(',');
			add(partsCurr, partsPrev, partsNew);
			printed = print(pw, partsNew, ordinal);
			copy(partsCurr, partsPrev);
			copy(partsNew, partsCurr);
		}
		pw.print(']');
		logger.exiting(getClass().getName(), "directPrint(PrintWriter)");
	}
	
	/**
	 * Reset composite numbers involved in calculations to all-zeros
	 */
	private void resetParts() {
		for(int i = 0; i < partsNum - 1; i ++ ){
			partsNew[i]  = 0;
			partsPrev[i] = 0;
			partsCurr[i] = 0;
		}
		partsNew[partsNum - 1]  = 0;
		partsPrev[partsNum - 1] = 0;
		partsCurr[partsNum - 1] = 1;
	}
	
	/**
	 * Print-out a single composite number, digit-by-digit;
	 * 
	 * @param pw where to print
	 * @param parts composite number itself
	 * @param ordinal number's ordinal (avoid printing sequence numbers outside
	 * the requested range)
	 * @return <b>true</b> if composite number is printed, <b>false</b> otherwise
	 * (e.g. number is beyond the requested range)
	 * @throws IOException
	 */
	private boolean print(PrintWriter pw, long[] parts, int ordinal) throws IOException {
		if(ordinal < seqFrom || ordinal > seqTo) {
			return false;
		}
		int partId = partsNum - 1;
		boolean all0s = true, prevPartIs0 = true;
		for(partId = 0; partId < partsNum; partId ++) {
			if(parts[partId] == 0) {
				prevPartIs0 = true;
				continue;
			}
			all0s = false;
			if(!prevPartIs0 && parts[partId] < leastValMaxLength) {
				pw.print('0');
			}
			pw.print(parts[partId]);
			prevPartIs0 = false;
		}

		if(all0s) {
			pw.print('0');
		}
		return true;
	}

	/**
	 * Helper routine, copies items from <b>src</b> to <b>tgt</b> array
	 */
	private void copy(long[] src, long[] tgt)  {
		for(int i = 0; i < partsNum; i ++ ){
			tgt[i] = src[i];
		}
	}
	
	/**
	 * Sum-up two composite numbers represented as long arrays.
	 * Each item in corresponding array represents a part of composite number.
	 * <p>
	 * Summation is executed in an old-school way of per-digit summing with overflow,
	 * starting from least-rank digits.
	 * </p> 
	 * @param parts1 1st summand
	 * @param parts2 2nd summand
	 * @param partsSum summation result
	 */
	private void add(long[] parts1, long[] parts2, long[] partsSum) {
		int partId = partsNum - 1;
		long rem = 0L;
		for(partId = partsNum - 1; partId >= 0; partId --) {
			partsSum[partId] = rem;
			if(parts1[partId] == 0 && parts2[partId] == 0) {
				break;
			}
			long sum = parts1[partId] + parts2[partId];
			if(sum > maxPartVal) {
				partsSum[partId] += sum % mod; 
				rem = 1L;
			} else {
				partsSum[partId] += sum; 
				rem = 0L;
			}
			
		}
	}
	

}
