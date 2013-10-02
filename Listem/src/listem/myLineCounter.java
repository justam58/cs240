package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class myLineCounter extends GrepLineCounter implements LineCounter{

	public myLineCounter(){

	}

	
	/**
	 * Count the number of lines in files whose names match a given pattern.
	 * 
	 * @param directory The base directory to look at files from
	 * @param fileSelectionPattern Pattern for selecting file names
	 * @param recursive Recursively search through directories
	 * @return A Map containing files whose lines were counted. Each file is mapped
	 * to an integer which is the number of lines counted in the file.
	 */
	public Map<File, Integer> countLines(File directory, String fileSelectionPattern, 
			boolean recursive) {
		
		Map<File, Integer> map = new HashMap<File, Integer>();
		List<File> files = super.searchFile(directory,fileSelectionPattern,recursive);
		for(int i = 0; i < files.size(); i++){
			try {
				int lineNumbers = 0;
				Scanner sc = new Scanner(files.get(i));			
				while(sc.hasNextLine()){
					sc.nextLine();
					lineNumbers++;
				}
				map.put(files.get(i), lineNumbers);
				sc.close();
			} catch (FileNotFoundException e) {
				System.out.println("Could not find file: " + files.get(i));
				e.printStackTrace();
			}				
		}		
		return map;
	}
	
}
