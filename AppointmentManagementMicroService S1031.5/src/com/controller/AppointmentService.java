package com.controller;

import java.sql.Date;
import java.sql.Time;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model.Appointment;

@Path("/appointment")
public class AppointmentService {
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {

		return "From  Appointment";
		
	}
	
	
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String makeAppoiintment(String details) {
		
		String output = "";
		Appointment appointment = new Appointment();
		String outPut = "";
		
		JsonObject appointmentObj = new JsonParser().parse(details).getAsJsonObject();
		
		
		//String m_appointment_no = appointmentObj.get("m_appointment_no").getAsString();
		String m_appointment_patient = appointmentObj.get("m_appointment_patient").getAsString();
		String m_appointment_category = appointmentObj.get("m_appointment_category").getAsString();
		String m_appointment_hospital = appointmentObj.get("m_appointment_hospital").getAsString();
		String m_appointment_date = appointmentObj.get("m_appointment_date").getAsString();
		String m_appointment_time = appointmentObj.get("m_appointment_time").getAsString();
		String m_appointment_description = appointmentObj.get("m_appointment_description").getAsString();
		
		
		output = appointment.makeAppointment(m_appointment_patient, m_appointment_category, m_appointment_hospital,m_appointment_date, m_appointment_time, m_appointment_description);
		
		return output;
		
	}
	
	
}
