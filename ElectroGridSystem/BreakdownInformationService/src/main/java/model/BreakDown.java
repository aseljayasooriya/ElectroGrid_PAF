package model;

import java.sql.*;

public class BreakDown {
	
	//A common method to connect to the DB
	private Connection connect()
	{
		Connection con = null;
			 
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			//Provide the database details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogriddb", "root", "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
			
		return con;
	}	
	
	public String insertItem(String bsector, String bdate, String sTime, String eTime, String btype) {
		
		String output = "";
		
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for inserting.";
			}
			
			//creating the prepared statement
			String query = " insert into breakdowninformation (`breakdownID`,`breakdownSector`,`breakdownDate`,`startTime`,`endTime`,`breakdownType`)"
							+ " values (?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding the values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, bsector);
			preparedStmt.setDate(3, Date.valueOf(bdate));
			preparedStmt.setTime(4, Time.valueOf(sTime));
			preparedStmt.setTime(5, Time.valueOf(eTime));
			preparedStmt.setString(6, btype);
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Inserted successfully";
			
		}
		catch (Exception e) {
			output = "Error while inserting the breakdown info.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}

}
