package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Payment {

	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the database details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogriddb", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	public String insertPayment(String accNo, String pAmount, String pMethod, String cardNo, String pEmail) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for payments.";
			}

			// creating the prepared statement
			String query = " insert into payment (`paymentID`,`accountNo`,`paymentAmount`,`paymentMethod`,`cardNo`,`email`)"
					+ " values (?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding the values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, accNo);
			preparedStmt.setDouble(3, Double.parseDouble(pAmount));
			preparedStmt.setString(4, pMethod);
			preparedStmt.setString(5, cardNo);
			preparedStmt.setString(6, pEmail);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Inserted successfully";

		} catch (Exception e) {
			output = "Error while inserting the payments";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// read method

	public JSONArray readPayment(String accNo) {
		JSONArray output = new JSONArray();

		try {
			Connection con = connect();

			if (con == null) {
				System.err.println("Error while connecting to the database for updating.");
				return null;
			}

			String query = "select * from payment ";
			if (accNo != null) {
				query += " where accountNo=?";
			}

			PreparedStatement preparedStatement = con.prepareStatement(query);
			if (accNo != null) {
				preparedStatement.setString(1, accNo);
			}
			ResultSet rs = preparedStatement.executeQuery();
			// go through all the rows in the result set using a while loop

			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("paymentID", rs.getInt("paymentID"));
				obj.put("accountNo", rs.getString("accountNo"));
				obj.put("paymentAmount", rs.getDouble("paymentAmount"));
				obj.put("paymentMethod", rs.getString("paymentMethod"));
				obj.put("cardNo", rs.getString("cardNo"));
				obj.put("email", rs.getString("email"));
				output.add(obj);
			}
			con.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());

		}

		return output;
	}

	// Update method
	public String updatePayment(String paymentID, String accNo, String pAmount, String pMethod, String cardNo,
								String pEmail) {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
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
		} catch (Exception e) {
			output = "Error while updating the payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// delete
	// method------------------------------------------------------------------
	public String deletePayment(String paymentID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from payment where paymentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(paymentID));
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

	// get due payments method
	public double getDueAmount(String accNo, String asOfDate) {
		final double UNIT_PRICE = 50.0;
		double output = 0;
		try {
			Connection con = connect();
			if (con == null) {
				return -1;
			}
			int year;
			int month;

			if (asOfDate != null) {
				year = LocalDate.parse(asOfDate).getYear();
				month = LocalDate.parse(asOfDate).getMonthValue();
			} else {
				year = LocalDate.now().getYear();
				month = LocalDate.now().getMonthValue();
			}

			JSONArray payments = readPayment(accNo);
			double totalPayments = 0;
			for (int i = 0; i < payments.size(); i++) {
				JSONObject payment = (JSONObject) payments.get(i);
				double paymentAmount = (double) payment.get("paymentAmount");
				totalPayments += paymentAmount;
			}

			JSONArray readings = new JSONArray();
			JSONObject reading;
			JSONParser parser = new JSONParser();
			String urlString = "http://localhost/PowerConsumptionService/readings/account/" + accNo + "?year=" + year + "&month=" + month;
			URL url = new URL(urlString);

			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Accept", "application/json");

			if (urlConnection.getResponseCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ urlConnection.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(urlConnection.getInputStream())));

			String response;
			while ((response = br.readLine()) != null) {
				readings = (JSONArray) parser.parse(response);
			}
			double numberOfUnits = 0;

			if (readings.size() > 0) {
				reading = (JSONObject) readings.get(0);
				numberOfUnits = Double.parseDouble(reading.get("reading").toString());;
			}
			con.close();
			output = numberOfUnits * UNIT_PRICE - totalPayments;
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage() + e.toString());
		}
		return output;
	}
}
