package model;

import java.sql.*;

public class Inquiry {
	
	//A common method to connect to the DataBase
	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");

	 //Details of the database
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", "");
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
	 } 
	
	//Create Insert method
	
	public String insertInquiry(String title,String description, int contact) {
		String output ="";
		
		try 
		{
			Connection con = connect();
			
			if(con ==null)
			{
				return "Error while connecting to the database for inserting.";
			}
			
			//creating prepared statement
			String query = "insert into inquiry ('inquiryID','inquiryTitle','inquiryDesc','contactNum')"+"values(?,?,?,?)";
			
			PreparedStatement preparedStmt =con.prepareStatement(query);
			
			//binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, title);
			preparedStmt.setString(3, description);
			preparedStmt.setInt(4,contact);
			
			//execute the statements
			preparedStmt.execute();
			con.close();
			
			output = "inserted successfully";	
			}
		catch (Exception e) {
			output="Error while inserting the inquiry";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	
	
	
}
