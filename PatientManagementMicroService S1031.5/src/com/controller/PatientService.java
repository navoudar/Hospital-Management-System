package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model.Patient;

@Path("/patient")
public class PatientService {

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {

		return "From  Patient";
	}
	
	@POST
	@Path("/getDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String getSpecificPatientDetails(String details) {
		
		Patient patient = new Patient();
		
		JsonObject itemObject = new JsonParser().parse(details).getAsJsonObject();
		
		String Uname = itemObject.get("name").getAsString();
		String output = patient.readItems(Uname);
		return output;
	}
}
