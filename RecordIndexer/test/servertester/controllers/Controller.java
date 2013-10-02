package servertester.controllers;

import java.util.*;

import client.communication.ServerCommunicator;

import servertester.views.*;
import shared.communication.*;

public class Controller implements IController {

	private IView _view;
	private ServerCommunicator sc = ServerCommunicator.getInstance();
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods

	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		sc.setHostandPort(getView().getHost(), getView().getPort());
		String[] params = getView().getParameterValues();
		ValidateUser_Input input = new ValidateUser_Input(params[0],params[1]);
		getView().setRequest(input.toString());
		ValidateUser_Output output = sc.validateUser(input);
		if(output == null){
			getView().setResponse("FAILED\n");
		}
		else if(output.isValid()){
			getView().setResponse("TRUE\n" + output.toString());
		}
		else{
			getView().setResponse("FALSE\n");
		}
	}
	
	private void getProjects() {
		sc.setHostandPort(getView().getHost(), getView().getPort());
		String[] params = getView().getParameterValues();
		GetProjects_Input input = new GetProjects_Input(params[0],params[1]);
		getView().setRequest(input.toString());
		GetProjects_Output output = sc.getProjects(input);
		if(output != null){
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILDED\n");
		}
	}
	
	private void getSampleImage() {
		sc.setHostandPort(getView().getHost(), getView().getPort());
		String[] params = getView().getParameterValues();
		int projectID = Integer.parseInt(params[2]);
		GetSampleImage_Input input = new GetSampleImage_Input(params[0],params[1],projectID);
		getView().setRequest(input.toString());
		GetSampleImage_Output output = sc.getSampleImage(input);
		if(output != null){
			output.getImage().addURLPrefix(getView().getHost(),getView().getPort());
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILDED\n");
		}
	}
	
	private void downloadBatch() {
		sc.setHostandPort(getView().getHost(), getView().getPort());
		String[] params = getView().getParameterValues();
		int projectID = Integer.parseInt(params[2]);
		DownloadBatch_Input input = new DownloadBatch_Input(params[0],params[1],projectID);
		getView().setRequest(input.toString());
		DownloadBatch_Output output = sc.downloadBatch(input);
		if(output != null){
			output.getImage().addURLPrefix(getView().getHost(),getView().getPort());
			for(int i = 0; i < output.getFields().size(); i++){
				if(output.getFields().get(i).getKnownData() != null) {
					output.getFields().get(i).addURLPrefixToKnownData(getView().getHost(),getView().getPort());
				}
				output.getFields().get(i).addURLPrefixToHelpHTML(getView().getHost(),getView().getPort());
			}
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILDED\n");
		}
	}
	
	private void getFields() {
		sc.setHostandPort(getView().getHost(), getView().getPort());
		String[] params = getView().getParameterValues();
		int projectID = 0;
		if(params[2] != ""){
			projectID = Integer.parseInt(params[2]);
		}
		else{
			projectID = -1;
		}
		GetFields_Input input = new GetFields_Input(params[0],params[1],projectID);
		getView().setRequest(input.toString());
		GetFields_Output output = sc.getFields(input);
		if(output != null){
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILDED\n");
		}
	}
	
	private void submitBatch() {
		sc.setHostandPort(getView().getHost(), getView().getPort());
		String[] params = getView().getParameterValues();
		int imageID = Integer.parseInt(params[2]);
		ArrayList<String> values = new  ArrayList<String>(Arrays.asList(params[3].split(",")));
		SubmitBatch_Input input = new SubmitBatch_Input(imageID,values,params[0],params[1]);
		getView().setRequest(input.toString());
		SubmitBatch_Output output = sc.submitBatch(input);
		getView().setResponse(output.toString());

	}
	
	private void search() {
		sc.setHostandPort(getView().getHost(), getView().getPort());
		String[] params = getView().getParameterValues();
		ArrayList<String> fields = new ArrayList<String>(Arrays.asList(params[2].split(",")));
		ArrayList<String> values = new ArrayList<String>(Arrays.asList(params[3].split(",")));
		ArrayList<String> valuesLow = new ArrayList<String>();
		for(int i = 0; i < values.size(); i++){
			valuesLow.add(values.get(i).toLowerCase());
		}
		ArrayList<Integer> fieldsIds = new ArrayList<Integer>();
		for(int i = 0; i < fields.size(); i++){
			int Id = Integer.parseInt(fields.get(i));
			fieldsIds.add(Id);
		}
		Search_Input input = new Search_Input(params[0],params[1],fieldsIds,valuesLow);
		getView().setRequest(input.toString());
		Search_Output output = sc.search(input);
		if(output != null){
			for(int i = 0; i < output.getImages().size(); i++){
				output.getImages().get(i).addURLPrefix(getView().getHost(),getView().getPort());
			}
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILDED\n");
		}
	}
}

