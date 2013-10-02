package parsing;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import server.database.Database;
import shared.model.*;

public class DOMparser {
	
	private static Database db = new Database();
	private static String URLPrefix;
	private static int imageID;
	private static int projectID;
	
	public static void main(String[] args) throws Exception {

		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		File file = null;
		if((args[0]) == null){
			file = new File("Records.xml");
		}
		else{
			file = new File(args[0]);
		}

		Document doc = builder.parse(file);
		
		Database.initialize();
		cleanDatabase();
		getURLPrefix();
		imageID = 0;
		projectID = 0;
		NodeList users = doc.getElementsByTagName("user");
		parseUser(users);
		NodeList projects = doc.getElementsByTagName("project");
		parseProject(projects);
								
	}
	
	public static void getURLPrefix(){	
		try {
			InetAddress address = InetAddress.getLocalHost();
			String localHost = address.getHostAddress();
			URLPrefix = "http://" + localHost + ":8080/";
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void cleanDatabase() throws SQLException, FileNotFoundException{	
		File srcFile = new File("RecordIndexerSchema.txt");
		Scanner sc = new Scanner(srcFile);
		String sql = "";
		while(sc.hasNextLine()){
			sql += sc.nextLine() + "\n";
		}
		sc.close();
		
		db.startTransaction();
		db.getConnection().createStatement().executeUpdate(sql);
		db.endTransaction(true);
	}
	
	public static void parseUser(NodeList users){

		for (int i = 0; i < users.getLength(); i++) {
			Element userElem = (Element)users.item(i);

			Element usernameElem = (Element)userElem.getElementsByTagName("username").item(0);
			Element passwordElem = (Element)userElem.getElementsByTagName("password").item(0);
			Element firstnameElem = (Element)userElem.getElementsByTagName("firstname").item(0);
			Element lastnameElem = (Element)userElem.getElementsByTagName("lastname").item(0);
			Element emailElem = (Element)userElem.getElementsByTagName("email").item(0);
			Element indexedrecordsElem = (Element)userElem.getElementsByTagName("indexedrecords").item(0);

			String username = usernameElem.getTextContent();
			String password = passwordElem.getTextContent();
			String firstname = firstnameElem.getTextContent();
			String lastname = lastnameElem.getTextContent();
			String email = emailElem.getTextContent();
			String indexedrecords = indexedrecordsElem.getTextContent();
			int indexedrecordsInt = Integer.parseInt(indexedrecords);
			
			User newUser = new User(-1,username,password,email,firstname,lastname,indexedrecordsInt);
			db.startTransaction();
			db.getUsers().add(newUser);
			db.endTransaction(true);
		}
	}
	
	public static void parseProject(NodeList projects){
		for (int i = 0; i < projects.getLength(); i++) {
			Element projectElem = (Element)projects.item(i);
			
			Element titleElem = (Element)projectElem.getElementsByTagName("title").item(0);
			Element recordsperimageElem = (Element)projectElem.getElementsByTagName("recordsperimage").item(0);
			Element firstycoordElem = (Element)projectElem.getElementsByTagName("firstycoord").item(0);
			Element recordheightElem = (Element)projectElem.getElementsByTagName("recordheight").item(0);
			
			
			String title = titleElem.getTextContent();
			String recordsperimage = recordsperimageElem.getTextContent();
			String firstycoord = firstycoordElem.getTextContent();
			String recordheight = recordheightElem.getTextContent();
			int recordsperimageInt = Integer.parseInt(recordsperimage);
			int firstycoordInt = Integer.parseInt(firstycoord);
			int recordheightInt = Integer.parseInt(recordheight);
			
			Project newProject = new Project(-1,title,recordsperimageInt,firstycoordInt,recordheightInt);
			db.startTransaction();
			db.getProjects().add(newProject);
			db.endTransaction(true);
			projectID++;
			
			//parse fields
			ArrayList<Field> fieldsObjects = null;
			Element fieldElem = (Element)projectElem.getElementsByTagName("field").item(0);
			if(fieldElem != null){
				NodeList fields = projectElem.getElementsByTagName("field");
				fieldsObjects = parseField(fields);
			}
			//parse images
			Element imageElem = (Element)projectElem.getElementsByTagName("field").item(0);
			if(imageElem != null){
				NodeList images = projectElem.getElementsByTagName("image");
				parseImage(images,fieldsObjects);
			}
		}
	}
	
	public static ArrayList<Field> parseField(NodeList fields){
		for (int i = 0; i < fields.getLength(); i++) {
			Element fieldElem = (Element)fields.item(i);
			
			Element titleElem = (Element)fieldElem.getElementsByTagName("title").item(0);
			Element xcoordElem = (Element)fieldElem.getElementsByTagName("xcoord").item(0);
			Element widthElem = (Element)fieldElem.getElementsByTagName("width").item(0);
			Element helphtmlElem = (Element)fieldElem.getElementsByTagName("helphtml").item(0);
			Element knowndataElem = (Element)fieldElem.getElementsByTagName("knowndata").item(0);
			
			
			String title = titleElem.getTextContent();
			String xcoord = xcoordElem.getTextContent();
			String width = widthElem.getTextContent();
			String helphtml = URLPrefix + helphtmlElem.getTextContent();
			String knowndata = null;
			if(knowndataElem != null){
				knowndata = URLPrefix + knowndataElem.getTextContent();
			}
			int widthInt = Integer.parseInt(width);
			int xcoordInt = Integer.parseInt(xcoord);
			
			Field newField = new Field(-1,title,xcoordInt,widthInt,helphtml,knowndata,projectID);
			db.startTransaction();
			db.getFields().add(newField);
			db.endTransaction(true);
		}
		db.startTransaction();
		ArrayList<Field> fieldsObjects = (ArrayList<Field>) db.getFields().getFieldsByProjectID(projectID);
		db.endTransaction(true);
		return fieldsObjects;
	}
	
	public static void parseImage(NodeList images, ArrayList<Field> fieldsObjects){
		for (int i = 0; i < images.getLength(); i++) {
			Element imageElem = (Element)images.item(i);
			
			Element fileElem = (Element)imageElem.getElementsByTagName("file").item(0);
			
			String file = URLPrefix + fileElem.getTextContent();
			
			Image newImage = new Image(-1,file,projectID,-1);
			db.startTransaction();
			db.getImages().add(newImage);
			db.endTransaction(true);
			imageID++;
			
			//parse records
			Element recordsElem = (Element)imageElem.getElementsByTagName("records").item(0);
			if(recordsElem != null){
				NodeList records = imageElem.getElementsByTagName("record");
				parseRecord(records,fieldsObjects);
			}
		}
	}
	
	public static void parseRecord(NodeList records, ArrayList<Field> fieldsObjects){
		for (int i = 0; i < records.getLength(); i++) {
			Element imageElem = (Element)records.item(i);
			ArrayList<Integer> fieldIds = new ArrayList<Integer>();
			for(int j = 0; j < fieldsObjects.size(); j++){
				fieldIds.add(fieldsObjects.get(j).getFieldID());
			}
			
			//parse values
			NodeList values = imageElem.getElementsByTagName("value");
			parseValue(values,fieldIds,i+1);

		}
	}
	
	public static void parseValue(NodeList values, ArrayList<Integer> fieldIds, int row){
		int count = 0;
		for (int i = 0; i < values.getLength(); i++) {
			Element valueElem = (Element)values.item(i);
		
			String info = valueElem.getTextContent().toLowerCase();
			Value newValue = new Value(info,-1,imageID,fieldIds.get(count),row);
			db.startTransaction();
			db.getValues().add(newValue);
			db.endTransaction(true);
			count++;
			if(count == fieldIds.size()){
				count = 0;
			}
		}
	}
}
