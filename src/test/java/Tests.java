import org.junit.Test;
import test.*;
import static org.junit.Assert.assertEquals;

public class Tests {
   void testRun(IUnitTest test, String name) {
     boolean ret = test.execute();
     assertEquals(ret, true);
     System.out.println("Unit test '" + name + "': ret = " + ret);
   }
   void testRun(IPerfTest test, String name) {
     boolean ret = test.execute();
     assertEquals(ret, true);
     System.out.println("Performance test '" + name + "': ret = " + ret);
   }

   @Test
   public void testSeqLimits0() {
     testRun(new SeqLimits0(), "testSeqLimits0");
   }
   @Test
   public void testSeqLimits1() {
     testRun(new SeqLimits1(), "testSeqLimits1");
   }
   @Test
   public void testSeqLimits2() {
     testRun(new SeqLimits2(), "testSeqLimits2");
   }
   @Test
   public void testSeqLimits3() {
     testRun(new SeqLimits3(), "testSeqLimits3");
   }
   @Test
   public void testSeqLimits4() {
     testRun(new SeqLimits4(), "testSeqLimits4");
   }
   @Test
   public void testSeqLimits5() {
     testRun(new SeqLimits5(), "testSeqLimits5");
   }
   @Test
   public void testSeqLimits6() {
     testRun(new SeqLimits6(), "testSeqLimits6");
   }
   @Test
   public void testPerfCheck10Kseq() {
     testRun(new PerfCheck10KSeq(), "testPerfCheck10Kseq    ");
   }
   @Test
   public void testPerfCheck50KSingle() {
     testRun(new PerfCheck50K(),    "testPerfCheck50Ksingle ");
   }
   @Test
   public void testPerfCheck100KSingle() {
     testRun(new PerfCheck100K(),   "testPerfCheck100Ksingle");
   }

}