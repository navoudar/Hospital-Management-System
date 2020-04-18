package com.model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;



public class Appointment {


	 int m_appouintment_no;
	 String m_appointment_patient;
	 String m_appointment_category;
	 String m_appointment_hospital;
	 Date m_appointment_date;
	Time m_appointment_time;
	String m_appointment_description;
	
	

	// A common method to connect to the DB
		private Connection connect() {

			Connection con = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");

				// Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management_system?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT", "root", "");
			} catch (Exception e) {
				e.printStackTrace();
			}

			return con;

		}
		
	
	public String makeAppointment(String m_appointment_patient, String m_appointment_category, String m_appointment_hospital, String m_appointment_date, String m_appointment_time, String m_appointment_description) {
		
		String output = "";
		
		try {
			
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for logging.";
			}

			String query = "insert into m_appointment (`m_appointment_patient`, `m_appointment_category`, `m_appointment_hospital`, `m_appointment_date`, `m_appointment_time`, `m_appointment_description`) values (?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			//preparedStmt.setInt(1, 0);
			preparedStmt.setString(1, m_appointment_patient);
			preparedStmt.setString(2, m_appointment_category);
			preparedStmt.setString(3, m_appointment_hospital);
			preparedStmt.setDate(4, Date.valueOf(m_appointment_date));
			preparedStmt.setTime(5, Time.valueOf(m_appointment_time));
			preparedStmt.setString(6, m_appointment_description);

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			

			output =  "Appointment Details Inserted successfully";
		} catch (Exception e) {
			output =  "Error while inserting the Appointment Details .";
			System.err.println(e.getMessage());
		}

		return output;
	
	}
	
	public String viewAllAppointment() {

		String output = "";

		try
			{

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Patient Name</th><th>Specialization Category</th><th>Hospital Name</th><th>Date</th><th>Time</th><th>Appointment Description</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from m_appointment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				String m_appointment_no = Integer.toString(rs.getInt("m_appointment_no"));
				String m_appointment_patient = rs.getString("m_appointment_patient");
				String m_appointment_category = rs.getString("m_appointment_category");
				String m_appointment_hospital = rs.getString("m_appointment_hospital");
				Date m_appointment_date = rs.getDate("m_appointment_date");
				Time m_appointment_time = rs.getTime("m_appointment_time");
				String m_appointment_description = rs.getString("m_appointment_description");

				// Add into the html table
				output += "<tr><td>" + m_appointment_patient + "</td>";
				output += "<td>" + m_appointment_category + "</td>";
				output += "<td>" + m_appointment_hospital + "</td>";
				output += "<td>" + m_appointment_date + "</td>";
				output += "<td>" + m_appointment_time + "</td>";
				output += "<td>" + m_appointment_description + "</td>";

				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"Appointment.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						+ "<input name=\"aNo\" type=\"hidden\" value=\"" + m_appointment_no + "\">" + "</form></td></tr>";

			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Appointment.";
			System.err.println(e.getMessage());
		}

			
			return output;
	}
	
		
	/*public String viewAnAppointment(String m_appointment_patient) {
		
		String output = "";

		try
			{

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Patient Name</th><th>Specialization Category</th><th>Hospital Name</th><th>Date</th><th>Time</th><th>Appointment Description</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from m_appointment where m_appointment_patient=?";
			PreparedStatement preparedstmt = con.prepareStatement(query);
			ResultSet rs = preparedstmt.executeQuery(query);
			
			while (rs.next()) {
				String m_appointment_no = Integer.toString(rs.getInt("m_appointment_no"));
				String m_appointment_patient1 = rs.getString("m_appointment_patient");
				String m_appointment_category = rs.getString("m_appointment_category");
				String m_appointment_hospital = rs.getString("m_appointment_hospital");
				Date m_appointment_date = rs.getDate("m_appointment_date");
				Time m_appointment_time = rs.getTime("m_appointment_time");
				String m_appointment_description = rs.getString("m_appointment_description");

				// Add into the html table
				output += "<tr><td>" + m_appointment_patient + "</td>";
				output += "<td>" + m_appointment_category + "</td>";
				output += "<td>" + m_appointment_hospital + "</td>";
				output += "<td>" + m_appointment_date + "</td>";
				output += "<td>" + m_appointment_time + "</td>";
				output += "<td>" + m_appointment_description + "</td>";

				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"Appointment.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						+ "<input name=\"aNo\" type=\"hidden\" value=\"" + m_appointment_no + "\">" + "</form></td></tr>";

			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Appointment.";
			System.err.println(e.getMessage());
		}

			
			return output;
		
	}*/
	
		
		
	public String updateAppointment(String m_appointment_n0, String m_appointment_patient, String m_appointment_category, String m_appointment_hospital, String m_appointment_date, String m_appointment_time, String m_appointment_description) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for updating.";

			}

			// create a prepared statement
			String query = "UPDATE m_appointment SET m_appointment_patient=?,m_appointment_category=?,m_appointment_hospital=?,m_appointment_date=?,m_appointment_time=?,m_appointment_description=?      WHERE m_appointment_no=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, m_appointment_patient);
			preparedStmt.setString(2, m_appointment_category);
			preparedStmt.setString(3, m_appointment_hospital);
			preparedStmt.setDate(4, Date.valueOf(m_appointment_date));
			preparedStmt.setTime(5, Time.valueOf(m_appointment_time));
			preparedStmt.setString(6, m_appointment_description);
			preparedStmt.setInt(7, Integer.parseInt(m_appointment_n0));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";

		} catch (Exception e) {
			output = "Error while updating the appointment.";
			System.err.println(e.getMessage());
		}

		return output;

	}
	
	
	public String deleteAppointment(String m_appointment_no) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for deleting.";

			}

			// create a prepared statement

			String query = "delete from m_appointment where m_appointment_no=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(m_appointment_no));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";

		} catch (Exception e) {
			output = "Error while deleting the appointment.";
			System.err.println(e.getMessage());

		}

		return output;

	}
		
		
		
	
	
}
