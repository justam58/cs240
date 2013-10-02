package server;

public class ServerUnitTests {
	
	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"server.database.DatabaseTest",
				"server.database.UsersTest",
				"server.database.ValuesTest",
				"server.database.ProjectsTest",
				"server.database.ImagesTest",
				"server.database.FieldsTest",
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}

}
