package main.java.com.service;

import java.sql.*;

import main.java.com.model.MeterReading;
import main.java.com.util.DBConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MeterReadingService {
	private static Connection connection;
	private PreparedStatement preparedStatement;

	private void setMeterReadingAttributes(MeterReading meterReading, ResultSet resultSet) throws SQLException {
		meterReading.setId(resultSet.getInt("id"));
		meterReading.setMeterReaderId(resultSet.getInt("meter_reader_id"));
		meterReading.setAccountNo(resultSet.getString("account_no"));
		meterReading.setYear(resultSet.getInt("year"));
		meterReading.setMonth(resultSet.getInt("month"));
		meterReading.setReading(resultSet.getInt("reading"));
	}

	private void closeConnectionAndPreparedStatementIfNotNull() {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public JSONObject insertMeterReading(MeterReading meterReading) {
		JSONObject output = new JSONObject();

		try {
			if (meterReading.getMonth() < 1 || meterReading.getMonth() > 12) {
				output.put("message", "Month must be between 1 and 12");
				output.put("status", "error");

				return output;
			}
			if (meterReading.getYear() < 0) {
				output.put("message", "Year must be greater than 0");
				output.put("status", "error");
				return output;
			}
			connection = DBConnection.getDBConnection();

			// creating the prepared statement
			String query = " INSERT INTO meter_reading (`meter_reader_id`,`account_no`,`year`, `month`, `reading`,`archived`) VALUES (?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(query);

			// binding the values
			preparedStatement.setInt(1, meterReading.getMeterReaderId());
			preparedStatement.setString(2, meterReading.getAccountNo());
			preparedStatement.setInt(3, meterReading.getYear());
			preparedStatement.setInt(4, meterReading.getMonth());
			preparedStatement.setInt(5, meterReading.getReading());
			preparedStatement.setInt(6, 0);

			// execute the statement
			preparedStatement.execute();

			output.put("message", "Meter Reading added successfully");
			output.put("status", "success");
		} catch (SQLException | ClassNotFoundException e) {
			if (e.getMessage().contains("Duplicate")) {
				output.put("message", "Meter Reading already exists");
			}
			else {
				output.put("message", "Error adding Meter Reading");
			}
			output.put("status", "error");
			System.out.println(e.getMessage());

		} finally {
			closeConnectionAndPreparedStatementIfNotNull();
		}
		return output;
	}

	// read all method

	public JSONArray readAllMeterReadings() {
		JSONArray output = new JSONArray();
		try {
			connection = DBConnection.getDBConnection();

			String query = "select * from meter_reading where archived = 0";

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// go through all the rows in the result set using a while loop

			while (rs.next()) {
					MeterReading meterReading = new MeterReading();
					setMeterReadingAttributes(meterReading, rs);
					output.add(meterReading);
				}

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;
	}

	// get all meter readings for a given account number
	public JSONArray readMeterReadingsByAccountNo(String accountNo, int year, int month) {
		JSONArray output = new JSONArray();
		try {
			connection = DBConnection.getDBConnection();

			String query = "select * from meter_reading where account_no = ? "
					+ (year != 0 ? "and year = ? " : "" ) + (month != 0 ? "and month = ? " : "") + "and archived = 0";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, accountNo);
			if (year != 0) {
				preparedStatement.setInt(2, year);
			}
			if (month != 0) {
				preparedStatement.setInt((year != 0 ? 3 : 2), month);
			}
			ResultSet rs = preparedStatement.executeQuery();

			// go through all the rows in the result set using a while loop

			while (rs.next()) {
					MeterReading meterReading = new MeterReading();
					setMeterReadingAttributes(meterReading, rs);
					output.add(meterReading);
				}

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;
	}

	// delete meter reading by account number, year and month
	public JSONObject deleteMeterReading(String accountNo, int year, int month) {
		JSONObject output = new JSONObject();
		try {
			connection = DBConnection.getDBConnection();

			String query = "delete from meter_reading where account_no = ? and year = ? and month = ?";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, accountNo);
			preparedStatement.setInt(2, year);
			preparedStatement.setInt(3, month);

			// execute the statement
			preparedStatement.execute();

			output.put("message", "Meter Reading deleted successfully");
			output.put("status", "success");
		} catch (SQLException | ClassNotFoundException e) {
			output.put("message", "Error deleting Meter Reading");
			output.put("status", "error");
			System.out.println(e.getMessage());

		} finally {
			closeConnectionAndPreparedStatementIfNotNull();
		}
		return output;
	}

	// update meter reading by account number, year and month
	public JSONObject updateMeterReading(String accountNo, int year, int month, MeterReading meterReading) {
		JSONObject output = new JSONObject();

		try {
			connection = DBConnection.getDBConnection();

			// creating the prepared statement
			String query = " UPDATE meter_reading SET reading = ? WHERE account_no = ? AND year = ? AND month = ? and archived = 0";

			preparedStatement = connection.prepareStatement(query);

			// binding the values
			preparedStatement.setDouble(1, meterReading.getReading());
			preparedStatement.setString(2, accountNo);
			preparedStatement.setInt(3, year);
			preparedStatement.setInt(4, month);

			// execute the statement
			int rowCount = preparedStatement.executeUpdate();

			if (rowCount > 0) {
				output.put("message", "Meter Reading Updated successfully");
				output.put("status", "success");
			} else{
				output.put("message", "Meter Reading not found");
				output.put("status", "error");
			}
		} catch (SQLException | ClassNotFoundException e) {
			output.put("message", "Error occurred deleting Meter Reading");
			output.put("status", "error");
			System.err.println(e.getMessage());

		} finally {
			closeConnectionAndPreparedStatementIfNotNull();
		}
		return output;
	}


}
