package server.database;

import java.sql.*;
import java.util.ArrayList;


import shared.model.Project;

/** Database Class that access to the Projects */
public class Projects {

	private Database db;
	
	/** Constructor
	 * 
	 * @param db	Database
	 * */
	public Projects(Database db){
		this.db = db;
	}
	
	/** get all the projects
	 * 
	 * @return	a list of projects
	 * */
	public ArrayList<Project> getAll(){	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Project> projects = new ArrayList<Project>();
		try{
		    String sql = "select ProjectID, Title, RecordSperImage, " +
		    			"FirstYCoord, RecordHeight from Projects";
		    stmt = db.getConnection().prepareStatement(sql);

		    rs = stmt.executeQuery();
		    while(rs.next()){
		    	Project newProject = new Project(rs.getInt(1),rs.getString(2),rs.getInt(3),
		    									rs.getInt(4),rs.getInt(5));
		    	projects.add(newProject);
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
		return projects;
	}
	
	/** get a project by projectID
	 * 
	 * @return	a project
	 * */
	public Project getProject(int ID){	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Project project = null;
		try{
		    String sql = "select ProjectID, Title, RecordSperImage, " +
		    			"FirstYCoord, RecordHeight from Projects where ProjectID = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setInt(1, ID);

		    rs = stmt.executeQuery();
		    while(rs.next()){
		    	Project newProject = new Project(rs.getInt(1),rs.getString(2),rs.getInt(3),
		    									rs.getInt(4),rs.getInt(5));
		    	project = newProject;
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
		return project;
	}
	
	
	/** add a project */
	public void add(Project project){
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try{
		    String sql = "insert into Projects " +
		    			"(Title, RecordSperImage, " +"FirstYCoord, RecordHeight) " +
		    			"values (?, ?, ?, ?)";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setString(1, project.getTitle());
		    stmt.setInt(2, project.getRecordsPerImage());
		    stmt.setInt(3, project.getFirstYCoord());
		    stmt.setInt(4, project.getRecordHeight());

		    if(stmt.executeUpdate() == 1){
		        keyStmt = db.getConnection().createStatement();
		        keyRS = keyStmt.executeQuery("select last_insert_rowid()");
		        keyRS.next();
		        int id = keyRS.getInt(1);  
		        project.setProjectID(id);
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
	
	/** update a project */
	public void update(Project project){

		PreparedStatement stmt = null;
		try {
		    String sql = "update Projects " + 
		                 "set Title = ?, RecordSperImage = ?, FirstYCoord = ?, RecordHeight = ? " +
		                 "where ProjectID = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setString(1, project.getTitle());
		    stmt.setInt(2, project.getRecordsPerImage());
		    stmt.setInt(3, project.getFirstYCoord());
		    stmt.setInt(4, project.getRecordHeight());
		    stmt.setInt(5, project.getProjectID());

		    if (stmt.executeUpdate() == 1){
		    	System.out.println("Update success");
		    }
		    else{
				System.out.println("Update fail");
		    }
		}
		catch (SQLException e) {
			System.out.println("Can't execute update");
			e.printStackTrace();
		}
		finally {
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				System.out.println("Can't execute connect");
				e.printStackTrace();
			}
		}
	}
}
