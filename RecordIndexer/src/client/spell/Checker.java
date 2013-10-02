package client.spell;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Checker{
	
	private Set<String> dictionary;
	private Set<String> suggestions;
	
	public void useDictionary(File dictionaryFile) throws IOException {
		Scanner sc = new Scanner(dictionaryFile);
		dictionary = new TreeSet<String>();
	    while(sc.hasNextLine()){
	        String[] tokens = sc.nextLine().split(",");
	        for(int i=0; i < tokens.length; i++){
	        	dictionary.add(tokens[i].toUpperCase());
	        }
	    }
		sc.close();
	}
	
	public void suggestSimilarWord(String inputWord){
		String input = inputWord.toUpperCase();
		suggestions = new TreeSet<String>();
		if(dictionary.contains(input)){
			suggestions.add(input);
			return;
		}
		getDistanceWord(input,1);
		getDistanceWord(input,2);
	}
	
	public Set<String> getSuggestions() {
		return suggestions;
	}
	
	public Set<String> getDictionary() {
		return dictionary;
	}

	private void getDistanceWord(String input, int distance){
		int size = input.length();
		for(int i = 0; i < size; i++){
			String delete1 = input.substring(0,i)+input.substring(i+1,size);
			if(distance == 2){
				getDistanceWord(delete1,1);
			}
			else{
				checkDictionary(delete1);
			}
			
		}
		
		for(int i = 0; i < size; i++){
			for(int j = i+1 ; j < size; j++){
				String transpose1 = input.substring(0,i)+input.substring(j,j+1)+input.substring(i+1,j)+input.substring(i,i+1)+input.substring(j+1,size);
				if(distance == 2){
					getDistanceWord(transpose1,1);
				}
				else{
					checkDictionary(transpose1);
				}
			}
		}
		
		for(int i = 0; i < size; i++){
			for(int j = 0 ; j < 26; j++){
				String alternate1 = input.substring(0,i)+numberToAlphabet(j)+input.substring(i+1,size);
				if(distance == 2){
					getDistanceWord(alternate1,1);
				}
				else{
					checkDictionary(alternate1);
				}
			}
		}
		
		for(int i = 0; i < size+1; i++){
			for(int j = 0 ; j < 26; j++){
				String insert1 = input.substring(0,i)+numberToAlphabet(j)+input.substring(i,size);
				if(distance == 2){
					getDistanceWord(insert1,1);
				}
				else{
					checkDictionary(insert1);
				}
			}
		}
	}
	
	private void checkDictionary(String word){
		if(dictionary.contains(word)){
			suggestions.add(word);
		}
	}
	
	private char numberToAlphabet(int number){
		char alphabet = 'a';
		switch(number){
			case 0: alphabet = 'A'; break;
			case 1: alphabet = 'B'; break;
			case 2: alphabet = 'C'; break;
			case 3: alphabet = 'D'; break;
			case 4: alphabet = 'E'; break;
			case 5: alphabet = 'F'; break;
			case 6: alphabet = 'G'; break;
			case 7: alphabet = 'H'; break;
			case 8: alphabet = 'I'; break;
			case 9: alphabet = 'j'; break;
			case 10: alphabet = 'K'; break;
			case 11: alphabet = 'L'; break;
			case 12: alphabet = 'M'; break;
			case 13: alphabet = 'N'; break;
			case 14: alphabet = 'O'; break;
			case 15: alphabet = 'P'; break;
			case 16: alphabet = 'Q'; break;
			case 17: alphabet = 'R'; break;
			case 18: alphabet = 'S'; break;
			case 19: alphabet = 'T'; break;
			case 20: alphabet = 'U'; break;
			case 21: alphabet = 'V'; break;
			case 22: alphabet = 'W'; break;
			case 23: alphabet = 'X'; break;
			case 24: alphabet = 'Y'; break;
			case 25: alphabet = 'Z'; break;
		}
		return alphabet;
	}


	
}