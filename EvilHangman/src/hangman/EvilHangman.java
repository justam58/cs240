package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class EvilHangman implements EvilHangmanGame {
	
	public ArrayList<String> possibleAns= new ArrayList<String>();
	public char[] guessed= new char[26];
	public EvilHangman(){}
	
	/**
	 * Starts a new game of evil hangman using words from <code>dictionary</code>
	 * with length <code>wordLength</code>
	 * 
	 * @param dictionary Dictionary of words to use for the game
	 * @param wordLength Number of characters in the word to guess
	 */
	public void startGame(File dictionary, int wordLength) {
		try {
			Scanner sc = new Scanner(dictionary);
			while(sc.hasNext()){
				String temp = sc.nextLine();
				if(temp.length() == wordLength){
					possibleAns.add(temp);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file: " + dictionary.getName());
			e.printStackTrace();
		}
		
	}
	public ArrayList<Integer> getFamilyIndex (String member, char target){
		ArrayList<Integer> familyIndex = new ArrayList<Integer>();
		char[] memberChar = member.toCharArray();
		for(int i = 0; i< memberChar.length; i++){
			if(memberChar[i] == target){
				familyIndex.add(i);
			}
		}
		return familyIndex;
	}
	
	public int alphabetToNumber(char alphabet) {
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


	/**
	 * Make a guess in the current game.
	 * 
	 * @param guess The character being guessed
	 * @return The set of strings that satisfy all the guesses made so far
	 * in the game, including the guess made in this call. The game could claim
	 * that any of these words had been the secret word for the whole game. 
	 * 
	 * @throws GuessAlreadyMadeException If the character <code>guess</code> 
	 * has already been guessed in this game.
	 */
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		if(guessed[alphabetToNumber(guess)] == guess){
			throw new GuessAlreadyMadeException();
		}
		guessed[alphabetToNumber(guess)] = guess;
		ArrayList<ArrayList<String>> families = new ArrayList<ArrayList<String>>();
		for(String s : possibleAns){
//			System.out.format("test %s",s);
			ArrayList<Integer> targetIndex = getFamilyIndex(s,guess);
			boolean addToFamily = false;
			for(ArrayList<String> family : families){
				String familyMember = family.get(0);
//				System.out.format("test %s",familyMember);
				ArrayList<Integer> familyIndex = getFamilyIndex(familyMember,guess);
				if(targetIndex.equals(familyIndex)){
					family.add(s);
					addToFamily = true;
				}
			}
			if(addToFamily == false){
				ArrayList<String> newFamily = new ArrayList<String>();
				newFamily.add(s);
				families.add(newFamily);
			}
		}
		
		ArrayList<String> possibleFamily = null;
		for(ArrayList<String> family: families){
			if(possibleFamily == null){
				possibleFamily = family;
			}
			else if (family.size() > possibleFamily.size()){
				possibleFamily = family;
			}
			else if(family.size() == possibleFamily.size()){
				String familyMember = family.get(0);
				String possibleFamilyMember = possibleFamily.get(0);
				ArrayList<Integer> familyIndex = getFamilyIndex(familyMember,guess);
				ArrayList<Integer> possiblieFamilyIndex = getFamilyIndex(possibleFamilyMember,guess);
//				System.out.format("test %s %d %s %d\n",familyMember,familyIndex.size(),
//						possibleFamilyMember,possiblieFamilyIndex.size());
				if(familyIndex.size() == 0){
					possibleFamily = family;
				}
				else if(possiblieFamilyIndex.size() == 0){
					
				}
				else if(familyIndex.size() < possiblieFamilyIndex.size()){
					possibleFamily = family;
				}
				else if(familyIndex.get(familyIndex.size()-1) == possiblieFamilyIndex.get(possiblieFamilyIndex.size()-1)){
					while(familyIndex.get(familyIndex.size()-1) == possiblieFamilyIndex.get(possiblieFamilyIndex.size()-1)){
						familyIndex.remove(familyIndex.size()-1);
						possiblieFamilyIndex.remove(possiblieFamilyIndex.size()-1);
						if(familyIndex.size() == 0){
							possibleFamily = family;
							break;
						}
						else if(possiblieFamilyIndex.size() == 0){
							break;
						}
						else if(familyIndex.get(familyIndex.size()-1) > possiblieFamilyIndex.get(possiblieFamilyIndex.size()-1)){
							possibleFamily = family;
							break;
						}
					}
					
				}
			}
			
		}
		possibleAns = possibleFamily;
		Set<String> result = new HashSet<String>(possibleFamily);
		return result;
		
	}
	
}