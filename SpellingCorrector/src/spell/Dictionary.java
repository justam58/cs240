package spell;

import java.util.ArrayList;

public class Dictionary implements Trie{
	
	private int wordCount = 0;
	private int nodeCount = 0;
	private WordNode root;
	
	public Dictionary() {
		root = new WordNode();
		nodeCount = 1;
	}
	
	public void add(String word) {
		char[] wordChar = word.toCharArray();
		int [] wordInt = new int[word.length()];
		for(int i = 0; i < word.length(); i++){
			wordInt[i] = alphabetToNumber(wordChar[i]);
		}
		if(find(word) == null){
			WordNode current = root;
			for(int i = 0; i < word.length(); i++){
				if(current.kids[wordInt[i]] == null){
					WordNode newNode = new WordNode();
					current.kids[wordInt[i]] = newNode;
					nodeCount++;
					current = newNode;
				}
				else{
					current = current.kids[wordInt[i]];
				}
			}
			wordCount++;
			current.incrementValue();
		}
		else{
			WordNode exstingWord = find(word);
			exstingWord.incrementValue();
		}
	}
		
	public WordNode find(String word) {
		char[] wordChar = word.toCharArray();
		int [] wordInt = new int[word.length()];
		for(int i = 0; i < word.length(); i++){
			wordInt[i] = alphabetToNumber(wordChar[i]);
		}
		WordNode current = root;
		for(int i = 0; i < word.length(); i++){
			if(current.kids[wordInt[i]] == null){
				return null;
			}
			else{
				current = current.kids[wordInt[i]];
			}
		}
		if(current != null && current.getValue()>0){
			return current;
		}
		return null;
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
		
	public int getWordCount() {
		return wordCount;
	}

	public int getNodeCount() {
		return nodeCount;
	}
	
	@Override
	public String toString() {
		ArrayList<String> words = toStringRecursion(root);
		String s = new String();
		for(int j = 0; j < words.size(); j++){
			s += words.get(j) + " " + find(words.get(j)).getValue() + "\n";
		}
		return s;
	}
	
	public ArrayList<String> toStringRecursion(WordNode local) {
		ArrayList<String> words = new ArrayList<String>();
		for(int i = 0; i < 26; i++){
			if(local.kids[i] != null){
				ArrayList<String> temp = toStringRecursion(local.kids[i]);
				for(String s : temp){
					words.add(numberToAlphabet(i) + s);
				}
			}
		}
		return words;
	}
	
	private char numberToAlphabet(int number) {
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

	@Override
	public int hashCode() {
		int temp = wordCount * 46 + nodeCount * 79;
		temp /= 38;
		return temp;
	}
	
	@Override
	public boolean equals(Object o) {
		Dictionary other = (Dictionary) o;
		return this.toString().equals(other.toString());
	}

	/**
	 * Your trie node class should implement the Trie.Node interface
	 */
	public class WordNode implements Trie.Node{
	
		private int value = 0;
		public WordNode[] kids;
		
		public WordNode() {
			kids = new WordNode[26];
		}
		
		public int getValue() {
			return value;
		}
		public int incrementValue() {
			value++;
			return value;
		}
		
		
	}
}

