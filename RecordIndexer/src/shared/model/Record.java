package shared.model;

/** Data Class storing Record's information */
public class Record {
	private int recordID;
	private String firstName;
	private String lastName;
	private String gender;
	private int age;
	private String ethnicity;
	private int imageID;
	
	/** Constructor with no parameters */
	public Record(){
		this.recordID = -1;
		this.firstName = null;
		this.lastName = null;
		this.gender = null;
		this.age = 0;
		this.ethnicity = null;
		this.imageID = -1;
	}
	
	/** Constructor with all parameters */
	public Record(int recordID,String firstName,String lastName,String gender,int age,String ethncity,int imageID){
		this.recordID = recordID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
		this.ethnicity = ethncity;
		this.imageID = imageID;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public int getRecordID() {
		return recordID;
	}

	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
	
	
}
