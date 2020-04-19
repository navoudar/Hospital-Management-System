package com.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Doctor {

	String userName;
	String Password;

	// A common method to connect to the DB
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

	public String doctorLoging(String dName, String password) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for logging.";
			}

			String query = "SELECT m_doctor_userName,m_doctor_password FROM m_doctor WHERE m_doctor_userName = ?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			preparedStmt.setString(1, dName);

			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {

				userName = rs.getString("m_doctor_userName");
				Password = rs.getString("m_doctor_password");
			}

			con.close();

			if (userName.equalsIgnoreCase(dName) && Password.equalsIgnoreCase(password)) {
				output = "success";
			}
		} catch (Exception e) {

			output = "Failed";
			System.err.println(e.getMessage());
		}

		return output;

	}

	public String insertDoctor(String SLMCNO, String m_doctor_name, String m_doctor_userName, String m_doctor_password,
			String m_doctor_address, String m_doctor_speciality, String m_doctor_sex, Date m_doctor_dateOfBirth,
			String m_doctor_qualification, String m_doctor_email, int m_doctor_phone, Date m_doctor_dateOfJoin,
			Double m_doctor_fees, String m_doctor_workingDays) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " insert into m_doctor (`m_SLMCNO`,`m_doctor_name`,`m_doctor_userName`,`m_doctor_password`,`m_doctor_address`,`m_doctor_speciality`,`m_doctor_sex`,`m_doctor_dateOfBirth`,`m_doctor_qualification`,`m_doctor_email`,`m_doctor_phone`,`m_doctor_dateOfJoin`,`m_doctor_fees`,`m_doctor_workingDays`) values (?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, SLMCNO);
			preparedStmt.setString(2, m_doctor_name);
			preparedStmt.setString(3, m_doctor_userName);
			preparedStmt.setString(4, m_doctor_password);
			preparedStmt.setString(5, m_doctor_address);
			preparedStmt.setString(6, m_doctor_speciality);
			preparedStmt.setString(7, m_doctor_sex);
			preparedStmt.setDate(8, m_doctor_dateOfBirth);
			preparedStmt.setString(9, m_doctor_qualification);
			preparedStmt.setString(10, m_doctor_email);
			preparedStmt.setInt(11, m_doctor_phone);
			preparedStmt.setDate(12, m_doctor_dateOfJoin);
			preparedStmt.setDouble(13, m_doctor_fees);
			preparedStmt.setString(14, m_doctor_workingDays);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Doctor Details Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Doctor Details .";
			System.err.println(e.getMessage());
		}

		return output;

	}

	public String readDoctor() {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>m_SLMCNO</th><th>m_doctor_name</th><th>m_doctor_address</th><th>m_doctor_speciality</th><th>m_doctor_sex</th><th>m_doctor_dateOfBirth</th>><th>m_doctor_qualification</th><th>m_doctor_email</th><th>m_doctor_phone</th><th>m_doctor_dateOfJoin</th><th>m_doctor_fees</th><th>m_doctor_workingDays</th><th>Update</th></tr>";

			String query = "select * from m_doctor";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String m_SLMCNO = rs.getString("m_SLMCNO");
				String m_doctor_name = rs.getString("m_doctor_name");
				String m_doctor_address = rs.getString("m_doctor_address");
				String m_doctor_speciality = rs.getString("m_doctor_speciality");
				String m_doctor_sex = rs.getString("m_doctor_sex");
				Date m_doctor_dateOfBirth = rs.getDate("m_doctor_dateOfBirth");
				String m_doctor_qualification = rs.getString("m_doctor_qualification");
				String m_doctor_email = rs.getString("m_doctor_email");
				int m_doctor_phone = rs.getInt("m_doctor_phone");
				Date m_doctor_dateOfJoin = rs.getDate("m_doctor_dateOfJoin");
				String m_doctor_fees = rs.getString("m_doctor_fees");
				String m_doctor_workingDays = rs.getString("m_doctor_workingDays");

				// Add into the html table
				output += "<tr><td>" + m_SLMCNO + "</td>";
				output += "<td>" + m_doctor_name + "</td>";
				output += "<td>" + m_doctor_address + "</td>";
				output += "<td>" + m_doctor_speciality + "</td>";
				output += "<td>" + m_doctor_sex + "</td>";
				output += "<td>" + m_doctor_dateOfBirth + "</td>";
				output += "<td>" + m_doctor_qualification + "</td>";
				output += "<td>" + m_doctor_email + "</td>";
				output += "<td>" + m_doctor_phone + "</td>";
				output += "<td>" + m_doctor_dateOfJoin + "</td>";
				output += "<td>" + m_doctor_fees + "</td>";
				output += "<td>" + m_doctor_workingDays + "</td>";
				;

				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"Doctor.jsp\">";

			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Details.";
			System.err.println(e.getMessage());
		}

		return output;

	}

	public String updateDoctor(String SLMCNO, String m_doctor_name, String m_doctor_address, String m_doctor_speciality,
			String m_doctor_sex, Date m_doctor_dateOfBirth, String m_doctor_qualification, String m_doctor_email,
			int m_doctor_phone, Date m_doctor_dateOfJoin, Double m_doctor_fees, String m_doctor_workingDays) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for updating.";

			}

			// create a prepared statement
			String query = "UPDATE m_doctor SET m_doctor_name=?,m_doctor_address=?,m_doctor_speciality=?,m_doctor_sex=?,m_doctor_dateOfBirth=?,m_doctor_qualification=?,m_doctor_email=?,m_doctor_phone=?,m_doctor_dateOfJoin=?,m_doctor_fees=?,m_doctor_workingDays=?     WHERE m_SLMCNO=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			preparedStmt.setString(1, m_doctor_name);
			preparedStmt.setString(2, m_doctor_address);
			preparedStmt.setString(3, m_doctor_speciality);
			preparedStmt.setString(4, m_doctor_sex);
			preparedStmt.setDate(5, m_doctor_dateOfBirth);
			preparedStmt.setString(6, m_doctor_qualification);
			preparedStmt.setString(7, m_doctor_email);
			preparedStmt.setInt(8, m_doctor_phone);
			preparedStmt.setDate(9, m_doctor_dateOfJoin);
			preparedStmt.setDouble(10, m_doctor_fees);
			preparedStmt.setString(11, m_doctor_workingDays);
			preparedStmt.setString(12, SLMCNO);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";

		} catch (Exception e) {
			output = "Error while updating the Doctor Details.";
			System.err.println(e.getMessage());
		}

		return output;

	}

	public String deleteDoctor(String m_SLMCNO) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for deleting.";

			}

			// create a prepared statement

			String query = "delete from m_doctor where m_SLMCNO=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, m_SLMCNO);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";

		} catch (Exception e) {
			output = "Error while deleting the Doctor.";
			System.err.println(e.getMessage());

		}

		return output;

	}

}
