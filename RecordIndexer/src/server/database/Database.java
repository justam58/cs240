package server.database;

import java.io.File;
import java.sql.*;


/** Database Class that connect to the database */
public class Database {
	
	/** load the database driver
	 * 
	 * @throws ServerException
	 * */
	public static void initialize(){	
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("Can't load database");
			e.printStackTrace();
		}
	}
	
	private Users users;
	private Projects projects;
	private Images images;
	private Fields fields;
	private Values values;
	private Connection connection;
	
	/** Constructor */
	public Database(){
		users = new Users(this);
		projects = new Projects(this);
		images = new Images(this);
		fields = new Fields(this);
		values = new Values(this);
		connection = null;
	}
	
	public Users getUsers() {
		return users;
	}

	public Projects getProjects() {
		return projects;
	}

	public Images getImages() {
		return images;
	}

	public Fields getFields() {
		return fields;
	}

	public Values getValues() {
		return values;
	}

	public Connection getConnection() {
		return connection;
	}
	
	/** Open a connection to the database and start a transaction
	 * 
	 * @throws ServerException
	 * */
	public void startTransaction(){
		String dbName = "database" + File.separator + "IndexerDatabase.sqlite";
		String connectionURL = "jdbc:sqlite:" + dbName;
		try{
		    // Open a database connection
			connection = DriverManager.getConnection(connectionURL);
		    
			// Start a transaction
			connection.setAutoCommit(false);
		}
		catch(SQLException e){
			System.out.println("Can't open a connection to the database and start a transaction");
			e.printStackTrace();
		}
	}
	
	/** Commit or rollback the transaction and close the connection
	 * 
	 * @throws SQLException 
	 * @throws ServerException
	 * */
	public void endTransaction(boolean commit){
		try{
		    if(commit){
		        connection.commit();
		    }
		    else{
		        connection.rollback();
		    }
		}
		catch(SQLException e){
			System.out.println("Can't commit or rollback the transaction and close the connection");
			e.printStackTrace();
		}
		finally{
		    try{
				connection.close();
			}
		    catch(SQLException e){
				System.out.println("Can't close the connection");
				e.printStackTrace();
			}
		}
		connection = null;
	}
}
