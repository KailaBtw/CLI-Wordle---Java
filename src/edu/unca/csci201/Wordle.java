package edu.unca.csci201;

import java.util.Scanner;  // Import the Scanner class
import java.util.Random;

public class Wordle {
	//instance methods
	private String hiddenWord;
	private boolean win; //true if user guess word
	private Scanner scan;
	
	//constructor
	public Wordle()  {
		win = false;
		
		Random rand = new Random();
		int randomIndex = rand.nextInt(3);
		
		switch (randomIndex) {
		case 0: 
			hiddenWord = "HELLO";
			break;
		case 1: 
			hiddenWord = "WRONG";
			break;
		case 2: 
			hiddenWord = "HAPPY";
			break;
		}
	}
	
	//getter/setter
	
	
	
	
	
	//instance methods

	private String getGuess() {
		//todo verify input type constraints
		scan = new Scanner(System.in);
		System.out.print("Enter your guess: ");
		String guess = scan.nextLine();
		return guess;
	}
	
	private String evaluateGuess(String guess) {
		//compare hidden word to guess string using equals() method
		
		//System.out.println("Guess: " + guess);		
		//System.out.println("Hidden Word: " + hiddenWord);	
		
		String code = "";
		
		//make a blank string and add each result to it to "build" the result string
		code+=evaluateNthLetter(guess,0);
		code+=evaluateNthLetter(guess,1);
		code+=evaluateNthLetter(guess,2);
		code+=evaluateNthLetter(guess,3);
		code+=evaluateNthLetter(guess,4);
		System.out.println(code);
		return code;
	}
	
	private String evaluateNthLetter(String guess, int n) {
		String letter = guess.substring(n,n+1);
		//System.out.println("evaluating" + letter);
		if (letter.equals(hiddenWord.substring(n,n+1))) {
			return "G";
		}
		//String.indexOf() returns the index of the first occurance of letter
		if (hiddenWord.indexOf(letter) != -1) {
			return "Y";
		}
		return "_";
	}
	

	private void takeTurn() {
		String guess = getGuess();
		String code = evaluateGuess(guess);
		if (code.equals("GGGGG")) {
			win = true;
		}
	}
	
	
	
	
	public void play() {
		for (int i = 0; i < 5; i++) {
			takeTurn();
			if (!win) {
				takeTurn();
			} else {
				System.out.println("You win!");
			}
		}
	}
	
	
	
	
	
	
	
	
	
}
