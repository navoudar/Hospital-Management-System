package com.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Payment {

	private Connection connect() {

		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/hospital_management_system?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;

	}

	public String getAllPayments() {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Payment No</th><th>Appointment No</th><th>Payment Date</th><th>Payment Time</th><th>Customer Name</th><th>Card No</th><th>Amount</th></tr>";

			String query = "select * from t_payments where t_payment_Status = 'Active'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				/*
				 * String itemID = Integer.toString(rs.getInt("itemID")); String itemCode =
				 * rs.getString("itemCode"); String itemName = rs.getString("itemName"); String
				 * itemPrice = Double.toString(rs.getDouble("itemPrice")); String itemDesc =
				 * rs.getString("itemDesc");
				 */
				// Add into the html table
				output += "<tr><td>" + rs.getString("t_payment_no") + "</td>";
				output += "<td>" + rs.getString("t_payment_appointment") + "</td>";
				output += "<td>" + rs.getDate("t_payment_date") + "</td>";
				output += "<td>" + rs.getTime("t_payment_time") + "</td>";
				output += "<td>" + rs.getString("t_payment_customerName") + "</td>";
				output += "<td>" + rs.getInt("t_payment_cardNo") + "</td>";
				output += "<td>" + rs.getDouble("t_payment_amount") + "</td></tr>";

				/*
				 * // buttons output +=
				 * "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
				 * + "<td><form method=\"post\" action=\"items.jsp\">" +
				 * "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
				 * + "<input name=\"itemID\" type=\"hidden\" value=\"" + itemID + "\">" +
				 * "</form></td></tr>";
				 */
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the payemnt.";
			System.err.println(e.getMessage());
		}

		return output;

	}

	public String validateCard(int cardNo) {
		String output = "";
		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = "Select * from m_card_details where m_card_cardNo =? ";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, cardNo);

			// execute the statement
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {

				output = "valid";
			}

			if (output.contains("valid")) {
				output = "valid";
			} else {
				output = "invalid";
			}
			con.close();

		} catch (Exception e) {
			output = "invalid";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String insertPayment(String t_payment_no, String t_payment_appointment, String t_payment_date,
			String t_payment_time, String t_payment_customerName, String t_payment_cardNo, String t_payment_cardtype,
			String t_payment_amount) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " insert into t_payments (`t_payment_no`,`t_payment_appointment`,`t_payment_date`,`t_payment_time`,`t_payment_customerName`,`t_payment_cardNo`,`t_payment_cardtype`,`t_payment_amount`) values (?, ?, ?, ?, ?,?,?,?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, t_payment_no);
			preparedStmt.setString(2, t_payment_appointment);
			preparedStmt.setDate(3, Date.valueOf(t_payment_date));
			preparedStmt.setTime(4, Time.valueOf(t_payment_time));
			preparedStmt.setString(5, t_payment_customerName);
			preparedStmt.setInt(6, Integer.parseInt(t_payment_cardNo));
			preparedStmt.setString(7, t_payment_cardtype);
			preparedStmt.setDouble(8, Double.parseDouble(t_payment_amount));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "success";
		} catch (Exception e) {
			output = "Error";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updatePayment(String t_payment_no, String t_payment_appointment, String t_payment_date,
			String t_payment_time, String t_payment_customerName, String t_payment_cardNo, String t_payment_cardtype,
			String t_payment_amount) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " UPDATE t_payments SET t_payment_appointment=?,t_payment_date=?,t_payment_time=?,t_payment_customerName=?,t_payment_cardNo=?,t_payment_cardtype=?,t_payment_amount=?  WHERE t_payment_no =?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, t_payment_appointment);
			preparedStmt.setDate(2, Date.valueOf(t_payment_date));
			preparedStmt.setTime(3, Time.valueOf(t_payment_time));
			preparedStmt.setString(4, t_payment_customerName);
			preparedStmt.setInt(5, Integer.parseInt(t_payment_cardNo));
			preparedStmt.setString(6, t_payment_cardtype);
			preparedStmt.setDouble(7, Double.parseDouble(t_payment_amount));
			preparedStmt.setString(8, t_payment_no);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "success";
		} catch (Exception e) {
			output = "Error";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deletePayment(String t_payment_no) {
		// In this delete function we are only changing the status into delete, not
		// going to remove the record from databse
		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for deleting.";

			}

			String query = "UPDATE t_payments SET t_payment_Status='cancelled' where t_payment_no=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			preparedStmt.setString(1, t_payment_no);

			preparedStmt.execute();
			con.close();

			output = "success";

		} catch (Exception e) {
			output = "Error";
			System.err.println(e.getMessage());

		}

		return output;

	}
}
