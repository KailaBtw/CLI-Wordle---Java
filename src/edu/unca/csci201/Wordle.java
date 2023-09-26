package edu.unca.csci201;

import java.util.Scanner;

/**
 * This class is the main wordle game class handling input and directing tasks to other classes.
 * 
 * See: Pro
 *  
 * @author Ellie Lagrave
 */
public class Wordle {

	private Scanner scan;
	private int guessCounter = 1;
	private WordList answerList;
	private WordList allowedList;
	private WordList letterList;
	private HiddenWord hiddenWord;
	String[] greenSet = new String[26];
	String[] yellowSet = new String[26];
	private final String YELLOW_BACKGROUND = "\u001b[43m";
	private final String GREEN_BACKGROUND = "\u001B[42m";
	private final String BACKGROUND_RESET = "\u001b[0m";//white
	
	/**
	 * Wordle Object Constructor. Also generate the wordlist from a file.
	 */
	public Wordle()  {
		answerList = new WordList("answerlist.txt");
	}
	
	/**
	 * Sequence of actions to take when the Wordle game is started (greeting messages, mode selection, etc)
	 */
	private void startUpSequence() {
		System.out.println(GREEN_BACKGROUND 
				+ "Welcome to my Wordle Clone!" 
				+ BACKGROUND_RESET + "\r\n\n"
				+ "Press enter to play with a random word,\r\n"
				+ "or enter a nonnegative integer to pick that word from the list:");
		
		//hiddenWord.win = false; //set win false each round at the start
		scan = new Scanner(System.in);
		allowedList = new WordList("wordlist.txt");
		letterList = new WordList("letterList.txt"); //array with a->z with 1 char in each slot
		
		String result = "";
		
		//create out inout validation for mode selection
		while (result.isEmpty()
				|| !onlyNumbers(result)) {
			
			result = scan.nextLine();

			//Enter is pressed (empty result)
			if (result.isEmpty()) { 
				System.out.println("playing with random word...\r\n");
				//create our hiddenword object containing HW variable, pass in wordlist
				hiddenWord = new HiddenWord(answerList.getWord());
				return;
			//check for only numbers to prevent parseInt exception
			} else if (!onlyNumbers(result)) {
				System.out.println("\r\nNot an integer, you may only input "
						+ "integer values between 0 and " + answerList.size() + "\r\n");
				result = "";
				
			//use parseInt to ensure the int is not longer than the array list
			} else if (Integer.parseInt(result) > answerList.size()) {
				System.out.println("\r\nInteger is too large, please pick "
						+ "an integer between 0 and " + answerList.size() + "\r\n");
				result = "";
				
			//now, use int to select hidden word
			} else {
				System.out.println("playing with word " + result + "...\r\n");
				//selecting ith word using mutated constructor
				hiddenWord = new HiddenWord(answerList.getWord(Integer.parseInt(result)));
				return;
			}
		}
	}
	
	/**
	 * Helper method to test input for only numbers to prevent Integer.parseInt exception
	 * 
	 * @param str string to be tested
	 * @return boolean true if only numbers, false if contains non-numeric characters
	 */
	private static boolean onlyNumbers(String str) {
		
		for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' 
            		|| str.charAt(i) > '9') {
                    return false;
                }
		}
		return true;
	}
	
	/**
	 * Method to start a Wordle match
	 */
	public void play() {
		
		startUpSequence();

		for (int i = 0; i < 7; i++) {
			if (guessCounter > 6) {
				System.out.println("\r\nSorry, you did not win.\r\n"  //working
						+ "The word was: " + hiddenWord.getSecretWord()); 
				return;
			}
			
			takeTurn();
			
			if (!hiddenWord.getWin()) {
				takeTurn();
			} else {
				System.out.println("\r\nCongratulations!\r\n"
						+ "You won in " + guessCounter + " turns!");
				return;
			} 
		}
	}
	
	/**
	 * Method to "take a turn" in wordle
	 * 	1. getGuess from the user (ensure valid input at this stage)
	 *  2. evaluate guess against the hidden word and get the result code
	 *  2a. check result code for win status (part of evaluate guess)
	 */
	private void takeTurn() {
		
		String guess = getGuess().toUpperCase();
		hiddenWord.evaluateGuess(guess);
		updateLetterSet(guess);
		printLetterSet();
	}
	
	/**
	 * Method to update greenSet and yellowSet arrays when the user makes a guess.
	 * greenSet stores any letters that the user guessed in the correct spot,
	 * 	they are stored at the ith cell determined by their place in the alphabet
	 * Same method with greenSet
	 * 
	 * @param guess String of the users guess word
	 */
	private void updateLetterSet(String guess) {
		

		
		for (int i = 0; i < guess.length(); i++) {
			String secretWord = hiddenWord.getSecretWord();
			
			//correct letter in correct position
			if (guess.charAt(i) == secretWord.charAt(i)) {
				int alphabetKey = letterList.getIndex(Character.toString(guess.charAt(i)));
				if (alphabetKey != -1) {
					greenSet[alphabetKey] = letterList.getWord(alphabetKey);
				}
			}
			
			//If letter is in the hidden word, but wrong position
			if (secretWord.indexOf(guess.charAt(i)) != -1) {
				int alphabetKey = letterList.getIndex(Character.toString(guess.charAt(i)));
				if (alphabetKey != -1) {
					yellowSet[alphabetKey] = letterList.getWord(alphabetKey);
				}
			}
			
			
		}
		
		
	}
	
	/**
	 * Method to print the letter set A->Z but also colorize greenSet and yellowSet values
	 */
	private void printLetterSet() {
		for (int i = 0; i < letterList.size(); i++) {
			if (greenSet[i] != null) {
				System.out.print(GREEN_BACKGROUND + greenSet[i] + BACKGROUND_RESET);
			} else if (greenSet[i] == null && yellowSet[i] != null ) {
				System.out.print(YELLOW_BACKGROUND + yellowSet[i] + BACKGROUND_RESET);
			} else {
				System.out.print(letterList.getWord(i));
			}
		}
		System.out.println("\r\n");
	}
	
	/**
	 * Method to get a Guess from the user in the Wordle, error checking happens here for inputs.
	 * Also checks to ensure the user guess is in our allowed word list
	 * 
	 * @return String guess from the user
	 */
	private String getGuess() {
		
		//todo verify input type constraints
		scan = new Scanner(System.in);
		String guess = "";
		
		//Input validation while loop
		while (guess == null 
				|| guess.isEmpty()
				|| guess.isBlank()
				|| guess.length() != 5
				|| !allowedList.inList(guess)) {
			
			//Only show error message when there is a guess String, but its not valid.
			//Dont show error message on the first turn when guess is empty
			if(!guess.toUpperCase().equals("SHOW") 
					&& !guess.toUpperCase().equals("HINT") 
					&& !guess.isEmpty()) {
				if (allowedList.inList(guess)) {
					System.out.println("Invalid guess. Please try again.\r\n");
				}
			//on invalid word, tell user to pick a better (allowed) word
				if (!allowedList.inList(guess)) {
					System.out.println("This is not an allowed word. Please try again.\r\n");
				}
			}
			//prompt for guess
			System.out.println("Please enter guess number " + guessCounter + ":");
			guess = scan.nextLine().toUpperCase();
			//check for "show" inside loop
			if (guess.toUpperCase().equals("SHOW")) { 
				System.out.println(hiddenWord.getSecretWord());
			}
			//check for "hint" inside loop
			if (guess.toUpperCase().equals("HINT")) { 
				generateHint();
			}
		}
		
		//now print result
		System.out.println(guess);
		guessCounter++;
		return guess;
	}
	
	/**
	 * Method to generate and display a hint for the player
	 * in this context a hint will be the wordList string that most closely
	 * resembles the hiddenWord (but is not the hiddenWord) i.e. the one with the 
	 * most G and Y tiles.
	 */
	private void generateHint() {
		
        String optimalGuess = "";
        int optimalGuessLetterCount = 0;
        
        for (String word : answerList.getWordList()) {
            if (word.toUpperCase().equals(hiddenWord.getSecretWord())) {
                continue; // Skip the hidden word
            }
            int letterCount = 0;
            for (char c : word.toUpperCase().toCharArray()) { 
            	if (hiddenWord.contains(c)) {
	            	letterCount++;
	        	}
            }
            if (letterCount > optimalGuessLetterCount) {
            	optimalGuess = word;
            	optimalGuessLetterCount = letterCount;
            }
        }
        System.out.println("Optimal Guess is: " + optimalGuess.toUpperCase());
	}

}
