package server.handler;

import java.io.*;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.*;
import shared.model.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SubmitBatchHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xStream = new XStream(new DomDriver());
		
		SubmitBatch_Input input = (SubmitBatch_Input) xStream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		int imageID = input.getImageID();
		ArrayList<String> values = input.getValues();
		
		db.startTransaction();
		User user = db.getUsers().getUser(username);
		Image image = (Image) db.getImages().getUserImage(user.getUserID());
		if(user != null && user.getPassword().equals(password) && image != null && imageID == image.getImageID()){
			int projectID =image.getProjectID();
			ArrayList<Field> fields = db.getFields().getFieldsByProjectID(projectID);
			if((values.size()/fields.size()) % 1 != 0){
				exchange.sendResponseHeaders(404, 0);
				db.endTransaction(false);
				System.out.println("incorrect value numbers");
				return;
			}
			int records = values.size()/fields.size();
			if(records > db.getProjects().getProject(image.getProjectID()).getRecordsPerImage()){
				exchange.sendResponseHeaders(404, 0);
				db.endTransaction(false);
				System.out.println("too many values");
				return;
			}
			int fieldsCount = 0;
			int recordsCount = 0;
			for(int i = 0; i < values.size(); i++){
				Value newValue = new Value(values.get(i), -1, imageID, 
											fields.get(fieldsCount).getFieldID(), recordsCount+1);
				db.getValues().add(newValue);
				fieldsCount++;
				if(fieldsCount == fields.size()){
					recordsCount++;
					fieldsCount = 0;
				}
			}

			SubmitBatch_Output output = new SubmitBatch_Output(true);
			exchange.sendResponseHeaders(200, 0);
			OutputStream os = exchange.getResponseBody();
			xStream.toXML(output, os);
			os.close();
			image.setUserID(0);
			user.incementIndexedRecords(records);
			db.getImages().update(image);
			db.getUsers().update(user);
			db.endTransaction(true);
		}
		else{
			exchange.sendResponseHeaders(404, 0);
			db.endTransaction(false);
			System.out.println("incorrect user input / user doesnt own a image/incorrect imageID");
		}
	}
}
