package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import shared.model.Value;

/** Database Class that access to the Values */
public class Values {

	private Database db;
	
	/** Constructor
	 * 
	 * @param db	Database
	 * */
	public Values(Database db) {
		this.db = db;
	}
	
	/** get search the Value
	 * 
	 * @return	a list of Values
	 * */
	public ArrayList<Value> getValues(int fieldID, String info){	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Value> values = new ArrayList<Value>();
		try{
		    String sql = "select  ValueID, ImageID, FieldID, Row, Info " +
		    				"from Valus where FieldID = ? and Info = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setInt(1, fieldID);
		    stmt.setString(2, info);

		    rs = stmt.executeQuery();
		    while(rs.next()){
		    	Value newValue = new Value(rs.getString(5),rs.getInt(1),
		    								rs.getInt(2),rs.getInt(3),rs.getInt(4));
		    	values.add(newValue);
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
		return values;
	}
	
	/** add a Value */
	public void add(Value value){
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try{
		    String sql = "insert into Valus " +
		    			"(Info, ImageID, FieldID, Row) " +
		    			"values (?, ?, ?, ?)";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setString(1, value.getInfo());
		    stmt.setInt(2, value.getImageID());
		    stmt.setInt(3, value.getFieldID());
		    stmt.setInt(4, value.getRow());

		    if(stmt.executeUpdate() == 1){
		        keyStmt = db.getConnection().createStatement();
		        keyRS = keyStmt.executeQuery("select last_insert_rowid()");
		        keyRS.next();
		        int id = keyRS.getInt(1); 
		        value.setValueID(id);
		    }
		    else{
				System.out.println("Add fail");
		    }
		}
		catch (SQLException e) {
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
				System.out.println("Can't execute connecr");
				e.printStackTrace();
			}
		}
	}
	
	/** update a Value */
	public void update(Value value){
		PreparedStatement stmt = null;
		try {
		    String sql = "update Valus " + 
		                 "set Info = ?, ImageID = ?, FieldID = ?, Row = ? " +
		                 "where ValueID = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setString(1, value.getInfo());
		    stmt.setInt(2, value.getImageID());
		    stmt.setInt(3, value.getFieldID());
		    stmt.setInt(4, value.getRow());
		    stmt.setInt(5, value.getValueID());

		    if (stmt.executeUpdate() == 1){
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
				System.out.println("Can't execute connecr");
				e.printStackTrace();
			}
		}
	}
}

