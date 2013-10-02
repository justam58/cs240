package shared.communication;

import java.util.ArrayList;

import shared.model.Image;
import shared.model.Value;


/** Class that store the output of searching */
public class Search_Output {
	
	private ArrayList<Value> values;
	private ArrayList<Image> images;
	
	/** Constructor with all parameters */
	public Search_Output(ArrayList<Value> values, ArrayList<Image> images) {
		this.values = values;
		this.images = images;
	}


	public ArrayList<Value> getValues() {
		return values;
	}

	public void setValues(ArrayList<Value> values) {
		this.values = values;
	}

	public ArrayList<Image> getImages() {
		return images;
	}

	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}
	
	@Override
	public String toString() {
		String result = "";
		for(int i = 0; i < values.size(); i++){
			Value value = values.get(i);
			Image image = images.get(i);
			String temp = String.format("%d\n%s\n%d\n%d\n",
										image.getImageID(),image.getFile(),
										value.getRow(),value.getFieldID());
			result += temp;
		}
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		Search_Output o = (Search_Output) obj;
		if((this == null && o != null) || (this != null && o == null)){
			return false;
		}
		for (int i = 0; i < values.size(); i++){
			if(!this.getValues().get(i).equals(o.getValues().get(i))){
				return false;
			}
		}
		for (int i = 0; i < images.size(); i++){
			if(!this.getImages().get(i).equals(o.getImages().get(i))){
				return false;
			}
		}
		return true;
	}
}
