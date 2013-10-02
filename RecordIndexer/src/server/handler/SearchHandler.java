package server.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.Search_Input;
import shared.communication.Search_Output;
import shared.model.Image;
import shared.model.User;
import shared.model.Value;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SearchHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xStream = new XStream(new DomDriver());
		
		Search_Input input = (Search_Input) xStream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		ArrayList<Integer> fields = input.getFieldIDs();
		ArrayList<String> searchValues = input.getSearchValues();
		
		db.startTransaction();
		User user = db.getUsers().getUser(username);
		Search_Output output = null;
		if(user != null && user.getPassword().equals(password)){
			for(int i = 0; i < fields.size(); i++){
				if(db.getFields().getField(fields.get(i)) == null){
					exchange.sendResponseHeaders(404, 0);
					db.endTransaction(false);
					System.out.println("incorrect fieldID");
					return;
				}
			}
			ArrayList<Value> result = new ArrayList<Value>();
			for(int i = 0; i < fields.size(); i++){
				for(int j = 0; j < searchValues.size(); j++){
					ArrayList<Value> values = db.getValues().getValues(fields.get(i), searchValues.get(j));
					if(values.size() != 0){
						result.addAll(values);
					}
				}
			}
			if(result.size() == 0){
				exchange.sendResponseHeaders(404, 0);
				db.endTransaction(false);
				System.out.println("no value found");
				return;
			}
			ArrayList<Image> images = new ArrayList<Image>();
			for(int i = 0; i < result.size(); i++){
				int imageID = result.get(i).getImageID();
				Image image = db.getImages().getImage(imageID);
				images.add(image);
			}
			exchange.sendResponseHeaders(200, 0);
			output = new Search_Output(result,images);
			OutputStream os = exchange.getResponseBody();
			xStream.toXML(output, os);
			os.close();
		}
		else{
			exchange.sendResponseHeaders(404, 0);
			System.out.println("incorrect user input");
		}
		db.endTransaction(false);
	}

}
