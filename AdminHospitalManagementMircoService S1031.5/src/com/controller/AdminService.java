package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model.AdminDetails;

@Path("/AdminDetails")
public class AdminService {
	
	AdminDetails adminObj=new AdminDetails();
	
	
	@GET
	@Path("/readallAdmin")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {

		return adminObj.readAdmin();
	}

	@POST
	@Path("/insertAdmin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(String logindata) {
		
		JsonObject loginObject = new JsonParser().parse(logindata).getAsJsonObject();
		
		String Uid = loginObject.get("Uid").getAsString();
		String Uname = loginObject.get("userName").getAsString();
		String Password = loginObject.get("password").getAsString();
		
		String output = adminObj.insertAdmin(Uid, Uname, Password);
		return output;
	}
	
	@POST
	@Path("/adminlogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String login(String loginData) {
		String output="";
		String response = "Error While Logging";
		
		
		JsonObject loginObject = new JsonParser().parse(loginData).getAsJsonObject();
		
		//String Uid = loginObject.get("userName").getAsString();
		String Uname = loginObject.get("userName").getAsString();
		String Password = loginObject.get("password").getAsString();
		
		
		output = adminObj.Adminloging(Uname,Password);
		
		if(output.equalsIgnoreCase("success")) {
			
			AdminDetails adminOb=new AdminDetails();
			
			response = "success login";
		
			}
		else {
			
			response = "Error While Logging to the System";
		}
		
		return response;

}}
