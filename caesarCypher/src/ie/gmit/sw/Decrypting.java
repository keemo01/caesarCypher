package ie.gmit.sw;

public class Decrypting extends ChiCypher {

	private int key;
	private String sentences;
	
	//Constructor
	public Decrypting(int key, String sentence) {
		super();
		this.key = key;
		this.sentences = sentence;
	}
	
	
	@Override
	public String getCracking() {
		// TODO Auto-generated method stub
		String decrypted = "";
		//Stores characters
		char newestChar;
		// beginning position on ascii table
		int beginPosition;
		// New position after
		int newestPosition;
	
		// Loops through the sentence
		for (char oneChar : sentences.toCharArray()) {
			// 32 is start of ascii table
			beginPosition = oneChar - 32;
			// This time we minus the key
			newestPosition = (beginPosition - key);
			// Ensuring we don't go outside of scope on table
			// Above min
			if (newestPosition >= 0) {
				// 32 is the start of table
				newestChar = (char) (newestPosition + 32);
			} else {
				// 127 is end of table
				newestChar = (char) (newestPosition + 127);
			}
			// append onto new string
			decrypted += newestChar;
		}
		return decrypted;
	}

}