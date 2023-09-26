package edu.unca.csci201;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
/**
 * This class is meant to handle the word set for answers 
 * 	and allowed words in wordle.
 * The class can also handles selecting a hidden word. 
 *  
 * @author Ellie Lagrave
 */
public class WordList {

	private int lines;
	private File wordFile;
	private Scanner fileScanner;
	private String[] words;


	/**<pre>
	 * Constructor for the WordList Object.
	 * 
	 * Given the fileName, the constuctor loads it in with the 
	 * file class, determines the line length of the file, 
	 * creates a string array of the same line length as the 
	 * file, and reads the lines with scanner and puts 
	 * each line into the slots of the array
	 * </pre>
	 * @param fileName String file name to load answers, 
	 * 	includes file extensions in the name.
	 */
	public WordList(String fileName) {

		//open our file into File class
		wordFile = new File(fileName);

		//find length (line count) of file
		Path path = Paths.get(fileName);
		try {
			lines = (int) Files.lines(path).count();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		//make our array to store the file values
		words = new String[lines];
		try {
			fileScanner = new Scanner(wordFile);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < lines; i++) {
			words[i] = fileScanner.nextLine();
		}
		//System.out.println(words[50]);
	}

	/**
	 * Returns the number of words in the WordList.
	 * 
	 * @return int size of word list
	 */
	public int size() { //tested working
		return lines;
		//System.out.println("length of file is " + lines);
	}

	/**
	 * Returns the word at given index in the WordList
	 * 
	 * @param index int from 0 to size()-1
	 * @return String word at that index
	 */
	public String getWord(int index) { //tested working
		return words[index].toUpperCase();
	}

	/**
	 * Helper method to get the array index value of the input string 
	 * 	as it exists within the wordList word arrays.
	 * 
	 * @param str to look for
	 * @return int index of the str in array
	 */
	public int getIndex(String str) {
		for (int i=0; i < lines; i++) {
			if (words[i].toUpperCase().equals(str.toUpperCase())) {
				return i;
			}
		}
		return -1; //fatal error
	}
	
	/**
	 * Method to modify the word array and change one element of it
	 * 
	 * @param newWord String value to put inside the array slot
	 * @param index of the array to place newWord into
	 */
	public void setWord(String newWord, int index) {
		words[index] = newWord;
	}
	
	/**
	 * Returns a word at a random index in the WordList
	 * @return String hiddenword
	 */
	public String getWord() { //tested working
		Random rand = new Random();
		return words[rand.nextInt(lines)].toUpperCase();
	}

	/**
	 * Helper method to return the private String[] word list 
	 * for use by other classes
	 * 
	 * @return String[] wordList
	 */
	public String[] getWordList() {
		return words;
	}
	
	/**
	 * Test to see if parameter string is in our array of words
	 * 
	 * @param word String to be tested
	 * @return boolean true if in list, false if not
	 */
	public boolean inList(String word) { //tested working
		for (int i=0; i < lines; i++) {
			if (words[i].toUpperCase().equals(word.toUpperCase())) {
				return true;
			}
		}
		return false;
	}

}
