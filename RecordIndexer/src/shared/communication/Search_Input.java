package shared.communication;

import java.util.ArrayList;

/** Class that store the input of searching */
public class Search_Input {
	
	private String username;
	private String password;
	private ArrayList<Integer> fieldIDs;
	private ArrayList<String> searchValues;
	
	/** Constructor with all parameters */
	public Search_Input(String username, String password, ArrayList<Integer> fieldIDs, ArrayList<String> searchValues) {
		this.username = username;
		this.password = password;
		this.fieldIDs = fieldIDs;
		this.searchValues = searchValues;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Integer> getFieldIDs() {
		return fieldIDs;
	}

	public void setFieldIDs(ArrayList<Integer> fieldIDs) {
		this.fieldIDs = fieldIDs;
	}

	public ArrayList<String> getSearchValues() {
		return searchValues;
	}

	public void setSearchValues(ArrayList<String> searchValues) {
		this.searchValues = searchValues;
	}

	@Override
	public String toString() {
		String result = String.format("%s\n%s\n",username,password);
		for(int i = 0; i < fieldIDs.size(); i++){
			if(i == fieldIDs.size()-1){
				result += fieldIDs.get(i) + "\n";
			}
			else{
				result += fieldIDs.get(i) + ",";
			}
		}
		for(int i = 0; i < searchValues.size(); i++){
			if(i == searchValues.size()-1){
				result += searchValues.get(i) + "\n";
			}
			else{
				result += searchValues.get(i) + ",";
			}
		}
		return result;
	}
}
