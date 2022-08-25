package ie.gmit.sw;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Runner {
		// stores encrypted and decrypted values
		private static String answer;
		// Text to be encrypted
		private static String text = "happy days!";
		// Key
		private static int key = 6;
		// List to store attempts using different keys 
		private List <Future<String>> resultList = new ArrayList<>();
		// user input
		private static int userInput;
		// Mapfile object
		static MapFile mf = new MapFile();
		// Occurrence object
		private static Occurence Oc =  new Occurence();
		
		//CHi
		private Map <Future<Double>, Integer> resultChiListMap = new ConcurrentHashMap<>(); 
		
		// My Chi-squared attempt - WIP - Currently not returning the <future> values into CHM
		public void goChi() throws InterruptedException{	
			// Map to store chars and probable occurrence values
			Map<Character, Double> expectedTest = new HashMap<>();
			// Map to store encrypted characters and their occurrences
			Map<Character, Integer> occurrencesTest = new HashMap<>();
			
			// populating local map with decrypted characters and their occurences
			occurrencesTest = Oc.characterCount(answer);
			// populating local map with ASCII chars and their probable occurrence values
			expectedTest = mf.mapTheFile();
			
			// New Thread pool
			ExecutorService es = Executors.newFixedThreadPool(5);
			
			// This will test for 10 shifts, we could bump the number higher here to check further shift values
			for(int i = 0; i<10; i++)
			{
				// Threads at work
				Mapper attempts = new Mapper(i, answer);
				Future<String> res = es.submit(attempts);
				resultList.add(res);
			}
			
			// Waiting for all threads to finish
			es.awaitTermination(3, TimeUnit.SECONDS);
			
			// Future values received, loop through and display
			for(int i = 0; i < resultList.size(); i++)
			{
				Future<String> resu = resultList.get(i);
				String myString = "";
				
				try {
					myString = resu.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				System.out.println("Shifted " + i + " : " + myString);
			}
			System.out.println("\n ** Did we crack it? **");
			
			// Shutting down executor service
			es.shutdown();
		}
		
		public static void main(String[] args) throws InterruptedException, FileNotFoundException {
			// Map to store chars and probable occurrence values
			Map<Character, Double> expected = new HashMap<>();
			// Map to store encrypted characters and their occurrences
			Map<Character, Integer> occurrences = new HashMap<>();
			// Scanner
			Scanner scan = new Scanner(System.in);
			
			// random number gen for member ID
			int randomKey = ThreadLocalRandom.current().nextInt(1, 9+1);
			// System.out.println(randomKey);
			key = randomKey;
			
			System.out.println("1) Enter text to encrypt");
			System.out.println("2) Decrypt the text");
			System.out.println("3) Show Mapped mono file and encrypted character occurrences");
			System.out.println("4) Please enter file path to cypher text you wish to decrypt");
			System.out.println("6) Chi square statistic decrypt - WIP, return values not working");
			userInput = scan.nextInt();
			
			do
			{
				if(userInput == 1)
				{
					System.out.println("Please enter a sentance to encrypt");
					scan.nextLine();
					text = scan.nextLine();
					
					// Display the now encrypted sentance 
					Encrypting en = new Encrypting(key, text);
					answer = en.getCracking();
					System.out.println("This is your new ecrypted sentance: " + answer);
				}
				else if(userInput == 2)
				{
					// Ceaser Cypher
					new Runner().go();
				}
				else if(userInput == 3)
				{
					// creating a new Encrypted object, passing the key and message
					Encrypting en = new Encrypting(key, text);
					answer = en.getCracking();
					
					// populating local map with decrypted characters and their occurences
					occurrences = Oc.characterCount(answer);
					// populating local map with ASCII chars and their probable occurrence values
					expected = mf.mapTheFile();
					
					System.out.println("Mapped mono file: " + expected);
					System.out.println("\nThis the is the ecrypted message: " + answer);
					System.out.println("\nDecrypted Character occurrences: " + occurrences); 
				}
				else if(userInput == 4)
				{
					// User types in file path which contains cypher text
					// My example, I enter: ../OOP_FinalProj/cypher.txt
					System.out.println("Please enter the path");
					scan.nextLine();
					String userEntry = scan.nextLine();
					
					String fileAns = mf.readFile(userEntry);
					System.out.println("Cypher code received from file: " + fileAns);
					// passing the new string which we want to decrypt
					answer = fileAns;
					new Runner().go();
				}
				// Intentionally put this after the "exit" option, just because it's not completely working as intended
				else if(userInput == 6)
				{
					// Chi Sqaure Cypher
					new Runner().goChi();
				}
				// Repeat menu
				System.out.println("\n1) Enter text to encrypt");
				System.out.println("2) Decrypt the text");
				System.out.println("3) Show Mapped mono file and encrypted character occurrences");
				System.out.println("4) Please enter file path to cypher text you wish to decrypt");
				System.out.println("5) Quit");
				System.out.println("6) Chi square statistic decrypt - WIP, return values not working");
				userInput = scan.nextInt();
			
			}while(userInput != 5);
			
			// User has opted to leave, close the scanner and display a goodbye message
			System.out.println("Thank you and goodbye");
			scan.close();
		}

		private void go() {
			// TODO Auto-generated method stub
			
		}
			
}