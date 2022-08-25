package ie.gmit.sw;

import java.util.*;
import java.util.concurrent.Callable;


public  class ChiSquared implements Callable<Double>{

	private String cryptedFile;
	private int key;
	Map<Character, Integer> occurrences;
	Map<Character, Double> expected;
	
	// Constructor
	public ChiSquared(int key, String cryptedFile, Map<Character, Double> expected,
			Map<Character, Integer> occurrences) {
		super();
		this.key = key;
		this.cryptedFile = cryptedFile;
		this.expected = expected;
		this.occurrences = occurrences;
	}
	
	
	@Override
	public Double call() throws Exception {
		// TODO Auto-generated method stub
		double result = 0.0;
		double calculatedValue;		
		double expectedValue;
		double occurenceValue;
		double total;
		
		for (Character c : occurrences.keySet())
		{
			// Calculation to get the key
			expectedValue = expected.get(c);
			occurenceValue = occurrences.get(c);
			calculatedValue = expectedValue * cryptedFile.length();
			total = (occurenceValue - calculatedValue) * (occurenceValue - calculatedValue) / calculatedValue;
			result += total;
			
			//Print out value
			System.out.println("Value: " + result);
						
		}
		
		return result;
	}
}
