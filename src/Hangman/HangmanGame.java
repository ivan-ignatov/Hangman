package Hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HangmanGame {

	private static Scanner sc = new Scanner(System.in);
	private static String guessedCharacter;
	private static String wrongCharacter;
	private static int guessTries;

	public static void getWord() throws FileNotFoundException {

		guessedCharacter = "";
		wrongCharacter = "";
		guessTries = 7;
		int numberOfLines = 0;

		File textFile = new File("res/HangmanGameWords.txt");
		Scanner fileScanner = new Scanner(textFile);

		while (fileScanner.hasNextLine()) {
			fileScanner.nextLine();
			numberOfLines++;
		}

		fileScanner = new Scanner(textFile);
		for(int i = 0; i < (int)(Math.random() * numberOfLines); i++) {
			fileScanner.nextLine();
		}

		String word = fileScanner.nextLine();
		guessedCharacter = guessedCharacter + word.charAt(0) + word.charAt(word.length() - 1);

		firstPrint(word);
		mainInput(word);
	}

	public static void print(String word) throws FileNotFoundException {
		int endGameFlag = 0;

		System.out.print("The current word is: ");
		for(int i = 0; i < word.length(); i++) {
			if(guessedCharacter.contains(Character.toString((word.charAt(i))))) {
				System.out.print(word.charAt(i) + " ");
			} else {
				System.out.print("_ ");
				endGameFlag = 1;
			}
		}

		System.out.println();

		if(endGameFlag == 0) {
			System.out.println("You have guessed the word " + word + " with " + guessTries + " lives remaining. ");
			gameOver();
		} else {
			mainInput(word);
		}
	}

	public static void firstPrint(String word) {
		System.out.print("The current word is: " + word.charAt(0) + " ");
		for(int i = 1; i < word.length() - 1; i++) {
			if(guessedCharacter.contains(Character.toString((word.charAt(i))))) {
				System.out.print(word.charAt(i) + " ");
			} else {
				System.out.print("_ ");
			}
		}
		System.out.println(word.charAt(word.length() - 1));
	}

	public static void gameOver() throws FileNotFoundException {
		System.out.print("GAME OVER\nWould you like to play again? -yes/no: ");
		String programRepetition = sc.nextLine();

		if(programRepetition.equals("no")) {
			System.exit(0);
		} else {
			System.out.println("You have started a new game. ");
			guessedCharacter = "";
			guessTries = 5;
			getWord();
		}
	}

	public static void lifeCheck(String givenWord) throws FileNotFoundException {
		if(guessTries > 0) {
			System.out.println("You have " + guessTries + " lives remaining.");
		} else {
			System.out.println("You have 0 lives remaining. You have lost the game.\nThe word was " + givenWord + ". ");
			gameOver();
		}
	}

	public static void mainInput(String givenWord) throws FileNotFoundException {
		lifeCheck(givenWord);

		System.out.print("\nEnter your guess: ");
		String guess = sc.nextLine();

		if(guess.length() == 1){
			characterGuess(givenWord, guess);
		} else {
			wordGuess(givenWord, guess);
		}
	}

	public static void wordGuess(String givenWord, String guess) throws FileNotFoundException {
		if(givenWord.equals(guess)) {
			System.out.println("You have guessed the word " + givenWord + " with " + guessTries + " lives remaining. ");
			gameOver();
		} else {
			System.out.println("Your guess is not the right word.");
			guessTries--;
			print(givenWord);
			mainInput(givenWord);
		}
	}

	public static void characterGuess(String givenWord, String guess) throws FileNotFoundException {
		if (wrongCharacter.contains(guess)) {
			System.out.println("This character has already been guessed as a wrong answer.");
			print(givenWord);
		} else if(guessedCharacter.contains(guess)) {
			System.out.println("This character has already been shown.");
			print(givenWord);
		} else if(givenWord.contains(guess)) {
			guessedCharacter += guess;
			System.out.println("You have guessed the character " + guess + ". ");
			print(givenWord);
		} else {
			System.out.println("The word does not contain the character " + guess + ". ");
			wrongCharacter += guess;
			guessTries--;
			print(givenWord);
			mainInput(givenWord);
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Welcome to the game of Hangman.\n"
				+ "The program will read and take a random word from the file HangmanGameWords.txt.\n"
				+ "You have 7 tries to guess either the random word or a character from it.\n"
				+ "Have fun!\n");

		getWord();
	}
}
