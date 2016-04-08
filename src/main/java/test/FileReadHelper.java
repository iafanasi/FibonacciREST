package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author Ilya Afanasiev (ilya.afanasiev@gmail.com)
 */
public class FileReadHelper {
    public static byte[] readAllBytes(String fname) throws IOException {
    	ClassLoader classLoader = FileReadHelper.class.getClassLoader();
    	InputStream is = //new FileInputStream(file);
    			classLoader.getResourceAsStream(fname);
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] chunk = new byte[16384];
		while ((nRead = is.read(chunk, 0, chunk.length)) != -1) {
		  buffer.write(chunk, 0, nRead);
		}
		buffer.flush();
		return buffer.toByteArray();    
    }

}
