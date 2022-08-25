package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


/**
* @author Akeem Jokosenumi
* @version 1.0
* @since 1.8
* 
* MapFile class, this class handles decryption requests
* Implements the Callable interface
* Once decryption is complete, it will return a future String, which populates a List in runner
*/

public class MapFile {

	private String filePath = "src/ie/gmit/sw/monograms-ASCII-32-127.txt";
	private double probability;
	private HashMap<Character, Double> map = new HashMap<>();
	
	// After the monogram file has been parsed and probabilities have been assigned (and divided by 100d), this method will return a HashMap.
	
	public HashMap<Character, Double> mapTheFile() {
		BufferedReader br = null;
		
		HashMap<Character, Double> mappedFile = new HashMap<>();

		try {
			// Making a new file object
			File file = new File(filePath);
			// Buffered reader to read the text files
			br = new BufferedReader(new FileReader(file));
			// Variable just to store values
			String line = null;

			// This code will be used to Read the file line by line
			while ((line = br.readLine()) != null) {
				if (line.startsWith(",")) continue;
				String[] fragments = line.split(",");

				// Assigns both values
				String theCharacters = fragments[0].trim();
				String value = fragments[1].trim();

				if (!theCharacters.equals("") && !value.equals("")) {
					// dividing the double entries by 100 to get their probable values
					probability = Double.parseDouble(value);
					map.put(theCharacters.charAt(0), probability/100d);
				}
			}
			// I skipped the ',' value as it would break the reader, had to add manually at end
			map.put(',', 0.00323418/100d);
			// Need to include a value for space also
			map.put(' ', 0.00189169/100d);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mappedFile = map;
		// returns a copy of the mapped file
		return mappedFile;
	}
	
	// From a local file, this function will return a string. A directory path to a local file MUST be entered by the user.
	/**
	 * A local file is read by this function.
	 * @param The user-entered string, filePath, should be a path directory string.
	 * @return returns a new String of the file contents
	 * @throws FileNotFoundException Exception should file not be found
	 */
	public String readFile(String filePath) throws FileNotFoundException {
		
		// ../OOP_FinalProj/cypher.txt - was my input on console when option 4 is pressed
		
		StringBuilder bobBuilder = new StringBuilder();
		
		String currentLine = "";
		
		try(BufferedReader br = new BufferedReader(new FileReader(filePath)))
		{
			while((currentLine = br.readLine()) != null)
			{
				bobBuilder.append(currentLine);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return whole file as a string
		return bobBuilder.toString();	
	}
}