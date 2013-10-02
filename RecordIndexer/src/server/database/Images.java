package server.database;


import java.sql.*;
import java.util.ArrayList;

import shared.model.*;

/** Database Class that access to the Images */
public class Images {
	
	private Database db;
	
	/** Constructor
	 * 
	 * @param db	Database
	 * */
	public Images(Database db){
		this.db = db;
	}
	
	/** get a project's images by projectID
	 * 
	 * @return	a list of images
	 * */
	public ArrayList<Image> getImagesByProjectID(int projectID){	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Image> images = new ArrayList<Image>();
		try{
		    String sql = "select ImageID, File, ProjectID, " +
		    			"UserID from Images where ProjectID = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setInt(1, projectID);

		    rs = stmt.executeQuery();
		    while(rs.next()){
		    	Image newImage = new Image(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
		    	images.add(newImage);
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
		return images;
	}
	
	/** get a user's images by userID
	 * 
	 * @return	a image
	 * */
	public Image getUserImage(int userID){	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Image image = null;
		try{
		    String sql = "select ImageID, File, ProjectID, " +
		    			"UserID from Images where UserID = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setInt(1, userID);

		    rs = stmt.executeQuery();
		    while(rs.next()){
		    	Image newImage = new Image(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
		    	image = newImage;
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
		return image;
	}
	
	public Image getImage(int imageID){	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Image image = null;
		try{
		    String sql = "select ImageID, File, ProjectID, " +
		    			"UserID from Images where ImageID = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setInt(1, imageID);

		    rs = stmt.executeQuery();
		    while(rs.next()){
		    	Image newImage = new Image(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
		    	image = newImage;
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
		return image;
	}
	
	/** add a image */
	public void add(Image image){
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try{
		    String sql = "insert into Images (File, ProjectID, UserID) values (?, ?, ?)";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setString(1, image.getFile());
		    stmt.setInt(2, image.getProjectID());
		    stmt.setInt(3, image.getUserID());

		    if(stmt.executeUpdate() == 1){
		        keyStmt = db.getConnection().createStatement();
		        keyRS = keyStmt.executeQuery("select last_insert_rowid()");
		        keyRS.next();
		        int id = keyRS.getInt(1); 
		        image.setImageID(id);
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
	
	/** update a image */
	public void update(Image image){
		PreparedStatement stmt = null;
		try{
		    String sql = "update Images " + 
		                 "set File = ?, ProjectID = ?, UserID = ? " +
		                 "where ImageID = ?";
		    stmt = db.getConnection().prepareStatement(sql);
		    stmt.setString(1, image.getFile());
		    stmt.setInt(2, image.getProjectID());
		    stmt.setInt(3, image.getUserID());
		    stmt.setInt(4, image.getImageID());

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

