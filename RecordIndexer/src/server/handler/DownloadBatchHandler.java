package server.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.DownloadBatch_Input;
import shared.communication.DownloadBatch_Output;
import shared.model.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DownloadBatchHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xStream = new XStream(new DomDriver());
		
		DownloadBatch_Input input = (DownloadBatch_Input) xStream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		int projectID = input.getProjectID();
		
		db.startTransaction();
		User user = db.getUsers().getUser(username);
		DownloadBatch_Output output = null;
		if(user != null && user.getPassword().equals(password)){
			Image UserImage = db.getImages().getUserImage(user.getUserID());
			ArrayList<Image> images = db.getImages().getImagesByProjectID(projectID);
			if(UserImage == null && images.size() != 0){
				Image image = null;
				boolean gotIt = false;
				for(int i = 0; i < images.size(); i++){
					if((images.get(i).getUserID() == -1) && (gotIt == false)){
						image = images.get(i);
						gotIt = true;
					}
				}
				if(image != null){
					Project project = db.getProjects().getProject(projectID);
					ArrayList<Field> fields = db.getFields().getFieldsByProjectID(projectID);
					output = new DownloadBatch_Output(image, project, fields);
					exchange.sendResponseHeaders(200, 0);
					OutputStream os = exchange.getResponseBody();
					xStream.toXML(output, os);
					os.close();
					image.setUserID(user.getUserID());
					db.getImages().update(image);
					db.endTransaction(true);
				}
				else{
					exchange.sendResponseHeaders(404, 0);
					db.endTransaction(false);
					System.out.println("no available image");
				}
			}
			else{
				exchange.sendResponseHeaders(404, 0);
				db.endTransaction(false);
				System.out.println("user doesnt own the image");
			}
		}
		else{
			exchange.sendResponseHeaders(404, 0);
			db.endTransaction(false);
			System.out.println("incorrect user input");
		}
	}

}
