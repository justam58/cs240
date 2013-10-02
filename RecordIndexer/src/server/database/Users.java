package server.database;

import java.sql.*;

import shared.model.User;

/** Database Class that access to the Users */
public class Users {
	
	private Database db;

	/** Constructor
	 * 
	 * @param db	Database
	 * */
	public Users(Database db){
		this.db = db;
	}
	
	/** get a user yb userID a user
	 * 
	 * */
	public User getUser(String username){	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;
		try{
		    String sql = "select UserId, UserName, PassWord, Email, FirstName, " +
		    			"LastName, IndexedRecords from Users where UserName = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setString(1, username);

		    rs = stmt.executeQuery();
		    while(rs.next()){
		    	User newUser = new User(rs.getInt(1),rs.getString(2),rs.getString(3),
		    							rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7));
		    	user = newUser;
		    }
		}
		catch(SQLException e){
			System.out.println("Can't execute query");
			e.printStackTrace();
		}
		finally{
			try{
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
			} 
			catch (SQLException e){
				System.out.println("Can't execute connect");
				e.printStackTrace();
			}
		}
		return user;
	}
	
	/** add a user */
	public void add(User user){

		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try{
		    String sql = "insert into Users " +
		    			"(LastName, FirstName, IndexedRecords, " +
		    			"Password, UserName, Email) values (?, ?, ?, ?, ?, ?)";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setString(1, user.getLastName());
		    stmt.setString(2, user.getFirstName());
		    stmt.setInt(3, user.getIndexedRecords());
		    stmt.setString(4, user.getPassword());
		    stmt.setString(5, user.getUsername());
		    stmt.setString(6, user.getEmail());

		    if(stmt.executeUpdate() == 1){
		        keyStmt = db.getConnection().createStatement();
		        keyRS = keyStmt.executeQuery("select last_insert_rowid()");
		        keyRS.next();
		        int id = keyRS.getInt(1);
		        user.setUserID(id);
		    }
		    else{
				System.out.println("Add fail");
		    }
		}
		catch(SQLException e){
			System.out.println("Can't execute add");
			e.printStackTrace();
		}
		finally{
			try{
				if (keyRS != null) keyRS.close();
				if (stmt != null) stmt.close();
				if (keyStmt != null) keyStmt.close();
			}
			catch(SQLException e){
				System.out.println("Can't execute connect");
				e.printStackTrace();
			}
		}
	}
	
	/** update a user */
	public void update(User user){
		PreparedStatement stmt = null;
		try{
		    String sql = "update Users " + 
		                 "set UserName = ?, Email = ?, FirstName = ?, " +
		                 "LastName = ?, IndexedRecords = ?, PassWord = ? " +
		                 "where UserID = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setString(1, user.getUsername());
		    stmt.setString(2, user.getEmail());
		    stmt.setString(3, user.getFirstName());
		    stmt.setString(4, user.getLastName());
		    stmt.setInt(5, user.getIndexedRecords());
		    stmt.setString(6, user.getPassword());
		    stmt.setInt(7, user.getUserID());

		    if(stmt.executeUpdate() == 1){
		    	System.out.println("Update success");
		    }
		    else{
				System.out.println("Update fail");
		    }
		}
		catch(SQLException e){
			System.out.println("Can't execute update");
			e.printStackTrace();
		}
		finally{
			try{
				if (stmt != null) stmt.close();
			} 
			catch(SQLException e){
				System.out.println("Can't execute connect");
				e.printStackTrace();
			}
		}
	}

}
