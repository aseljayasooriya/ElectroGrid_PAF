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
		
		
		//read method
		
		public String readPayment()
		{
			String output="";
			
			try {
				
				Connection con = connect();
				
				if(con==null) {
					return "Error while connecting to the database for reading the Payment";
				}
				//creating a table using html to display the payments
				output ="<table border ='1'><tr><th>Account Number</th><th>Payment Amount</th><th>Payment Method</th><th>Card Number</th><th>email</th><th>Update</th><th>Remove</th></tr>";
				
				String query = "select * from payment";
							
						
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				//go through all the rows in the result set using a while loop
				
				while(rs.next()) 
					{
					
					String paymentID = Integer.toString(rs.getInt("paymentID"));
					String accountNo = rs.getString("accountNo");
					String paymentAmount = Double.toString(rs.getDouble("paymentAmount"));
					String paymentMethod = rs.getString("paymentMethod");
					String cardNo = rs.getString("cardNo");
					String email = rs.getString("email");
					
					
					//add to the above created table
					
				
					output += "<td>"+accountNo+"</td>";
					output += "<td>"+paymentAmount+"</td>";
					output += "<td>"+paymentMethod+"</td>";
					output += "<td>"+cardNo+"</td>";
					output += "<td>"+email+"</td>";
					
					//buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
								+"<td><form method='post' action ='payment.jsp'>"
								+"<input name ='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
								+"<input name='paymentID' type='hidden' value='"+paymentID+"'>"+"</form></td></tr>";
					
					
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
		
		//Update method
		public String updatePayment(String paymentID, String accNo, String pAmount,
					    String pMethod, String cardNo, String pEmail)
		{
			String output = "";
			
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for updating.";
			 } 
			 
			 // create a prepared statement
			 String query = "UPDATE payment SET accountNo=?,paymentAmount=?,paymentMethod=?,cardNo=?, email = ? WHERE paymentID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setString(1, accNo);
			 preparedStmt.setDouble(2, Double.parseDouble(pAmount));
		         preparedStmt.setString(3, pMethod);
			 preparedStmt.setString(4, cardNo);
			 preparedStmt.setString(5, pEmail);
			 preparedStmt.setInt(6, Integer.parseInt(paymentID));
			 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Updated successfully"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "Error while updating the payment."; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output;
		}
				
		}

