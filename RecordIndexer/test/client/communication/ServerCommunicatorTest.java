package client.communication;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



import importer.DOMparser;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.*;

import server.database.Database;
import shared.model.*;
import shared.communication.*;

public class ServerCommunicatorTest {
	
	private static ServerCommunicator server;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// get host
		String localHost = null;
		try {
			InetAddress address = InetAddress.getLocalHost();
			localHost = address.getHostAddress();
			
		} catch (UnknownHostException e) {
			System.out.println("Can't get local host");
			e.printStackTrace();
		}
		server = new ServerCommunicator(localHost,"8080");
	}

	@Before
	public void setUp() throws Exception {
		DOMparser.main(null);
	}
	
	@Test
	public void testValidateUser(){
		// test correct input
		ValidateUser_Input input = new ValidateUser_Input("test1","test1");
		ValidateUser_Output output = server.validateUser(input);
		User user = new User(1,"test1","test1","test1@gmail.com","Test","One",0);
		ValidateUser_Output answer = new ValidateUser_Output(user,true);
		assertTrue(output.isValid());
		assertTrue(output.equals(answer));
		
		// test incorrect username
		ValidateUser_Input input2 = new ValidateUser_Input("justam","wrong");
		ValidateUser_Output output2 = server.validateUser(input2);
		assertFalse(output2.isValid());
		assertNull(output2.getUser());
	
		// test incorrect password
		ValidateUser_Input input3 = new ValidateUser_Input("test1","wrong");
		ValidateUser_Output output3 = server.validateUser(input3);
		assertFalse(output3.isValid());
		assertNull(output3.getUser());
	}
	
	@Test
	public void testGetProjects(){
		// test correct input
		GetProjects_Input input = new GetProjects_Input("test1","test1");
		GetProjects_Output output = server.getProjects(input);
		ArrayList<Project> projects = new ArrayList<Project>();
		projects.add(new Project(1,"1890 Census",8,199,60));
		projects.add(new Project(2,"1900 Census",10,204,62));
		projects.add(new Project(3,"Draft Records",7,195,65));
		GetProjects_Output answer = new GetProjects_Output(projects);
		assertTrue(output.equals(answer));
		
		// test incorrect user
		GetProjects_Input input2 = new GetProjects_Input("test1","wrong");
		GetProjects_Output output2 = server.getProjects(input2);
		assertNull(output2);
	}
	
	@Test
	public void testGetFields(){

		// test correct input with projectID
		GetFields_Input input = new GetFields_Input("test1","test1",2);
		GetFields_Output output = server.getFields(input);
		ArrayList<Field> fields = new ArrayList<Field>();
		fields.add(new Field(5,"Gender",45,205,"" +
								"fieldhelp/gender.html","knowndata/genders.txt",2));
		fields.add(new Field(6,"Age",250,120,"fieldhelp/age.html",null,2));
		fields.add(new Field(7,"Last Name",370,325,
								"fieldhelp/last_name.html","knowndata/1900_last_names.txt",2));
		fields.add(new Field(8,"First Name",695,325,
								"fieldhelp/first_name.html","knowndata/1900_first_names.txt",2));
		fields.add(new Field(9,"Ethnicity",1020,450,
								"fieldhelp/ethnicity.html","knowndata/ethnicities.txt",2));
		GetFields_Output answer = new GetFields_Output(fields);
		assertTrue(output.equals(answer));
		
		// test correct input without ProjectID
		GetFields_Input input2 = new GetFields_Input("test1","test1",-1);
		GetFields_Output output2 = server.getFields(input2);
		ArrayList<Field> fields2 = new ArrayList<Field>();
		fields2.add(new Field(1,"Last Name",60,300,
								"fieldhelp/last_name.html","knowndata/1890_last_names.txt",1));
		fields2.add(new Field(2,"First Name",360,280,
								"fieldhelp/first_name.html","knowndata/1890_first_names.txt",1));
		fields2.add(new Field(3,"Gender",640,205,
								"fieldhelp/gender.html","knowndata/genders.txt",1));
		fields2.add(new Field(4,"Age",845,120,
								"fieldhelp/age.html",null,1));
		fields2.add(new Field(5,"Gender",45,205,
								"fieldhelp/gender.html","knowndata/genders.txt",2));
		fields2.add(new Field(6,"Age",250,120,"fieldhelp/age.html",null,2));
		fields2.add(new Field(7,"Last Name",370,325,
								"fieldhelp/last_name.html","knowndata/1900_last_names.txt",2));
		fields2.add(new Field(8,"First Name",695,325,
								"fieldhelp/first_name.html","knowndata/1900_first_names.txt",2));
		fields2.add(new Field(9,"Ethnicity",1020,450,
								"fieldhelp/ethnicity.html","knowndata/ethnicities.txt",2));
		fields2.add(new Field(10,"Last Name",75,325,
								"fieldhelp/last_name.html","knowndata/draft_last_names.txt",3));
		fields2.add(new Field(11,"First Name",400,325,"fieldhelp/first_name.html","knowndata/draft_first_names.txt",3));
		fields2.add(new Field(12,"Age",725,120,
								"fieldhelp/age.html",null,3));
		fields2.add(new Field(13,"Ethnicity",845,450,
								"fieldhelp/ethnicity.html","knowndata/ethnicities.txt",3));
		GetFields_Output answer2 = new GetFields_Output(fields2);
		assertTrue(output2.equals(answer2));
		
		// test incorrect user
		GetFields_Input input3 = new GetFields_Input("test1","wrong",2);
		GetFields_Output output3 = server.getFields(input3);
		assertNull(output3);
	
		// test incorrect projectID
		GetFields_Input input4 = new GetFields_Input("test1","test1",5);
		GetFields_Output output4 = server.getFields(input4);
		assertNull(output4);
	}
	
	
	@Test
	public void testGetSampleImage(){
		// test correct input
		GetSampleImage_Input input = new GetSampleImage_Input("test1","test1",1);
		GetSampleImage_Output output = server.getSampleImage(input);
		Image image = new Image(1,"images/1890_image0.png", 1, -1);
		GetSampleImage_Output answer = new GetSampleImage_Output(image);
		assertTrue(output.equals(answer));
		
		// test incorrect user
		GetSampleImage_Input input2 = new GetSampleImage_Input("test1","wrong",3);
		GetSampleImage_Output output2 = server.getSampleImage(input2);
		assertNull(output2);
		
  	// test incorrect projectID
		GetSampleImage_Input input3 = new GetSampleImage_Input("test1","test1",5);
		GetSampleImage_Output output3 = server.getSampleImage(input3);
		assertNull(output3);
	}
	
	@Test
	public void testSearch(){
		// test correct input
		ArrayList<Integer> fieldIDs = new ArrayList<Integer>();
		fieldIDs.add(1);
		fieldIDs.add(5);
		fieldIDs.add(10);
		fieldIDs.add(12);
		ArrayList<String> searchValues = new ArrayList<String>();
		searchValues.add("32");
		searchValues.add("fox");
		searchValues.add("dave");
		Search_Input input = new Search_Input("test1","test1",fieldIDs,searchValues);
		Search_Output output = server.search(input);
		ArrayList<Value> resultValues = new ArrayList<Value>();
		resultValues.add(new Value("fox",1,41,10,1));
		resultValues.add(new Value("dave",2,41,12,2));
		ArrayList<Image> resultImages = new ArrayList<Image>();
		resultImages.add(new Image(41,"images/draft_image0.png",3,-1));
		resultImages.add(new Image(41,"images/draft_image0.png",3,-1));
		Search_Output answer = new Search_Output(resultValues,resultImages);
		assertTrue(output.equals(answer));
		
		// test incorrect user
		Search_Input input2 = new Search_Input("test1","wrong",fieldIDs,searchValues);
		Search_Output output2 = server.search(input2);
		assertNull(output2);
		
		// test incorrect fieldId
		fieldIDs.add(20);
		Search_Input input3 = new Search_Input("test1","test1",fieldIDs,searchValues);
		Search_Output output3 = server.search(input3);
		assertNull(output3);
	}
	
	@Test
	public void testDownloadBatch(){
		// test correct input
		DownloadBatch_Input input = new DownloadBatch_Input("test1","test1",1);
		DownloadBatch_Output output = server.downloadBatch(input);
		Image image = new Image(1,"images/1890_image0.png", 1, -1);
		Project project = new Project(1,"1890 Census",8,199,60);
		ArrayList<Field> fields = new ArrayList<Field>();
		fields.add(new Field(1,"Last Name",60,300,
							"fieldhelp/last_name.html","knowndata/1890_last_names.txt",1));
		fields.add(new Field(2,"First Name",360,280,
							"fieldhelp/first_name.html","knowndata/1890_first_names.txt",1));
		fields.add(new Field(3,"Gender",640,205,"fieldhelp/gender.html","knowndata/genders.txt",1));
		fields.add(new Field(4,"Age",845,120,"fieldhelp/age.html",null,1));
		DownloadBatch_Output answer = new DownloadBatch_Output(image,project,fields);
		assertTrue(output.equals(answer));

		
		// test incorrect user
		DownloadBatch_Input input2 = new DownloadBatch_Input("test1","wrong",3);
		DownloadBatch_Output output2 = server.downloadBatch(input2);
		assertNull(output2);
		
		// test incorrect projectID
		DownloadBatch_Input input3 = new DownloadBatch_Input("test1","test1",5);
		DownloadBatch_Output output3 = server.downloadBatch(input3);
		assertNull(output3);
	}
	
	@Test
	public void testSubmitBatch(){
		// test correct input
		try {
			Database db = new Database();
			Database.initialize();
			db.startTransaction();
			db.getConnection().createStatement().executeUpdate("update Images set UserID = 1 where ImageID = 1");
			db.endTransaction(true);
		} catch (SQLException e) {
			System.out.println("Can't update database");
			e.printStackTrace();
		}
		ArrayList<String> values = new ArrayList<String>();
		values.add("aaa");
		values.add("bbb");
		values.add("ccc");
		values.add("ddd");
		SubmitBatch_Input input = new SubmitBatch_Input(1,values,"test1","test1");
		SubmitBatch_Output output = server.submitBatch(input);
		assertTrue(output.isValid());
		//check if they are in there
		ArrayList<Integer> fieldIds = new ArrayList<Integer>();
		fieldIds.add(1);
		fieldIds.add(2);
		fieldIds.add(3);
		fieldIds.add(4);
		Search_Input test = new Search_Input("test1","test1",fieldIds,values);
		Search_Output result = server.search(test);
		ArrayList<Value> resultValues = new ArrayList<Value>();
		resultValues.add(new Value("aaa",561,1,1,1));
		resultValues.add(new Value("bbb",562,1,2,1));
		resultValues.add(new Value("ccc",563,1,3,1));
		resultValues.add(new Value("ddd",564,1,4,1));
		ArrayList<Image> resultImages = new ArrayList<Image>();
		resultImages.add(new Image(1,"images/1890_image0.png", 1, 0));
		resultImages.add(new Image(1,"images/1890_image0.png", 1, 0));
		resultImages.add(new Image(1,"images/1890_image0.png", 1, 0));
		resultImages.add(new Image(1,"images/1890_image0.png", 1, 0));
		Search_Output answer = new Search_Output(resultValues,resultImages);
		assertTrue(result.equals(answer));
		//check user's indexed record numbers
		ValidateUser_Input test2 = new ValidateUser_Input("test1","test1");
		ValidateUser_Output result2 = server.validateUser(test2);
		User user = new User(1,"test1","test1","test1@gmail.com","Test","One",1);
		ValidateUser_Output answer2 = new ValidateUser_Output(user,true);
		assertTrue(result2.equals(answer2));
		
		
		// test incorrect user
		SubmitBatch_Input input2 = new SubmitBatch_Input(1,values,"test1","wrong");
		SubmitBatch_Output output2 = server.submitBatch(input2);
		assertNull(output2);
		
		// test doest own the batch
		SubmitBatch_Input input3 = new SubmitBatch_Input(2,values,"test1","test1");
		SubmitBatch_Output output3 = server.submitBatch(input3);
		assertNull(output3);
		
		// test wrong imageID
		SubmitBatch_Input input4 = new SubmitBatch_Input(200,values,"test1","test1");
		SubmitBatch_Output output4 = server.submitBatch(input4);
		assertNull(output4);	
		
		// test wrong number of values
		values.remove(0);
		SubmitBatch_Input input5 = new SubmitBatch_Input(1,values,"test1","test1");
		SubmitBatch_Output output5 = server.submitBatch(input5);
		assertNull(output5);
	}

}