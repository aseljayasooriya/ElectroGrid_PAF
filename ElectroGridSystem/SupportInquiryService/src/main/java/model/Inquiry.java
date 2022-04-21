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
	
	
	//read method
	
	public String readInquiry()
	{
		String output="";
		
		try {
			
			Connection con = connect();
			
			if(con==null) {
				return "Error while connecting to the database for reading the inquiry";
			}
			//creating a table using html to display the inquires
			
			output ="<table border ='1'><tr><th>Inquiry Title</th><th>Inquiry Description</th><th>Contact Number</th><th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from inquiry";
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			//go through all the rows in the result set using a while loop
			
			while(rs.next()) 
			{
				
				String inquiryID = Integer.toString(rs.getInt("inquiryID"));
				String inquiryTitle=rs.getString("inquiryTitle");
				String inquiryDesc =rs.getString("inquiryDesc");
				String contactNum=Integer.toString(rs.getInt("contactNum"));
				
				//add to the above created table
				
				output += "<tr><td>"+inquiryID+"</td>";
				output += "<td>"+inquiryTitle+"</td>";
				output += "<td>"+inquiryDesc+"</td>";
				output += "<td>"+contactNum+"</td>";
				
				//buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
							+"<td><form method='post' action ='inquiry.jsp'>"
							+"<input name ='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
							+"<input name='inquiryID' type='hidden' value='"+inquiryID+"'>"+"</form></td></tr>";
				
				
			}
			con.close();
			
			//completing the created table
			output += "<table>";
			}
			catch (Exception e){
				output +="Error while reading the inquiries";
				System.err.println(e.getMessage());
				
			}
	
		return output;
	}
	
	
	
	//Update inquiry method
	
	public String updateInquiry(int ID,String title, String description,int contact) {
		String output ="";
		
		
		try 
		{
			Connection con = connect();
			
			if(con==null) {
				return "Error While connecting to the database for updating";
			}
			
			//create prepared statement
			
			String query ="UPDATE inquiry inquiryID=?,inquiryTitle=?,inquiryDesc=?,contactNum=? WHERE inquiryID=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//bind values
			preparedStmt.setInt(1, ID);
			preparedStmt.setString(2, title);
			preparedStmt.setString(3, description);
			preparedStmt.setInt(4, contact);
			
			//execute statement
			preparedStmt.execute();
			con.close();
			
			output="Update Sucessfully";
			
		}
		catch (Exception e) {
			output="Error while updating inquiry";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
}
	
	
	

