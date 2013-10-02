package client;

public class ClientUnitTests {
	
	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"client.communication.ServerCommunicatorTest",
				"client.spell.CheckerTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}


