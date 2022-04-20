package model;

import java.sql.*;

public class Payment {
	
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
		
		public String insertPayment(String accNo, String pAmount, String pMethod, String cardNo, String pEmail) {
			
			String output = "";
			
			try {
				Connection con = connect();
				
				if(con == null) {
					return "Error while connecting to the database for payments.";
				}
				
				//creating the prepared statement
				String query = " insert into payment (`paymentID`,`accountNo`,`paymentAmount`,`paymentMethod`,`cardNo`,`email`)"
								+ " values (?, ?, ?, ?, ?, ?)";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				//binding the values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, accNo);
				preparedStmt.setDouble(3, Double.parseDouble(pAmount));
				preparedStmt.setString(4, pMethod);
				preparedStmt.setString(5, cardNo);
				preparedStmt.setString(6, pEmail);
				
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
