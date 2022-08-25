package ie.gmit.sw;

public class Encrypting extends ChiCypher {
	
	private int key;
	private String sentences ;
	private String answer;
	
	//The Constructor
	public Encrypting(int key, String sentence) {
		super();
		this.key = key;
		this.sentences  = sentence;
	}
	
	// The Getter
	public String getAnswer() {
		return answer;
	}
	
	// The Setter
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String getCracking() {
		// TODO Auto-generated method stub
		String encrypted = "";
		char newestChar;
		int beginPosition;
		int newestPosition;
		
		//Looping through sentence
		for (char oneChar : sentences.toCharArray()) {
			
			// The beginning of the ascii table is number 32
			beginPosition = oneChar - 32;
			//Adding the x amount to the shift
			newestPosition = (beginPosition + key);
			// cast a char and store
			newestChar = (char) (32 + newestPosition);
			// add it all to the new String
			encrypted += newestChar;
			
		}
		return encrypted;
	}

}
