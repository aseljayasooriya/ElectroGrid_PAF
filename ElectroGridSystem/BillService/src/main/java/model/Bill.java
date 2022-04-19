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

}
