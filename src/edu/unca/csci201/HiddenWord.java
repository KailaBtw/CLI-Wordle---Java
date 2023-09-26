package edu.unca.csci201;

/**
 * This class is meant to handle the hidden word storage and evaluation
 *  
 * @author Ellie Lagrave
 */
public class HiddenWord {
	
	private boolean winStatus = false; //true if user guess word
	private String secretWord;
	private final String GREEN_BACKGROUND = "\u001B[42m";
	private final String YELLOW_BACKGROUND = "\u001b[43m";
	private final String BACKGROUND_RESET = "\u001b[0m";
	
	/**
	 * Constructor for the HiddenWord class, sets the private hiddenWord to the supplied String
	 * 
	 * @param hiddenWord String of the hiddenWord to play the Game with
	 */
	public HiddenWord(String hiddenWord) {
		this.secretWord = hiddenWord.toUpperCase();
	}
	
	/**
	 * build our result string by checking each letter of our guess
	 * 
	 * @param guess String of the user input guess
	 * @return String of the result (GGGGG, YY_YY, etc)
	 */
	public String evaluateGuess(String guess) {
		//compare hidden word to guess string using equals() method
		String code = "";
		
		//make a blank string and add each result to it to "build" the result string
		for (int i=0; i<5; i++) {
			code+=evaluateNthLetter(guess,i);
		}
		System.out.println("");
		if (code.equals("GGGGG")) {
			winStatus = true;
		}
		return code; //return isn't used
	}
	
	/**
	 * Check each individual character of the guess and compare it to the same letter in the hiddenWord
	 * 
	 * @param guess String of the user input guess
	 * @param n which letter we need to test
	 * @return
	 */
	private String evaluateNthLetter(String guess, int n) {
		//If the letters are the same
		if (guess.charAt(n) == secretWord.charAt(n)) {
			//print our result string to the screen but dont store ANSI color codes in code variable
			System.out.print(GREEN_BACKGROUND + "G" + BACKGROUND_RESET); 
			return ("G");
		}
		//If letter is in the hidden word, but wrong position
		//String.indexOf() returns the index of the first occurance of letter, -1 means no occurances
		if (secretWord.indexOf(guess.charAt(n)) != -1) {
			System.out.print(YELLOW_BACKGROUND + "Y" + BACKGROUND_RESET);
			return ("Y");
		}
		//Letter is not in String at all
		System.out.print("_");
		return "_";
	}
	
	/**
	 * Method to get the secret hidden word for other methods to use
	 * @return String hiddenWord
	 */
	public String getSecretWord() {
		return secretWord;
	}
	
	/**
	 * Method to check current winStatus
	 * @return true if win, false if has not won yet.
	 */
	public boolean getWin() {
		return winStatus;
	}
	
	/**
	 * toString method that returns the hiddenWord to the screen
	 */
	public String toString() {
		return "HiddenWord is: " + secretWord;
	}

	/**
	 * Method to check if a given char letter occurs in the secret hidden word
	 * 
	 * @param letter char to check for
	 * @return boolean true if in hiddenWord, false if not
	 */
	public boolean contains(char letter) {
		for (int i=0; i<5; i++) {
			if(secretWord.charAt(i) == letter) {
				return true;
			}
		}
		return false;
	}
	
	
	
}
