package server.handler;

import java.io.IOException;
import java.io.OutputStream;

import server.database.Database;
import shared.communication.*;
import shared.model.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ValidateUserHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xStream = new XStream(new DomDriver());
		ValidateUser_Input input = (ValidateUser_Input) xStream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		db.startTransaction();
		User result = db.getUsers().getUser(username);
		ValidateUser_Output output = null;
		if(result != null && result.getPassword().equals(password)){
			output = new ValidateUser_Output(result,true);
			exchange.sendResponseHeaders(200, 0);
		}
		else{
			output = new ValidateUser_Output(null,false);
			exchange.sendResponseHeaders(406, 0);
			System.out.println("incorrect password");
		}
		OutputStream os = exchange.getResponseBody();
		xStream.toXML(output, os);
		os.close();
		db.endTransaction(false);
	}

}
