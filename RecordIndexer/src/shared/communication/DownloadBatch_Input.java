package shared.communication;

/** Class that store the input of downloading the batch */
public class DownloadBatch_Input {
	
	private String username;
	private String password;
	private int projectID;
	
	/** Constructor with all parameters */
	public DownloadBatch_Input(String username, String password, int projectID) {
		this.username = username;
		this.password = password;
		this.projectID = projectID;
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

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	@Override
	public String toString() {
		return String.format("%s\n%s\n%d\n",username,password,projectID);
	}
}
