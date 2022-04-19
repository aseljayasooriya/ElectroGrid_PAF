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
	
	public String insertBreakdown(String bsector, String bdate, String sTime, String eTime, String btype) {
		
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
	
	public String readBreakdowns() {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for reading.";
			}
			
			//Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Breakdown Sector</th><th>Breakdown Date</th>" +
					 "<th>Breakdown Start Time</th>" +
					 "<th>Breakdown End Time</th>" +
					 "<th>Breakdown Type</th>" +
					 "<th>Update</th><th>Remove</th></tr>";
			
			String query = "SELECT * FROM breakdowninformation";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			//iterate through the rows in the result set
			while(rs.next()) {
				String breakdownID = Integer.toString(rs.getInt("breakdownID"));
				String breakdownSector = rs.getString("breakdownSector");
				String breakdownDate = rs.getString("breakdownDate");
				String startTime = rs.getString("startTime");
				String endTime = rs.getString("endTime");
				String breakdownType = rs.getString("breakdownType");
				
				//add into html table
				output += "<tr><td>" + breakdownSector + "</td>";
				output += "<td>" + breakdownDate + "</td>";
				output += "<td>" + startTime + "</td>";
				output += "<td>" + endTime + "</td>";
				output += "<td>" + breakdownType + "</td>";
				
				//buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						 + "<td><form method='post' action='breakdowns.jsp'>"
						 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						 + "<input name='breakdownID' type='hidden' value='" + breakdownID
						 + "'>" + "</form></td></tr>"; 
			}
			
			con.close();
			
			//complete the html table
			output += "</table>"; 
		}
		catch (Exception e) {
			output = "Error while reading the breakdown info.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String updateBreakdown(String ID, String sector, String date, String sTime, String eTime, String type) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for updating.";
			}
			
			//create the prepared statement
			String query = "UPDATE breakdowninformation SET breakdownSector=?,breakdownDate=?,startTime=?,endTime=?,breakdownType=? WHERE breakdownID=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values
			preparedStmt.setString(1, sector);
			preparedStmt.setDate(2, Date.valueOf(date));
			preparedStmt.setTime(3, Time.valueOf(sTime));
			preparedStmt.setTime(4, Time.valueOf(eTime));
			preparedStmt.setString(5, type);
			preparedStmt.setInt(6, Integer.parseInt(ID));
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Updated successfully.";
		}
		catch (Exception e) {
			output = "Error while updating the breakdown info.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String deleteBreakdown(String breakdownID) {
		
		String output = "";
		
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for deleting.";
			}
			
			//create a prepared statement
			String query = "delete from breakdowninformation where breakdownID=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values
			preparedStmt.setInt(1, Integer.parseInt(breakdownID));
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Deleted successfully";
			
		}
		catch(Exception e) {
			output = "Error while deleting the breakdown info.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}

}
