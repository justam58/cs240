package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shared.model.Record;

/** Database Class that access to the Records */
public class Records {
	private static Logger logger;
	
	static {
		logger = Logger.getLogger("Database");
	}

	private Database db;
	
	/** Constructor
	 * 
	 * @param db	Database
	 * */
	public Records(Database db) {
		this.db = db;
	}
	
	/** get all the records
	 * 
	 * @return	a list of records
	 * @throws ServerException
	 * @throws SQLException 
	 * */
	public List<Record> getAll() throws SQLException {	
		
		logger.entering("server.database.Records", "getAll");
		
		// TODO: Use db's connection to query all records from the database and return them
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Record> records = new ArrayList<Record>();
		try {
		    String sql = "select RecordID, LastName, FirstName, Gender, Age, Ethnicity, ImageID from Records";
		    stmt = db.getConnection().prepareStatement(sql);

		    rs = stmt.executeQuery();
		    while (rs.next()) {
		    	Record newRecord = new Record(rs.getInt(0),rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getInt(6));
		    	records.add(newRecord);
		    }
		}
		catch (SQLException e) {
			Throwable error = new Throwable("can't execute connect");
			logger.throwing("server.database.Records", "getAll", error);
		}
		finally {
		    if (rs != null) rs.close();
		    if (stmt != null) stmt.close();
		}
		logger.fine("Use db's connection to query all records from the database and return them");
		
		logger.exiting("server.database.Records", "getAll");
		
		return records;
	}
	
	/** add a record
	 * 
	 * @throws ServerException
	 * @throws SQLException 
	 * */
	public void add(Record record) throws SQLException {

		logger.entering("server.database.Records", "add");
		
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try {
		    String sql = "insert into Records (LastName, FirstName, Gender, Age, Ethnicity, ImageID) values (?, ?, ?, ?, ?, ?)";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setString(1, record.getLastName());
		    stmt.setString(2, record.getFirstName());
		    stmt.setString(3, record.getGender());
		    stmt.setInt(4, record.getAge());
		    stmt.setString(5, record.getEthnicity());
		    stmt.setInt(6, record.getImageID());

		    if (stmt.executeUpdate() == 1) {
		        keyStmt = db.getConnection().createStatement();
		        keyRS = keyStmt.executeQuery("select last_insert_rowid()");
		        keyRS.next();
		        int id = keyRS.getInt(1);   // ID of the new book
		        record.setRecordID(id);
		    }
		    else{
				Throwable error = new Throwable("can't execute add");
				logger.throwing("server.database.Records", "add", error);
		    }
		}
		catch (SQLException e) {
			Throwable error = new Throwable("can't connect");
			logger.throwing("server.database.Records", "add", error);
		}
		finally {
		    if (stmt != null) stmt.close();
		    if (keyRS != null) keyRS.close();
		    if (keyStmt != null) keyStmt.close();
		}
		logger.fine("TODO: Use db's connection to add a new record to the database");
		
		logger.exiting("server.database.Records", "add");
	}
	
	/** update a record
	 * 
	 * @throws ServerException
	 * @throws SQLException 
	 * */
	public void update(Record record) throws SQLException {

		logger.entering("server.database.Records", "update");
		
		PreparedStatement stmt = null;
		try {
		    String sql = "update Records " + 
		                 "set LastName = ?, FirstName = ?, Gender = ?, Age = ?, Ethnicity = ?, ImageID = ? " +
		                 "where UserID = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setString(1, record.getLastName());
		    stmt.setString(2, record.getFirstName());
		    stmt.setString(3, record.getGender());
		    stmt.setInt(4, record.getAge());
		    stmt.setString(5, record.getEthnicity());
		    stmt.setInt(6, record.getImageID());
		    stmt.setInt(7, record.getRecordID());

		    if (stmt.executeUpdate() == 1){
		    	// Commit = true
		    }
		    else{
				Throwable error = new Throwable("can't execute update");
				logger.throwing("server.database.Records", "update", error);
		    }
		}
		catch (SQLException e) {
			Throwable error = new Throwable("can't connect");
			logger.throwing("server.database.Records", "update", error);
		}
		finally {
		    if (stmt != null) stmt.close();
		}
		logger.fine("Use db's connection to update record in the database");
		
		logger.exiting("server.database.Records", "update");
	}
	
	/** delete a record
	 * 
	 * @throws ServerException
	 * */
	public void delete(Record record) {

		logger.entering("server.Records", "update");
	
		// TODO: Use db's connection to delete contact from the database
		logger.fine("TODO: Use db's connection to delete contact from the database");
		
		logger.exiting("server.Records", "update");
	}
}

