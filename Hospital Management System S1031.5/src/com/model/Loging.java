package com.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class Loging {

	String username;
	String password;
	String type;
	
	String userId;
	String PateintName;
	
	LocalDateTime lastLogin = LocalDateTime.now();

	//Getters and Setters
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	// Database connection method
	private Connection connect() {

		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Because of a JDBC driver issue we had to provide the time zone to the driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management_system?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;

	}
	
	// user logging method
	public String userlogging(String Uname, String Password, String Type) {
	
		String output="test";
		
		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for logging.";
			}

			if(Type.contentEquals("Patient")) {
				
			//	String query = " select m_patientid, m_patient_name, m_patient_userName, m_patient_password from m_patient where m_patient_userName =? AND m_patient_password =?";
				String query = "select * from m_patient";
			//	String query = "SELECT m_patient_id, m_patient_name FROM m_patient WHERE m_patient_userName =? AND m_patient_password =?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				
				// binding values
			//	preparedStmt.setString(1, Uname);
			//	preparedStmt.setString(2, Password);
				
				// execute the statement
				ResultSet rs = preparedStmt.executeQuery(query);
					
					while(rs.next()) {
						username = rs.getString("m_patient_userName");
						password = rs.getString("m_patient_password");
						userId = rs.getString("m_patient_id");
						PateintName = rs.getString("m_patient_name");
						
					}
		
				
				con.close();
				
			}
		
			
			if(username.equalsIgnoreCase(Uname) && password.equalsIgnoreCase(Password)) {
				output = "success";
			}
			
			
		} catch (Exception e) {
			output = "Failed";
			System.err.println(e.getMessage());
		}

		return output;
	}
	
}
