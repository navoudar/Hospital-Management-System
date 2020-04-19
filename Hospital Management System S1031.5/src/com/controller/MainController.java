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

import com.model.LogingInf;


@Path("/main")
public class MainController{
	
	public String loginStatus;
	
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
		
		//Address of the communicating micro service for logging
		WebTarget targetPatientLogin = client.target("http://localhost:8080/PatientManagementMicroServiceS1031.5/PatientModule/Patient/Login");
		WebTarget targetDoctorLogin = client.target("http://localhost:8080/DoctorManagementMicroServiceS1031.5/DoctorModule/doctor/Loging");
		WebTarget targetAdminLogin = client.target("http://localhost:8080/AdminHospitalManagementMircoServiceS1031.5/HospitalModule/AdminDetails/adminlogin");
		//WebTarget target = client.target("http://localhost:8080/PatientManagementMicroServiceS1031.5/PatientModule/patient/getDetails");
		
		//Model class object
		LogingInf login = new LogingInf();
		
		//Reading the JSON object values
		String Uname = loginObject.get("userName").getAsString();
		String password = loginObject.get("password").getAsString();
		String type = loginObject.get("userType").getAsString();
		
		//Bind data for model
		login.setUserName(Uname);
		login.setPassword(password);
		login.setType(type);
		
		// Login to the system according to user type
		if (type.equalsIgnoreCase("Doctor")) {

			response = targetDoctorLogin.request(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_HTML)
					.post(Entity.json(login), String.class);

			if (response.contains("success")) {
				loginStatus = "true";
				output = "<h1>Login Successfull for Doctor: " + Uname + "</h1>";

			} else {
				output = "<h1>Login Failed for Admin: " + Uname + "</h1><p>Check your user name and password</p>";
			}

		} else if (type.equalsIgnoreCase("Admin")) {

			response = targetAdminLogin.request(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_HTML)
					.post(Entity.json(login), String.class);

			if (response.contains("success")) {
				loginStatus = "true";
				output = "<h1>Login Successfull for Admin: " + Uname + "</h1>";

			} else {
				output = "<h1>Login Failed for Admin: " + Uname + "</h1><p>Check your user name and password</p>";
			}
		} else if (type.equalsIgnoreCase("Patient")) {

			response = targetPatientLogin.request(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_HTML)
					.post(Entity.json(login), String.class);

			if (response.contains("success")) {
				loginStatus = "true";
				output = "<h1>Login Successfull for Patient: " + Uname + "</h1>";

			} else {
				output = "<h1>Login Failed for Patient: " + Uname + "</h1><p>Check your user name and password</p>";
			}
		}else {
			output = "<h1>Inavalid User Type for: " + Uname + "</h1><p>Check the User Type</p><p>Available user types are Doctor, Admin and Patient</p>";
			loginStatus = "null";
		}
	
		
		
		//if the logging success only, call Patient Management micro service and get details
	/*	if(output.equalsIgnoreCase("success")) {
			
			Patient patient = new Patient();
			
			patient.setName(Uname);
			
			response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_HTML).post(Entity.json(patient),String.class);
		}else {
			
			response = "Error While Logging to the System";
		}
		*/
		return output;
	}
	
	@POST
	@Path("/makeAppoinment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String makeappoinment(String appoData) {
		String output="";
		String response = "";
		System.out.println("login status"+ loginStatus);
	//	if(loginStatus.contains("true")) {
		
		JsonObject appoinmnetObject = new JsonParser().parse(appoData).getAsJsonObject();
		
		// Create client to communicate with other micro service
		Client client = ClientBuilder.newClient();
		
		//Address of the communicating micro service for appoinment
		WebTarget targetAppoinment = client.target("http://localhost:8080/AppointmentManagementMicroServiceS1031.5/AppointmentModule/appointment/create");	
		
		response = targetAppoinment.request(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_HTML).post(Entity.json(appoData), String.class);
		
		if(response.contains("success")) {
			output = "<h1>Appoinmnet Created Successfuly </h1>";
		}else {
			output = "<h1>Error Creating the Apoointment </h1>";
		}
		//end of the login check
	/*	}else {
			output = "<p style='color:red'>To make appointment you should login ... !!!</p>";
		}*/
	return output;	
	}
	
}
