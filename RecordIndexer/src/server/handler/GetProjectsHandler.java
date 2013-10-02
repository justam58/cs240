package server.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.GetProjects_Input;
import shared.communication.GetProjects_Output;
import shared.model.Project;
import shared.model.User;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetProjectsHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xStream = new XStream(new DomDriver());
		
		GetProjects_Input input = (GetProjects_Input) xStream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		
		db.startTransaction();
		User user = db.getUsers().getUser(username);
		GetProjects_Output output = null;
		if(user != null && user.getPassword().equals(password)){
			ArrayList<Project> projects = db.getProjects().getAll();
			output = new GetProjects_Output(projects);
			exchange.sendResponseHeaders(200, 0);
		}
		else{
			exchange.sendResponseHeaders(404, 0);
			System.out.println("incorrect user input");
		}
		OutputStream os = exchange.getResponseBody();
		xStream.toXML(output, os);
		os.close();
		db.endTransaction(false);
	}

}
