package test;

/**
 * Interface class setting the wrapping rules for simple yes/no test.
 * Inherit from it while creating another performance test.
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public interface IUnitTest {
	public String testName();
	public boolean execute();
}
