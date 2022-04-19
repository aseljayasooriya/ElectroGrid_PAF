package model;

import java.sql.*;

public class Bill {
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

	//Insert method 
	public String insertBill(String billCode, String accountNo, String billMonth, String units,String meterReader_name) 
	 { 
		 String output = "";
		 
		 //bill calculation
		 //Conversion of the String variable into double
		 double unit = Double.parseDouble(units);
		 double unitPrice = 50.00;
		 //calculation
		 double tot = unit*unitPrice;
		 String amount = Double.toString(tot);
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database when inserting the bill."; 
			 } 
			 // create a prepared statement
			 String query = " insert into bill (`billID`,`billCode`,`accountNo`,`billMonth`,`units`,`billAmount`,`meterReader_name`)"
			 + " values (?, ?, ?, ?, ?, ?, ?)"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, billCode); 
			 preparedStmt.setString(3, accountNo);
			 preparedStmt.setString(4, billMonth);
			 preparedStmt.setString(5, units);
			 preparedStmt.setString(6, amount);
			 preparedStmt.setString(7, meterReader_name); 
			 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Inserted successfully"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "Error while inserting the bill."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	 }
	
	//Read method
	public String readBills() 
	 { 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for reading."; 
			 } 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Bill Code</th>" +
			 "<th>Account No</th>" + 
			 "<th>Bill Month</th>" +
			 "<th>Units</th>" +
			 "<th>Bill Amount</th>" +
			 "<th>Meter Reader Name</th>" +
			 "<th>Update</th><th>Remove</th></tr>"; 
			 
			 String query = "select * from bill"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query);
			 
			 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
			 String billID = Integer.toString(rs.getInt("billID")); 
			 String billCode = rs.getString("billCode"); 
			 String accountNo = rs.getString("accountNo");
			 String billMonth = rs.getString("billMonth");
			 String units = rs.getString("units");
			 String billAmount = rs.getString("billAmount");
			 String meterReader_name = rs.getString("meterReader_name");
			 
			 // Add into the html table
			 output += "<tr><td>" + billCode + "</td>"; 
			 output += "<td>" + accountNo + "</td>"; 
			 output += "<td>" + billMonth + "</td>"; 
			 output += "<td>" + units + "</td>";
			 output += "<td>" + billAmount + "</td>";
			 output += "<td>" + meterReader_name + "</td>";
			 
			 // buttons
			 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
			 + "<td><form method='post' action='items.jsp'>"
			 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
			 + "<input name='itemID' type='hidden' value='" + billID 
			 + "'>" + "</form></td></tr>"; 
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "Error while reading the items."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	 } 

}
