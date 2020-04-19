package com.controller;

import javax.ws.rs.core.MediaType;

import javax.ws.rs.*;
import com.google.gson.*;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import com.model.HosRooms;
import com.model.Hospital;

@Path("/hospital")
public class HospitalService {
	Hospital hosObj=new Hospital();
	HosRooms hosRoomObj=new HosRooms();
	@GET
	@Path("/viewall")
	@Produces(MediaType.TEXT_HTML)
	public String readDetails() {

		//return "From  Hospital";
		
		return hosObj.readHospitalDetails();
	}
	
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertHospitalDetails(String hospitaldata) {
		JsonObject hospiObj = new JsonParser().parse(hospitaldata).getAsJsonObject();
		
		String hospital_id = hospiObj.get("hospital_id").getAsString();
		String hospital_name = hospiObj.get("hospital_name").getAsString();
		String hospital_location = hospiObj.get("hospital_location").getAsString();
		int hospital_availableRooms = hospiObj.get("hospital_availableRooms").getAsInt();
		String hospital_labs =hospiObj.get("hospital_labs").getAsString();

		String output = hosObj.insertHospitalDetails(hospital_id, hospital_name, hospital_location, hospital_availableRooms,hospital_labs);
		return output;
		
	}
	@PUT
	@Path("/hospitalupdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateHospitalDetails(String hospitalData) {

		// Convert the input string to a JSON object
		JsonObject hospiObj = new JsonParser().parse(hospitalData).getAsJsonObject();

		// Read the values from the JSON object
		String hospital_id = hospiObj.get("hospital_id").getAsString();
		String hospital_name = hospiObj.get("hospital_name").getAsString();
		String hospital_location = hospiObj.get("hospital_location").getAsString();
		int hospital_availableRooms = hospiObj.get("hospital_availableRooms").getAsInt();
		String hospital_labs =hospiObj.get("hospital_labs").getAsString();

		String output = hosObj.updateHospitalDetails(hospital_id, hospital_name, hospital_location, hospital_availableRooms, hospital_labs);

		return output;
	}

	@DELETE
	@Path("/hospitaldelete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String hospitalData) {

		
		JsonObject hospiObj = new JsonParser().parse(hospitalData).getAsJsonObject();

		// Read the value from the element <itemID>
		String hospital_id = hospiObj.get("hospital_id").getAsString();

		String output = hosObj.deleteHospitalDetails(hospital_id);

		return output;
	}
	
	@GET
	@Path("/getallroom")
	@Produces(MediaType.TEXT_HTML)
	public String readroomDetails() {
		
		
		return hosRoomObj.readRoomDetails();
	}
	
	@POST
	@Path("/insertroom")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertRooms(String roomdata) {
		JsonObject hospiObjinsert = new JsonParser().parse(roomdata).getAsJsonObject();
		
		String room_code=hospiObjinsert.get("room_code").getAsString();
		String room_hospital_name=hospiObjinsert.get("room_hospital_name").getAsString();
		String room_doctor_name=hospiObjinsert.get("room_doctor_name").getAsString();
		String room_date=hospiObjinsert.get("room_date").getAsString();
		String room_time=hospiObjinsert.get("room_time").getAsString();
		
		String output=hosRoomObj.insertRooms(room_code, room_hospital_name, room_doctor_name, room_date, room_time);
		return output;
		
	}
	@PUT
	@Path("/updateroom")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateRoomDetails(String roomdata) {
		
		JsonObject hospiObj = new JsonParser().parse(roomdata).getAsJsonObject();
		
		String room_code=hospiObj.get("room_code").getAsString();
		String room_hospital_name=hospiObj.get("room_hospital_name").getAsString();
		String room_doctor_name=hospiObj.get("room_doctor_name").getAsString();
		String room_date=hospiObj.get("room_date").getAsString();
		String room_time=hospiObj.get("room_time").getAsString();
		
		String output=hosRoomObj.updateRoomDetails(room_code, room_hospital_name, room_doctor_name, room_date, room_time);
		return output;

		
	}
	
	@DELETE
	@Path("/deleteroom")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteRooms(String roomdata) {
		JsonObject hospiObjdelete = new JsonParser().parse(roomdata).getAsJsonObject();
		String room_code=hospiObjdelete.get("room_code").getAsString();
		
		String output = hosRoomObj.deleteRooms(room_code);
		return output;
		
		
	}
	
}
