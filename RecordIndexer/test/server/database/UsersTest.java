package server.database;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Scanner;

import org.junit.*;
import shared.model.*;


public class UsersTest {

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
	private Users dbUsers;

	@Before
	public void setUp() throws Exception {

		// Delete all Users from the database	
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
		dbUsers = db.getUsers();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		dbUsers = null;
	}
	
	@Test
	public void testGetUser(){
		
		User result = dbUsers.getUser("paula");
		assertNull(result);
	}

	@Test
	public void testAdd(){
		
		User test = new User(1,"justam","pswrd","paula.chenn@gamil","Paula","Chen");
		dbUsers.add(test);
		
		User result = dbUsers.getUser("justam");
		assertTrue(test.equals(result));
		
	}

	@Test
	public void testUpdate(){
		
		User test = new User(1,"justam","pswrd","paula.chenn@gamil","Paula","Chen",6);
		
		dbUsers.add(test);
		
		test.setFirstName("Robert White");
		test.setLastName("000-000-0000");
		test.setEmail("robert@white.org");
		test.setPassword("http://www.white.org/robert");
		test.setIndexedRecords(30);
		
		dbUsers.update(test);
		
		User result = dbUsers.getUser("justam");
		assertTrue(test.equals(result));

	}

}