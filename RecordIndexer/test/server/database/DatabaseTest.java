package server.database;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Scanner;

import org.junit.*;

import shared.model.*;

public class DatabaseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Load database driver		
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}

	@Before
	public void setUp() throws Exception {

		// Delete all Users from the database	
		Database db = new Database();		
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

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCommit(){
		
		// Add a user
		addOneProject(true);
		
		// Start new transaction
		Database db = new Database();
		db.startTransaction();
		
		// Make sure the previous transaction was properly committed
		Project test = db.getProjects().getProject(1);
		db.endTransaction(true);
		
		assertNotNull(test);
	}

	@Test
	public void testRollback(){
		
		// Add a user
		addOneProject(false);
		
		// Start new transaction
		Database db = new Database();
		db.startTransaction();
		
		// Make sure the previous transaction was properly rolled back
		Project test = db.getProjects().getProject(1);
		db.endTransaction(true);

		assertNull(test);
	}
	
	private void addOneProject(boolean commit){
		
		// Start transaction	
		Database db = new Database();
		db.startTransaction();
		
		// Add a user
		Project test = new Project(-1,"title",3,5,9);
		
		db.getProjects().add(test);
		// End transaction	
		db.endTransaction(commit);	
		
	}

}

