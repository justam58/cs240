package spell;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Checker implements SpellCorrector {
	public Dictionary dictionary = new Dictionary();
	public class Word{
		public String word;
		public int count;
		
		public Word(String word,int count){
			this.word = word;
			this.count = count;
		}
	}
	
	public void useDictionary(String dictionaryFileName) throws IOException {
		File srcFile = new File(dictionaryFileName);
		Scanner sc = new Scanner(srcFile);
		while(sc.hasNext()){
			String word = sc.next();
			String wordLower = word.toLowerCase();
			dictionary.add(wordLower);
		}
//		System.out.format("%d %d \n",dictionary.getWordCount(),dictionary.getNodeCount());
//		System.out.format("%s",dictionary.toString());
		sc.close();
	}
		
	public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
		String input = inputWord.toLowerCase();
		if(dictionary.find(input) != null && dictionary.find(input).getValue() > 0){
			return input;
		}
		Word suggest = null;
		suggest = getDistanceWord(input,1,suggest);
		if(suggest == null){
			suggest = getDistanceWord(input,2,suggest);
		}
		if(suggest == null){
			throw new NoSimilarWordFoundException();
		}
		else{
			return suggest.word;
		}
	}
	public Word getDistanceWord(String input, int distance, Word suggest){
		int size = input.length();
		for(int i = 0; i < size; i++){
			String delete1 = input.substring(0,i)+input.substring(i+1,size);
			if(distance == 2){
				suggest = getDistanceWord(delete1,1,suggest);
				if(suggest != null){
				}
			}
			else{
				suggest = checkDictionary(delete1,suggest);
			}
			
		}
		
		for(int i = 0; i < size; i++){
			for(int j = i+1 ; j < size; j++){
				String transpose1 = input.substring(0,i)+input.substring(j,j+1)+input.substring(i+1,j)+input.substring(i,i+1)+input.substring(j+1,size);
				if(distance == 2){
					suggest = getDistanceWord(transpose1,1,suggest);
				}
				else{
					suggest = checkDictionary(transpose1,suggest);
				}
			}
		}
		
		for(int i = 0; i < size; i++){
			for(int j = 0 ; j < 26; j++){
				String alternate1 = input.substring(0,i)+numberToAlphabet(j)+input.substring(i+1,size);
				if(distance == 2){
					suggest = getDistanceWord(alternate1,1,suggest);
				}
				else{
					suggest = checkDictionary(alternate1,suggest);
				}
			}
		}
		
		for(int i = 0; i < size+1; i++){
			for(int j = 0 ; j < 26; j++){
				String insert1 = input.substring(0,i)+numberToAlphabet(j)+input.substring(i,size);
				if(distance == 2){
					suggest = getDistanceWord(insert1,1,suggest);
				}
				else{
					suggest = checkDictionary(insert1,suggest);
				}
			}
		}
		return suggest;
	}
	public Word checkDictionary(String word, Word suggest){
		if(dictionary.find(word) != null && dictionary.find(word).getValue() > 0){
			if(suggest == null){
				Word newWord = new Word(word,dictionary.find(word).getValue());
				suggest = newWord;
			}
			else{
				if(dictionary.find(word).getValue() > suggest.count){
					Word newWord = new Word(word,dictionary.find(word).getValue());
					suggest = newWord;
				}
				else if(dictionary.find(word).getValue() == suggest.count){
					if(word.compareTo(suggest.word)<0){
						Word newWord = new Word(word,dictionary.find(word).getValue());
						suggest = newWord;
					}
				}
			}
		}
		return suggest;
	}
	public char numberToAlphabet(int number){
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
}