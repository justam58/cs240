package server.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.GetFields_Input;
import shared.communication.GetFields_Output;
import shared.model.Field;
import shared.model.User;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetFieldsHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xStream = new XStream(new DomDriver());
		
		GetFields_Input input = (GetFields_Input) xStream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		int ProjectID = input.getProjectID();
		
		db.startTransaction();
		User user = db.getUsers().getUser(username);
		GetFields_Output output = null;
		if(user != null && user.getPassword().equals(password)){
			ArrayList<Field> fields = new ArrayList<Field>();
			if(ProjectID == -1){
				fields = db.getFields().getAll();
			}
			else{
				fields = db.getFields().getFieldsByProjectID(ProjectID);
			}
			if(fields.size() == 0 ){
				exchange.sendResponseHeaders(404, 0);
				System.out.println("incorrect projectID/ the project has no fields");
			}
			else{
				exchange.sendResponseHeaders(200, 0);
				output = new GetFields_Output(fields);
				OutputStream os = exchange.getResponseBody();
				xStream.toXML(output, os);
				os.close();
			}
		}
		else{
			exchange.sendResponseHeaders(404, 0);
			System.out.println("incorrect user input");
		}
		db.endTransaction(false);
	}

}
