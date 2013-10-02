package shared.communication;

import java.util.ArrayList;


/** Class that store the input of submitting the batch */
public class SubmitBatch_Input {
	
	private String username;
	private String password;
	private int imageID;
	private ArrayList<String> values;
	
	/** Constructor with all parameters */
	public SubmitBatch_Input(int imageID, ArrayList<String> values, String username, String password) {
		this.username = username;
		this.password = password;
		this.imageID = imageID;
		this.values = values;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setgetValues(ArrayList<String> values) {
		this.values = values;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
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
	
	@Override
	public String toString() {
		String result = String.format("%s\n%s\n%d\n",username,password,imageID);
		
		for(int i = 0; i < values.size(); i++){
			if(i == values.size()-1){
				result += values.get(i) + "\n";
			}
			else{
				result += values.get(i) + ",";
			}
		}
		
		return result;
	}

}
