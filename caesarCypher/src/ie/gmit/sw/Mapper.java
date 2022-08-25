package ie.gmit.sw;

import java.util.concurrent.*;

/**
 * @author Akeem Jokosenumi
 * @version 1.0
 * @since 1.8
 * 
 * Mapper class, this class handles decryption requests
 * Implements the Callable interface
 * Once decryption is complete, it will return a future String, which populates a List in runner
 */

public class Mapper implements Callable<String> {
	
	// Private variables, assigned via constructor
	private int key;
	private String cryptedFile;
	
	// Constructor for the 'put maps to work' class
	public Mapper(int key, String cryptedFile) {
		super();
		this.key = key;
		this.cryptedFile = cryptedFile;
	}

	// Callable override, the threads will run this and return their attempts
	@Override
	public String call() throws Exception {
		Decrypting de = new Decrypting(key, cryptedFile);
		String ans = de.getCracking();
		return ans;
	}
}
