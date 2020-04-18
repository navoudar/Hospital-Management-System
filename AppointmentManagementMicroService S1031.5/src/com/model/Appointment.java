package com.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
			
			

			output =  "Doctor Details Inserted successfully";
		} catch (Exception e) {
			output =  "Error while inserting the Doctor Details .";
			System.err.println(e.getMessage());
		}

		return output;
	
	}
	
		
		
		
		
		
		
		
		
	
	
}
