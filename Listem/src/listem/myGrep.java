package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class myGrep extends GrepLineCounter implements Grep{
	
	public myGrep(){

	}
	
	/**
	 * Find lines that match a given pattern in files whose names match another
	 * pattern
	 * @param fileSelectionPattern Pattern for selecting file names
	 * @param substringSelectionPattern Pattern to search for in lines of a file
	 * @param recursive Recursively search through directories
	 * @return A Map containing files that had at least one match found inside them.
	 * Each file is mapped to a list of strings which are the exact strings from
	 * the file where the <code>substringSelectionPattern</code> was found.
	 */
	public Map<File, List<String>> grep(File directory, String fileSelectionPattern, 
			String substringSelectionPattern, boolean recursive) {
		
		Map<File, List<String>> map = new HashMap<File, List<String>>();
		List<File> files = super.searchFile(directory,fileSelectionPattern,recursive);
		for(int i = 0; i < files.size(); i++){
			try {
				Scanner sc = new Scanner(files.get(i));
				List<String> matchStrings = new ArrayList<String>();			
				while(sc.hasNextLine()){
					String s = sc.nextLine();
					Pattern p = Pattern.compile(substringSelectionPattern);
				    Matcher m = p.matcher(s);
					if(m.find()){
						matchStrings.add(s);
					}
				}
				if(matchStrings.size()!=0){
					map.put(files.get(i), matchStrings);
				}
				sc.close();
			} catch (FileNotFoundException e) {
				System.out.println("Could not find file: " + files.get(i));
				e.printStackTrace();
			}				
		}		
		return map;
	}
}