package server.database;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.*;
import shared.model.*;


public class FieldsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Load database driver	
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
		
	private Database db;
	private Fields dbFields;

	@Before
	public void setUp() throws Exception {

		// Delete all Fields from the database	
		db = new Database();		
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

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		dbFields = db.getFields();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		dbFields = null;
	}
	
	@Test
	public void testGetFieldsByProjectID(){
		
		ArrayList<Field> result = dbFields.getFieldsByProjectID(3);
		assertEquals(0,result.size());
		
		Field test1 = new Field(-1,"title",5,7,"123","467",3);
		dbFields.add(test1);
		Field test2 = new Field(-1,"title",5,7,"123","467",7);
		dbFields.add(test2);
		Field test3 = new Field(-1,"title",5,7,"123","467",7);
		dbFields.add(test3);
		ArrayList<Field> result2 = dbFields.getFieldsByProjectID(7);
		assertEquals(2,result2.size());
	}
	
	@Test
	public void testGetField(){
		
		Field result = dbFields.getField(3);
		assertNull(result);
	}
	
	@Test
	public void testGetAll(){
		
		ArrayList<Field> result = dbFields.getAll();
		assertEquals(0,result.size());
	}

	@Test
	public void testAdd(){
		
		Field test = new Field(1,"title",5,7,"123","467",3);
		dbFields.add(test);
		
		ArrayList<Field> result = dbFields.getAll();
		assertEquals(1,result.size());
		assertTrue(test.equals(result.get(0)));
		
	}

	@Test
	public void testUpdate(){
		
		Field test = new Field(-1,"title",5,7,"123","467",3);
		dbFields.add(test);
		
		test.setTitle("new");
		test.setxCoord(33);
		test.setWidth(11);
		test.setHelpHTML("html");
		test.setKnownData("data");
		test.setProjectID(11);
		dbFields.update(test);
		
		ArrayList<Field> result = dbFields.getAll();
		assertEquals(1,result.size());
		assertTrue(test.equals(result.get(0)));

	}

}