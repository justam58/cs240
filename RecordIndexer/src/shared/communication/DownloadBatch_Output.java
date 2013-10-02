package shared.communication;

import java.util.ArrayList;

import shared.model.*;

/** Class that store the output of downloading the batch */
public class DownloadBatch_Output {
	
	private Image image;
	private Project project;
	private ArrayList<Field> fields;
	
	/** Constructor with all parameters */
	public DownloadBatch_Output(Image image, Project project, ArrayList<Field> fields) {
		this.image = image;
		this.project = project;
		this.fields = fields;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	

	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		String result = String.format("%d\n%d\n%s\n%d\n%d\n%d\n%d\n", 
										image.getImageID(),project.getProjectID(), image.getFile(),project.getFirstYCoord(),
										project.getRecordHeight(),project.getRecordsPerImage(),fields.size());
		for(int i = 0; i < fields.size(); i++){
			Field field = fields.get(i);
			String temp = String.format("%d\n%d\n%s\n%s\n%d\n%d\n", 
					field.getFieldID(), i+1, field.getTitle(), field.getHelpHTML(),
					field.getxCoord(), field.getWidth());
			if(field.getKnownData() != null) {
				temp += field.getKnownData() + "\n";
			}
			result += temp;
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		DownloadBatch_Output o = (DownloadBatch_Output) obj;
		if((this == null && o != null) || (this != null && o == null)){
			return false;
		}
		for (int i = 0; i < fields.size(); i++){
			if(!this.getFields().get(i).equals(o.getFields().get(i))){
				return false;
			}
		}
		return(this.getImage().equals(o.getImage()) &&
			   this.getProject().equals(o.getProject()));
	}
}
