package listem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GrepLineCounter {
	
	public GrepLineCounter(){
		
	}
	public List<File> searchFile(File directory, String target, boolean recurssive){
		if(recurssive){
			List<File> result = searchFileRecursion(directory,target);
			return result;
		}
		else{
			File[] lists = directory.listFiles();
			List<File> result = new ArrayList<File>();
			for(int i = 0; i < lists.length; i++){
				if (lists[i].isFile())  {
					boolean match = Pattern.matches(target, lists[i].getName());
					if(match){
						result.add(lists[i]);
					}
				} 
			}
			return result;		
		}
	}
	
	public List<File> searchFileRecursion(File local, String target){
		File[] lists = local.listFiles();
		List<File> result = new ArrayList<File>();
		for(int i = 0; i < lists.length; i++){
			if (lists[i].isDirectory())  {
				result.addAll(searchFileRecursion(lists[i], target));
			} 
			else {
				boolean match = Pattern.matches(target, lists[i].getName());
				if(match){
					result.add(lists[i]);
				}
			}
		}
		return result;
	}
	
}
