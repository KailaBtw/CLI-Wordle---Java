package edu.unca.csci201;
/**
 * This class is meant to handle the word set for answers and allowed words in wordle.
 * The class can also handles selecting a hidden word. 
 *  
 * @author Ellie Lagrave
 */
public class Main {

	public static void main(String[] args) {
		
		Wordle wordle = new Wordle();
		wordle.play();	
		
		

	}
}


/*
 * Testing Println Methods below
 */

//WordList words = new WordList("answerlist.txt");
//System.out.println("word is in list: " + words.inList("Happy"));
//System.out.println("list length: " + words.size());
//System.out.println("get word: " + words.getWord());