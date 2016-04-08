package test;

/**
 * Interface class setting the wrapping rules for performance test:
 * combination of yes/no test plus check against elapsed execution time.
 * Inherit from it while creating another performance test.
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public interface IPerfTest {
	public String testName();
	public boolean execute();
	public boolean isPerfOk();
	public long getRunTime();
}
