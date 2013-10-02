package server.database;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.*;
import shared.model.*;


public class ImagesTest {

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
	private Images dbImages;

	@Before
	public void setUp() throws Exception {

		// Delete all Images from the database	
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
		dbImages = db.getImages();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(true);
		db = null;
		dbImages = null;
	}
	
	@Test
	public void testGetImagesByProjectID(){
		
		ArrayList<Image> result = dbImages.getImagesByProjectID(3);
		assertEquals(0,result.size());
		
		Image test1 = new Image(-1,"title",3,5);
		dbImages.add(test1);
		Image test2 = new Image(-1,"title",6,-1);
		dbImages.add(test2);
		Image test3 = new Image(-1,"title",6,3);
		dbImages.add(test3);
		ArrayList<Image> result2 = dbImages.getImagesByProjectID(6);
		assertEquals(2,result2.size());
	}
	
	@Test
	public void testGetUserImage(){
		
		Image result = dbImages.getUserImage(3);
		assertNull(result);
		
		Image test = new Image(-1,"title",3,5);
		dbImages.add(test);
		Image result2 = dbImages.getUserImage(5);
		assertNotNull(result2);
	}
	
	@Test
	public void testGetImage(){
		
		Image result = dbImages.getImage(3);
		assertNull(result);
	}

	@Test
	public void testAdd(){
		
		Image test = new Image(1,"title",3,5);
		dbImages.add(test);
		
		Image result = dbImages.getImage(1);
		assertTrue(test.equals(result));
		
		// check if userid is unique
		Image test2 = new Image(1,"title",3,5);
		dbImages.add(test2);
		
		Image result2 = dbImages.getImage(1);
		assertTrue(test.equals(result2));
		
	}

	@Test
	public void testUpdate(){
		
		Image test = new Image(1,"title",3,5);
		dbImages.add(test);
		
		test.setFile("new");
		test.setProjectID(33);
		test.setUserID(6);
		dbImages.update(test);
		
		Image result = dbImages.getImage(1);
		assertTrue(test.equals(result));

	}


}
