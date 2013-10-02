package server.database;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.*;
import shared.model.*;


public class ProjectsTest {

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
	private Projects dbProjects;

	@Before
	public void setUp() throws Exception {

		// Delete all Projects from the database	
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
		dbProjects = db.getProjects();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		dbProjects = null;
	}
	
	@Test
	public void testGetAll(){
		
		ArrayList<Project> result = (ArrayList<Project>) dbProjects.getAll();
		assertEquals(0,result.size());
	}
	
	@Test
	public void testGetPorject(){
		
		Project result = dbProjects.getProject(3);
		assertNull(result);
	}

	@Test
	public void testAdd(){
		
		Project test = new Project(-1,"title",3,5,9);
		dbProjects.add(test);
		
		ArrayList<Project> result = dbProjects.getAll();
		assertEquals(1,result.size());
		assertTrue(test.equals(result.get(0)));
		
	}

	@Test
	public void testUpdate(){
		
		Project test = new Project(-1,"title",3,5,9);
		dbProjects.add(test);
		
		test.setTitle("new");
		test.setFirstYCoord(33);
		test.setRecordHeight(11);
		test.setRecordsPerImage(66);
		
		dbProjects.update(test);
		
		ArrayList<Project> result = (ArrayList<Project>) dbProjects.getAll();
		assertEquals(1,result.size());
		Project result2 = dbProjects.getProject(1);
		assertTrue(test.equals(result2));

	}

}