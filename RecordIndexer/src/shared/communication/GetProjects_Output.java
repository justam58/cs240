package shared.communication;

import java.util.ArrayList;
import shared.model.Project;

/** Class that store the output of getting the projects */
public class GetProjects_Output {
	
	private ArrayList<Project> projects;
	
	/** Constructor with all parameters */
	public GetProjects_Output(ArrayList<Project> projects) {
		this.projects = projects;
	}

	public ArrayList<Project> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}
	
	@Override
	public String toString() {
		String result = "";
		for(int i = 0; i < projects.size(); i++){
			Project project = projects.get(i);
			String temp = String.format("%d\n%s\n",
										project.getProjectID(),project.getTitle());
			result += temp;
		}
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		GetProjects_Output o = (GetProjects_Output) obj;
		if((this == null && o != null) || (this != null && o == null)){
			return false;
		}
		for (int i = 0; i < projects.size(); i++){
			if(!this.getProjects().get(i).equals(o.getProjects().get(i))){
				return false;
			}
		}
		return true;
	}

}
