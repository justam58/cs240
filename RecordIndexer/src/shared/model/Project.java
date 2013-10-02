package shared.model;


/** Data Class storing Project's information */
public class Project {
	
	private int projectID;
	private String title;
	private int recordsPerImage;
	private int firstYCoord;
	private int recordHeight;
	
	/** Constructor with no parameters */
	public Project(){
		this.projectID = -1;
		this.title = null;
		this.recordsPerImage = 0;
		this.firstYCoord = 0;
		this.recordHeight = 0;
	}
	
	/** Constructor with all parameters */
	public Project(int projectID,String title,int recordsPerImage,int firstYCoord,int recordHeight){
		this.projectID = projectID;
		this.title = title;
		this.recordsPerImage = recordsPerImage;
		this.firstYCoord = firstYCoord;
		this.recordHeight = recordHeight;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRecordsPerImage() {
		return recordsPerImage;
	}

	public void setRecordsPerImage(int recordsPerImage) {
		this.recordsPerImage = recordsPerImage;
	}

	public int getFirstYCoord() {
		return firstYCoord;
	}

	public void setFirstYCoord(int firstYCoord) {
		this.firstYCoord = firstYCoord;
	}

	public int getRecordHeight() {
		return recordHeight;
	}

	public void setRecordHeight(int recordHeight) {
		this.recordHeight = recordHeight;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	
	@Override
	public boolean equals(Object obj) {
		Project o = (Project) obj;
		return areEqual(this,o,true);
	}
	
	private boolean areEqual(Project a, Project b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getProjectID() != b.getProjectID()) {
				return false;
			}
		}	
		return (safeEquals(a.getTitle(), b.getTitle()) &&
				safeEquals(a.getFirstYCoord(), b.getFirstYCoord()) &&
				safeEquals(a.getRecordHeight(), b.getRecordHeight()) &&
				safeEquals(a.getRecordsPerImage(), b.getRecordsPerImage()));
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
