package hangman;

import hangman.EvilHangmanGame.GuessAlreadyMadeException;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static char numberToAlphabet(int number) {
		char alphabet = 'a';
		switch(number){
			case 0: alphabet = 'a'; break;
			case 1: alphabet = 'b'; break;
			case 2: alphabet = 'c'; break;
			case 3: alphabet = 'd'; break;
			case 4: alphabet = 'e'; break;
			case 5: alphabet = 'f'; break;
			case 6: alphabet = 'g'; break;
			case 7: alphabet = 'h'; break;
			case 8: alphabet = 'i'; break;
			case 9: alphabet = 'j'; break;
			case 10: alphabet = 'k'; break;
			case 11: alphabet = 'l'; break;
			case 12: alphabet = 'm'; break;
			case 13: alphabet = 'n'; break;
			case 14: alphabet = 'o'; break;
			case 15: alphabet = 'p'; break;
			case 16: alphabet = 'q'; break;
			case 17: alphabet = 'r'; break;
			case 18: alphabet = 's'; break;
			case 19: alphabet = 't'; break;
			case 20: alphabet = 'u'; break;
			case 21: alphabet = 'v'; break;
			case 22: alphabet = 'w'; break;
			case 23: alphabet = 'x'; break;
			case 24: alphabet = 'y'; break;
			case 25: alphabet = 'z'; break;
		} 
		return alphabet;
	}
	public static int alphabetToNumber(char alphabet) {
		int number = 0;
		switch(alphabet){
			case 'a': number = 0; break;
			case 'b': number = 1; break;
			case 'c': number = 2; break;
			case 'd': number = 3; break;
			case 'e': number = 4; break;
			case 'f': number = 5; break;
			case 'g': number = 6; break;
			case 'h': number = 7; break;
			case 'i': number = 8; break;
			case 'j': number = 9; break;
			case 'k': number = 10; break;
			case 'l': number = 11; break;
			case 'm': number = 12; break;
			case 'n': number = 13; break;
			case 'o': number = 14; break;
			case 'p': number = 15; break;
			case 'q': number = 16; break;
			case 'r': number = 17; break;
			case 's': number = 18; break;
			case 't': number = 19; break;
			case 'u': number = 20; break;
			case 'v': number = 21; break;
			case 'w': number = 22; break;
			case 'x': number = 23; break;
			case 'y': number = 24; break;
			case 'z': number = 25; break;
		}
		return number;
	}

	public static void main(String[] args) throws GuessAlreadyMadeException {
		if(args[0] == null || args[1] == null || args[2] == null ){
			System.out.println("USAGE: java Main dictionary wordlength guesses");
	        System.exit(0);
		}
		File dictionary = new File(args[0]);
		int wordLength = Integer.parseInt(args[1]);
		int guesses = Integer.parseInt(args[2]);
		ArrayList<Character> Word = new ArrayList<Character>();
		for(int i = 0; i < wordLength; i++){
			Word.add(new Character('-'));
		}
		if(wordLength < 2 || guesses < 1){
			System.out.println("wordlength should >= 2 and guesses should >= 1");
	        System.exit(0);
		}
		EvilHangman game = new EvilHangman();
		game.startGame(dictionary, wordLength);
		for(int i = 0; i < guesses; i++){
			System.out.format("You have %d guesses left\n", guesses-i);
			System.out.print("Used letters: ");
			for(int j = 0; j < game.guessed.length; j++){
				if(game.guessed[j] == numberToAlphabet(j)){
					System.out.format("%s ",game.guessed[j]);
				}
			}
			System.out.print("\nWords: ");
			for(int j = 0; j < wordLength; j++){
				System.out.format("%c",Word.get(j));
			}
			System.out.print("\nEnter guess: ");
			Scanner sc = new Scanner(System.in);
			String input = sc.next();
			input = input.toLowerCase();
			char[] guessArray = input.toCharArray();
			char guess = guessArray[0];
			while(game.guessed[alphabetToNumber(guess)] == guess||guessArray.length != 1 || !Character.isLetter(guess)){
				if(guessArray.length != 1 || !Character.isLetter(guess)){
					System.out.println("Invalid input");
				}
				else{
					System.out.println("You already used that letter");
				}
				input = sc.next();
				input = input.toLowerCase();
				guessArray = input.toCharArray();
				guess = guessArray[0];
			}
			game.makeGuess(guess);
			String possibleAnsMember = game.possibleAns.get(0);
			ArrayList<Integer> index = game.getFamilyIndex(possibleAnsMember, guess);
			boolean gotIt = false;
			int count = 0;
			for(int j = 0; j < wordLength; j++){
				if(index.contains(j)){
					Word.set(j,guess);
					count++;
					gotIt = true;
				}
			}
			if(gotIt){
				System.out.format("Yes, there is %d %c\n\n", count, guess);
				guesses++;
			}
			else{
				System.out.format("Sorry there is no %c's\n\n",guess);
			}
			boolean won = true;
			for(int j = 0; j < wordLength; j++){
				if(Word.get(j).charValue() == '-'){
//					System.out.format("false %d's\n\n",j);
					won = false;
				}
			}
			if(won){
				System.out.print("You win!\nThe word was: ");
				for(int j = 0; j < wordLength; j++){
					System.out.print(Word.get(j).charValue());
				}
				sc.close();
				System.exit(0);
			}
		}
		System.out.format("You lose!\nThe word was: %s\n",game.possibleAns.get(0));
		System.exit(0);
		
	}

}
