package shared.model;

/** Data Class storing Record's information */
public class Value {
	private String info;
	private int valueID;
	private int imageID;
	private int fieldID;
	private int row;
	
	/** Constructor with no parameters */
	public Value(){
		this.info = null;
		this.valueID = -1;
		this.imageID = -1;
		this.fieldID = -1;
		this.row = -1;
	}
	
	/** Constructor with all parameters */
	public Value(String info, int valueID,int imageID,int fieldID, int row){
		this.info = info;
		this.valueID = valueID;
		this.imageID = imageID;
		this.fieldID = fieldID;
		this.row = row;
	}

	public int getValueID() {
		return valueID;
	}

	public void setValueID(int valueID) {
		this.valueID = valueID;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public int getFieldID() {
		return fieldID;
	}

	public void setFieldID(int fieldID) {
		this.fieldID = fieldID;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	@Override
	public boolean equals(Object obj) {
		Value o = (Value) obj;
		return areEqual(this,o,true);
	}
	

	private boolean areEqual(Value a, Value b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getValueID() != b.getValueID()) {
				return false;
			}
		}	
		return (safeEquals(a.getInfo(), b.getInfo()) &&
				safeEquals(a.getFieldID(), b.getFieldID()) &&
				safeEquals(a.getImageID(), b.getImageID()) &&
				safeEquals(a.getRow(), b.getRow()));
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
