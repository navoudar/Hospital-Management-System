package com.controller;

import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model.Doctor;

@Path("/doctor")
public class DoctorService {

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {

		return "From  Doctor";
	}
	
	@POST
	@Path("/Loging")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String doctorLoging(String details) {
		String output ="";
		Doctor doctor = new Doctor();
		String outPut = "";
		
		JsonObject doctorObj = new JsonParser().parse(details).getAsJsonObject();
		
		String userName = doctorObj.get("userName").getAsString();
		String password = doctorObj.get("password").getAsString();
		
		output = doctor.doctorLoging(userName, password);
		
		return output;	
	}
	
	

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateDoctor(String doctorData) {
		Doctor doctor = new Doctor();
		
		JsonObject docObject = new JsonParser().parse(doctorData).getAsJsonObject();
		
		String m_SLMCNO = docObject.get("m_SLMCNO").getAsString();
		String m_doctor_name = docObject.get("m_doctor_name").getAsString();
		String m_doctor_address = docObject.get("m_doctor_address").getAsString();
		String m_doctor_speciality = docObject.get("m_doctor_speciality").getAsString();
		String m_doctor_sex = docObject.get("m_doctor_sex").getAsString();
		String m_doctor_dateOfBirth = docObject.get("m_doctor_dateOfBirth").getAsString();
		String m_doctor_qualification = docObject.get("m_doctor_qualification").getAsString();
		String m_doctor_email = docObject.get("m_doctor_email").getAsString();
		int m_doctor_phone = docObject.get("m_doctor_phone").getAsInt();
		String m_doctor_dateOfJoin = docObject.get("m_doctor_dateOfJoin").getAsString();
		Double m_doctor_fees = docObject.get("m_doctor_fees").getAsDouble();
		String m_doctor_workingDays = docObject.get("m_doctor_workingDays").getAsString();
		
		String output = doctor.updateDoctor(m_SLMCNO, m_doctor_name, m_doctor_address, m_doctor_speciality, m_doctor_sex, Date.valueOf(m_doctor_dateOfBirth), m_doctor_qualification, m_doctor_email, m_doctor_phone, Date.valueOf(m_doctor_dateOfJoin), m_doctor_fees, m_doctor_workingDays);
		
		
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDoctor(String doctorData) {
		Doctor doctor = new Doctor();
		JsonObject docObject = new JsonParser().parse(doctorData).getAsJsonObject();
		
		String m_SLMCNO = docObject.get("m_SLMCNO").getAsString();
		
		String output = doctor.deleteDoctor(m_SLMCNO);
		return output;
	}
	
	
	
	
}
