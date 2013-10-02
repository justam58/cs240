package shared.communication;

import java.util.ArrayList;

import shared.model.Field;

/** Class that store the output of getting the fields */
public class GetFields_Output {
	
	private ArrayList<Field> fields;
	
	/** Constructor with all parameters */
	public GetFields_Output( ArrayList<Field> fields) {
		this.fields = fields;
	}


	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		String result = "";
		for(int i = 0; i < fields.size(); i++){
			Field field = fields.get(i);
			String temp = String.format("%d\n%d\n%s\n",
										field.getProjectID(),field.getFieldID(),field.getTitle());
			result += temp;
		}
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		GetFields_Output o = (GetFields_Output) obj;
		if((this == null && o != null) || (this != null && o == null)){
			return false;
		}
		for (int i = 0; i < fields.size(); i++){
			if(!this.getFields().get(i).equals(o.getFields().get(i))){
				return false;
			}
		}
		return true;
	}
}
