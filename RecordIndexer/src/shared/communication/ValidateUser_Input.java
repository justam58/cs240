package shared.communication;

/** Class that store the input of validating the user */
public class ValidateUser_Input {
	
	private String username;
	private String password;
	
	/** Constructor with all parameters */
	public ValidateUser_Input(String username, String password) {
		this.username = username;
		this.password = password;
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
		return String.format("%s\n%s\n",username,password);
	}
	
}
