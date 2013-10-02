package server.database;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.*;
import shared.model.*;


public class ValuesTest {

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
	private Values dbValues;

	@Before
	public void setUp() throws Exception {

		// Delete all Values from the database	
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
		dbValues = db.getValues();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		dbValues = null;
	}
	
	@Test
	public void testGetValues(){
		
		ArrayList<Value> result = dbValues.getValues(3,"abc");
		assertEquals(0,result.size());
	}

	@Test
	public void testAdd(){
		
		Value test = new Value("smart",1,9,6,7);
		dbValues.add(test);
		
		ArrayList<Value> result = dbValues.getValues(6,"smart");
		assertEquals(1,result.size());
		assertTrue(test.equals(result.get(0)));
		
	}

	@Test
	public void testUpdate(){
		
		Value test = new Value("smart",1,9,6,7);
		dbValues.add(test);
		
		test.setFieldID(4);
		test.setImageID(3);
		test.setRow(1);
		test.setInfo("asd");
		
		dbValues.update(test);
		
		ArrayList<Value> result1 = dbValues.getValues(6,"smart");
		ArrayList<Value> result2 =  dbValues.getValues(4,"asd");
		assertEquals(0,result1.size());
		assertEquals(1,result2.size());
		assertTrue(test.equals(result2.get(0)));

	}

}