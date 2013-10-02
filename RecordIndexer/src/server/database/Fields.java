package server.database;

import java.sql.*;
import java.util.ArrayList;

import shared.model.Field;

/** Database Class that access to the Fields */
public class Fields {
	
	private Database db;
	
	/** Constructor
	 * 
	 * @param db	Database
	 * */
	public Fields(Database db){
		this.db = db;
	}
	
	/** get a project's fields by ProjectID
	 * 
	 * @return	a list of fields
	 * */
	public ArrayList<Field> getFieldsByProjectID(int projectID){	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Field> fields = new ArrayList<Field>();
		try{
		    String sql = "select FieldID, Title, XCoord, Width, HelpHTML, " +
		    			"KnownData, ProjectID from Fields where ProjectID = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setInt(1, projectID);
		    
		    rs = stmt.executeQuery();
		    while (rs.next()) {
		    	Field newField = new Field(rs.getInt(1),rs.getString(2),rs.getInt(3),
		    								rs.getInt(4),rs.getString(5),rs.getString(6),rs.getInt(7));
		    	fields.add(newField);
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
			}catch(SQLException e){
				System.out.println("Can't execute connect");
				e.printStackTrace();
			}
		}
		return fields;
	}
	/** get a field by FieldID
	 * 
	 * @return	a field
	 * */
	public Field getField(int fieldID){	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Field field = null;
		try{
		    String sql = "select FieldID, Title,  XCoord, Width, HelpHTML, " +
		    			"KnownData, ProjectID from Fields where FieldID = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setInt(1, fieldID);
		    
		    rs = stmt.executeQuery();
		    while(rs.next()){
		    	Field newField = new Field(rs.getInt(1),rs.getString(2),rs.getInt(3),
		    								rs.getInt(4),rs.getString(5),rs.getString(6),rs.getInt(7));
		    	field = newField;
		    }
		}
		catch(SQLException e){
			System.out.println("Can't execute query");
			e.printStackTrace();
		}
		finally{
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
			} 
			catch(SQLException e){
				System.out.println("Can't execute connect");
				e.printStackTrace();
			}
		}
		return field;
	}
	
	/** get all fields
	 * 
	 * @return	a list of fields
	 * */
	public ArrayList<Field> getAll(){	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Field> fields = new ArrayList<Field>();
		try{
		    String sql = "select FieldID, Title, XCoord, Width, " +
		    			"HelpHTML, KnownData, ProjectID from Fields";
		    stmt = db.getConnection().prepareStatement(sql);
		    
		    rs = stmt.executeQuery();
		    while(rs.next()){
		    	Field newField = new Field(rs.getInt(1),rs.getString(2),rs.getInt(3),
		    								rs.getInt(4),rs.getString(5),rs.getString(6),rs.getInt(7));
		    	fields.add(newField);
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
			catch(SQLException e){
				System.out.println("Can't execute connect");
				e.printStackTrace();
			}
		}
		return fields;
	}
	
	/** add a field */
	public void add(Field field){
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try{
		    String sql = "insert into Fields (XCoord, Title, Width, HelpHTML, " +
		    			"KnownData, ProjectID) values (?, ?, ?, ?, ?, ?)";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setInt(1, field.getxCoord());
		    stmt.setInt(3, field.getWidth());
		    stmt.setString(4, field.getHelpHTML());
		    stmt.setString(5, field.getKnownData());
		    stmt.setInt(6, field.getProjectID());
		    stmt.setString(2, field.getTitle());

		    if(stmt.executeUpdate() == 1){
		        keyStmt = db.getConnection().createStatement();
		        keyRS = keyStmt.executeQuery("select last_insert_rowid()");
		        keyRS.next();
		        int id = keyRS.getInt(1);
		        field.setFieldID(id);
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
			try {
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
	
	/** update a field */
	public void update(Field field){
		PreparedStatement stmt = null;
		try{
		    String sql = "update Fields " + 
		                 "set XCoord = ?, Width = ?, HelpHTML = ?, " +
		                 "KnownData = ?, ProjectID = ?, Title = ? " +
		                 "where FieldID = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setInt(1, field.getxCoord());
		    stmt.setInt(2, field.getWidth());
		    stmt.setString(3, field.getHelpHTML());
		    stmt.setString(4, field.getKnownData());
		    stmt.setInt(5, field.getProjectID());
		    stmt.setInt(7, field.getFieldID());
		    stmt.setString(6, field.getTitle());

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
