package shared.model;


/** Data Class storing Image's information */
public class Image {
	
	private int userID;
	private int imageID;
	private String file;
	private int projectID;
	
	/** Constructor with no parameters */
	public Image(){
		this.imageID = -1;
		this.file = null;
		this.projectID = -1;
		this.userID = -1;
	}
	
	/** Constructor with all parameters */
	public Image(int imageID,String file,int projectID,int userID){
		this.imageID = imageID;
		this.file = file;
		this.projectID = projectID;
		this.userID = userID;
	}
	
	public void addURLPrefix(String host, String port){
		String urlPrefix = "http://" + host + ":" + port + "/";
		file = urlPrefix + file;
	}
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	@Override
	public boolean equals(Object obj) {
		Image o = (Image) obj;
		return areEqual(this,o,true);
	}
	
	private boolean areEqual(Image a, Image b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getImageID() != b.getImageID()) {
				return false;
			}
		}	
		return (safeEquals(a.getFile(), b.getFile()) &&
				safeEquals(a.getProjectID(), b.getProjectID()) &&
				safeEquals(a.getUserID(), b.getUserID()));
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
