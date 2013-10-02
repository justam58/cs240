package shared.model;

/** Data Class storing User's information */
public class User {
	
	private int userID;
	private String username;
	private String password;
	private String email;
	private int indexedRecords;
	private String firstName;
	private String lastName;
	
	/** Constructor with no parameters */
	public User(){
		this.userID = -1;
		this.username = null;
		this.password = null;
		this.email = null;
		this.indexedRecords = 0;
		this.firstName = null;
		this.lastName = null;
	}
	
	/** Constructor with all parameters */
	public User(int userID,String username,String password,String email,String firstName,String lastName){
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.email = email;
		this.indexedRecords = 0;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(int userID,String username,String password,String email,String firstName,String lastName, int indexedRecords){
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.email = email;
		this.indexedRecords = indexedRecords;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	

	/** increment the records the user has indexed */
	public void incementIndexedRecords(int n){
		indexedRecords += n;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIndexedRecords() {
		return indexedRecords;
	}
	public void setIndexedRecords(int indexedRecords) {
		this.indexedRecords = indexedRecords;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}

	@Override
	public boolean equals(Object obj) {
		User o = (User) obj;
		return areEqual(this,o,true);
	}
	
	private boolean areEqual(User a, User b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getUserID() != b.getUserID()) {
				return false;
			}
		}	
		return (safeEquals(a.getFirstName(), b.getFirstName()) &&
				safeEquals(a.getLastName(), b.getLastName()) &&
				safeEquals(a.getUsername(), b.getUsername()) &&
				safeEquals(a.getEmail(), b.getEmail()) &&
				safeEquals(a.getIndexedRecords(),b.getIndexedRecords()) &&
				safeEquals(a.getPassword(), b.getPassword()));
	}
	
	private boolean safeEquals(Object a, Object b) {
		if (a == null || b == null) {
			return (a == null && b == null);
		}
		else {
			return a.equals(b);
		}
	}
	
}
