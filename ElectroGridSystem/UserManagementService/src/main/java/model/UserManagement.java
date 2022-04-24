package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.*;

public class UserManagement {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogriddb", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	// insert method
	// ------------------------------------------------------------------------------------
	public String insertUser(String nic, String name, String address, String type, String sector) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into usermanagement(`userID`,`userNIC`,`userName`,`userAddress`,`userType`,`userSector`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, nic);
			preparedStmt.setString(3, name);
			preparedStmt.setString(4, address);
			preparedStmt.setString(5, type);
			preparedStmt.setString(6, sector);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// read
	// function-----------------------------------------------------------------------
	public String readUsers() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>User NIC</th><th>User Name</th>" + "<th>User Address</th>"
					+ "<th>User Type</th>" + "<th>User Sector</th>" + "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from usermanagement";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String userID = Integer.toString(rs.getInt("userID"));
				String userNIC = rs.getString("userNIC");
				String userName = rs.getString("userName");
				String userAddress = rs.getString("userAddress");
				String userType = rs.getString("userType");
				String userSector = rs.getString("userSector");
				// Add into the html table
				output += "<tr><td>" + userNIC + "</td>";
				output += "<td>" + userName + "</td>";
				output += "<td>" + userAddress + "</td>";
				output += "<td>" + userType + "</td>";
				output += "<td>" + userSector + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
						+ "<input name='itemID' type='hidden' value='" + userID + "'>" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// update
	// method---------------------------------------------------------------------------------
	public String updateUser(String ID, String nic, String name, String address, String type, String sector) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE usermanagement SET userNIC=?,userName=?,userAddress=?,userType=?, userSector=?WHERE userID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, nic);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, address);
			preparedStmt.setString(4, type);
			preparedStmt.setString(5, sector);
			preparedStmt.setInt(6, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// delete
	// method----------------------------------------------------------------------------
	public String deleteUser(String userID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from usermanagement where userID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(userID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// get users count by sector
	// method----------------------------------------------------------------------------
	public int getUsersCountBySector(String sector) {
		int output = 0;
		try {
			Connection con = connect();
			if (con == null) {
				throw new Error("Error while connecting to the database for reading user count.");
			}
			// create a prepared statement
			String query = "select count(*) as userCount from usermanagement where userSector=? and userType='O'";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, sector);
			// execute the statement
			ResultSet rs = preparedStmt.executeQuery();
			// write the results
			rs.next();
			output = rs.getInt("userCount") ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
	}
}
