package shared.model;

/** Data Class storing Field's information */
public class Field {
	
	private int fieldID;
	private String title;
	private int xCoord;
	private int width;
	private String helpHTML;
	private String knownData;
	private int projectID;
	
	/** Constructor with no parameters */
	public Field(){
		this.fieldID = -1;
		this.title = null;
		this.xCoord = 0;
		this.width = 0;
		this.helpHTML = null;
		this.knownData = null;
		this.projectID = -1;
	}
	
	/** Constructor with all parameters */
	public Field(int fieldID,String title,int xCoord,int width,String helpHTML,String knownData, int projectID){
		this.fieldID = fieldID;
		this.title = title;
		this.xCoord = xCoord;
		this.width = width;
		this.helpHTML = helpHTML;
		this.knownData = knownData;
		this.projectID = projectID;
	}
	
	public void addURLPrefixToHelpHTML(String host, String port){
		String urlPrefix = "http://" + host + ":" + port + "/";
		helpHTML = urlPrefix + helpHTML;
	}
	
	public void addURLPrefixToKnownData(String host, String port){
		String urlPrefix = "http://" + host + ":" + port + "/";
		knownData = urlPrefix + knownData;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getxCoord() {
		return xCoord;
	}
	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getHelpHTML() {
		return helpHTML;
	}
	public void setHelpHTML(String helpHTML) {
		this.helpHTML = helpHTML;
	}
	public String getKnownData() {
		return knownData;
	}
	public void setKnownData(String knownData) {
		this.knownData = knownData;
	}
	public int getFieldID() {
		return fieldID;
	}
	public void setFieldID(int fieldID) {
		this.fieldID = fieldID;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	@Override
	public boolean equals(Object obj) {
		Field o = (Field) obj;
		return areEqual(this,o,true);
	}
	
	private boolean areEqual(Field a, Field b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getFieldID() != b.getFieldID()) {
				return false;
			}
		}	
		return (safeEquals(a.getTitle(), b.getTitle()) &&
				safeEquals(a.getxCoord(), b.getxCoord()) &&
				safeEquals(a.getWidth(), b.getWidth()) &&
				safeEquals(a.getHelpHTML(), b.getHelpHTML()) &&
				safeEquals(a.getKnownData(), b.getKnownData()) &&
				safeEquals(a.getProjectID(), b.getProjectID()));
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
