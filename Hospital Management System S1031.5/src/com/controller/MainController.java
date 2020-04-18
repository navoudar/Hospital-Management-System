package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model.Loging;
import com.model.Patient;

@Path("/main")
public class MainController {

	@POST
	@Path("/loging")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String login(String loginData) {
		String output="";
		String response = "Error While Logging";
		// convert incoming String as a JSON object
		JsonObject loginObject = new JsonParser().parse(loginData).getAsJsonObject();
		
		// Create client to communicate with other micro service
		Client client = ClientBuilder.newClient();
		
		//Address of the communicating micro service
		WebTarget target = client.target("http://localhost:8080/PatientManagementMicroServiceS1031.5/PatientModule/patient/getDetails");
		
		//Model class object
		Loging loging =  new Loging();
		
		//Reading the JSON object values
		String Uname = loginObject.get("userName").getAsString();
		String password = loginObject.get("password").getAsString();
		String type = loginObject.get("userType").getAsString();
		
		//Calling the model class to loging
		output = loging.userlogging(Uname, password, type);
		
		//if the logging success only, call Patient Management micro service and get details
		if(output.equalsIgnoreCase("success")) {
			
			Patient patient = new Patient();
			
			patient.setName(Uname);
			
			response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_HTML).post(Entity.json(patient),String.class);
		}else {
			
			response = "Error While Logging to the System";
		}
		
		return response;
	}
	
	
}
