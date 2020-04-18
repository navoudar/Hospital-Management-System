package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Patient {
	
	String name;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Connection connect() {

		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management_system?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;

	}
	
	public String readItems(String Uname) {
		
		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>User ID</th><th>User Name</th><th>Name</th><th>Password</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from m_patient";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				
				String UserID = rs.getString("m_patient_id");
				String UserName = rs.getString("m_patient_userName");
				String Name = rs.getString("m_patient_name");
				String Password = rs.getString("m_patient_password");

				// Add into the html table
				output += "<tr><td>" + UserID + "</td>";
				output += "<td>" + UserName + "</td>";
				output += "<td>" + Name + "</td>";
				output += "<td>" + Password + "</td>";

				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"items.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						+ "<input name=\"itemID\" type=\"hidden\" value=\"" + UserID + "\">" + "</form></td></tr>";

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
	
}
