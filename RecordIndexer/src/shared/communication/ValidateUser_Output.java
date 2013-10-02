package shared.communication;

import shared.model.User;

/** Class that store the output of validating the user */
public class ValidateUser_Output {
	
	private User user;
	private boolean valid;
	
	/** Constructor with all parameters */
	public ValidateUser_Output(User user,boolean valid) {
		this.user = user;
		this.valid = valid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return String.format("%s\n%s\n%d\n",user.getFirstName(),
							user.getLastName(),user.getIndexedRecords());
	}
	
	@Override
	public boolean equals(Object obj) {
		ValidateUser_Output o = (ValidateUser_Output) obj;
		if((this == null && o != null) || (this != null && o == null)){
			return false;
		}
		return(this.isValid() == o.isValid() &&
			   this.getUser().equals(o.getUser()));
	}
	
}
