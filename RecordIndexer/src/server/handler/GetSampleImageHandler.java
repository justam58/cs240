package server.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.GetSampleImage_Input;
import shared.communication.GetSampleImage_Output;
import shared.model.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetSampleImageHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xStream = new XStream(new DomDriver());
		
		GetSampleImage_Input input = (GetSampleImage_Input) xStream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		int projectID = input.getProjectID();
		
		db.startTransaction();
		User user = db.getUsers().getUser(username);
		GetSampleImage_Output output = null;
		if(user != null && user.getPassword().equals(password)){
			ArrayList<Image> images = db.getImages().getImagesByProjectID(projectID);
			if(images.size() != 0){
				output = new GetSampleImage_Output(images.get(0));
				exchange.sendResponseHeaders(200, 0);
				OutputStream os = exchange.getResponseBody();
				xStream.toXML(output, os);
				os.close();
			}
			else{
				exchange.sendResponseHeaders(404, 0);
				System.out.println("incorrect projectID/ the project has no images");
			}
		}
		else{
			exchange.sendResponseHeaders(404, 0);
			System.out.println("incorrect user input");
		}
		db.endTransaction(false);
	}

}
