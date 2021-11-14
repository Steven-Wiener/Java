/**
 * @author Steven Wiener
 * @Version 1
 * @Description This program simulates a hangman game between the computer and a user.
 */

import java.util.Scanner;
import java.util.Arrays;

public class Hangman {
	/**
	 * This method starts the game
	 * @param word generated from generateWord method
	 * @param guessed generates string of "*" with same length as word
	 * @param missCount number of guesses missed
	 */
	public static void main(String[] args) {
		// Generate word and guessed
		char[] word = generateWord();
		char[] guessed = generateGuessed(word);
		
		// Create miss count
		int missCount = 0;
		
		// Start game
		nextTurn(word, guessed, missCount);
	}
	
	/**
	 * This method generates a word to be used in the game
	 * @param words Words to be used
	 * @param word Randomly chosen word
	 */
	public static char[] generateWord()	{
		// Add any words you wish in this array
		String[] words = {"write", "computer", "science", "program", "coding"};
		
		// Take word from list to use
		char[] word = (words[(int)(Math.random() * words.length)]).toCharArray();
		
		return word;
	}
	
	/**
	 * This method generates a replacement word ("***") to be used in the game
	 * @param guessed "***" word with length of word variable
	 */
	public static char[] generateGuessed(char[] word)	{
		// Create guessed word, initialize with asterisks
		char[] guessed = new char[word.length];
		
		for (int i = 0; i < guessed.length; i++)	{
			guessed[i] = '*';
		}
		
		return guessed;
	}
	
	/**
	 * This method contains the bulk of the game, and repeats with every new turn
	 * @param letter Inputted letter guessed by user
	 * @param guessed Guessed word generated previously
	 * @param word Word to be guessed
	 * @param missCount Count of times user has missed
	 */
	public static void nextTurn(char[] word, char[] guessed, int missCount)	{
		// Create Scanner object
		Scanner in = new Scanner(System.in);
		
		// Prompt user for letter
		System.out.print("\n(Guess) Enter a letter in word " + Arrays.toString(guessed) + " > ");
		
		// Store letter
		char letter = in.next().toLowerCase().charAt(0);
		
		// Check if already in word or not in word, print out appropriate response
		if (alreadyInWord(letter, guessed))	{
			System.out.print("       " + letter + " is already in the word");
			nextTurn(word, guessed, missCount);
		}
		else if (notInWord(letter, word))	{
			System.out.print("       " + letter + " is not in the word");
			missCount += 1;
			nextTurn(word, guessed, missCount);
		}
		else	{
			for (int i = 0; i < guessed.length; i++)	{
				if (letter == word[i])	{
					guessed[i] = letter;
				}
			}
			
			if (Arrays.equals(guessed, word))	{
				win(word, missCount);
			}
			else	{
				nextTurn(word, guessed, missCount);
			}
		}
		
		// Close in
		in.close();
	}
	
	/**
	 * This method checks whether a letter has been guessed already
	 * @param letter Inputted letter
	 * @param guessed Letters/word guessed so far
	 */
	public static boolean alreadyInWord(char letter, char[] guessed)	{
		for (int i = 0; i < guessed.length; i++)	{
			if (letter == guessed[i])	{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method checks whether a letter is in the word or not
	 * @param letter Inputted letter
	 * @param word Word selected at beginning of game
	 */
	public static boolean notInWord(char letter, char[] word)	{
		for (int i = 0; i < word.length;i++)	{
			if (letter == word[i])	{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method starts the endgame routine and prompts whether the user would like to restart
	 * @param word Word selected at beginning of game
	 * @param guessed Letters/word guessed so far
	 * @param missCount Count of how many times the user has missed
	 * @param decision Whether or not the user would like to continue
	 */
	public static void win(char []word, int missCount)	{
		// Create Scanner object
		Scanner in = new Scanner(System.in);
		
		System.out.println("The word is " + Arrays.toString(word) + ". You missed " + missCount + " time(s)");
		System.out.print("Do you want to guess another word? Enter a y or n > ");
		
		char decision = in.next().toLowerCase().charAt(0);
		
		if (decision == 'y')	{
			word = generateWord();
			char[] guessed = generateGuessed(word);
			missCount = 0;
			nextTurn(word, guessed, missCount);
		}
		
		// Close in
		in.close();
	}
}
