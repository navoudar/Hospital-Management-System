package com.controller;

import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.model.Payment;

@Path("/payment")
public class PaymentService {

	Payment payment = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {

		return "From  Payment";
	}

	@GET
	@Path("/allPayments")
	@Produces(MediaType.TEXT_HTML)
	public String getAllPayments() {

		return payment.getAllPayments();
	}

	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String testInsert(String temp) {

		String output = "";
		String response = "";
		// Convert the input string to a JSON object
		JsonObject paymentObject = new JsonParser().parse(temp).getAsJsonObject();

		// Create client to communicate with other micro service
		Client client = ClientBuilder.newClient();

		// Address of the communicating micro service for logging
		// WebTarget targetvalidateAppoinmnet =
		// client.target("http://localhost:8080/AppointmentManagementMicroServiceS1031.5/AppointmentModule/appointment/create");

		// Read the values from the JSON object
		String t_payment_no = paymentObject.get("t_payment_no").getAsString();
		String t_payment_appointment = paymentObject.get("t_payment_appointment").getAsString();
		String t_payment_date = paymentObject.get("t_payment_date").getAsString();
		String t_payment_time = paymentObject.get("t_payment_time").getAsString();
		String t_payment_customerName = paymentObject.get("t_payment_customerName").getAsString();
		String t_payment_cardNo = paymentObject.get("t_payment_cardNo").getAsString();
		String t_payment_cardtype = paymentObject.get("t_payment_cardtype").getAsString();
		String t_payment_amount = paymentObject.get("t_payment_amount").getAsString();

		// String ValidateAppoinmnet =
		// targetvalidateAppoinmnet.request(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_HTML).post(Entity.json(temp),
		// String.class);

		String Validateoutput = payment.validateCard(Integer.parseInt(t_payment_cardNo));

		if (Validateoutput.equalsIgnoreCase("valid")) {
			output = payment.insertPayment(t_payment_no, t_payment_appointment, t_payment_date, t_payment_time,
					t_payment_customerName, t_payment_cardNo, t_payment_cardtype, t_payment_amount);
			if (output.contains("success")) {
				response = "<p style='color:green'> Payment Created Successfully ....!</p>";
			} else {
				response = "<p style='color:red'> Error While making the Payment ....!</p>";
			}

		} else {
			response = "<p style='color:red'> Not a Valid Card Number ....!</p>";
		}

		return response;
	}

	@PUT
	@Path("/editPayment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateDoctor(String payData) {
		String output = "";
		String response = "";
		Payment payment = new Payment();

		JsonObject paymentObject = new JsonParser().parse(payData).getAsJsonObject();

		String t_payment_no = paymentObject.get("t_payment_no").getAsString();
		String t_payment_appointment = paymentObject.get("t_payment_appointment").getAsString();
		String t_payment_date = paymentObject.get("t_payment_date").getAsString();
		String t_payment_time = paymentObject.get("t_payment_time").getAsString();
		String t_payment_customerName = paymentObject.get("t_payment_customerName").getAsString();
		String t_payment_cardNo = paymentObject.get("t_payment_cardNo").getAsString();
		String t_payment_cardtype = paymentObject.get("t_payment_cardtype").getAsString();
		String t_payment_amount = paymentObject.get("t_payment_amount").getAsString();

		String Validateoutput = payment.validateCard(Integer.parseInt(t_payment_cardNo));

		if (Validateoutput.equalsIgnoreCase("valid")) {
			output = payment.updatePayment(t_payment_no, t_payment_appointment, t_payment_date, t_payment_time,
					t_payment_customerName, t_payment_cardNo, t_payment_cardtype, t_payment_amount);
			if (output.contains("success")) {
				response = "<p style='color:green'> Payment Updated Successfully ....!</p>";
			} else {
				response = "<p style='color:red'> Error While Updating the Payment ....!</p>";
			}

		} else {
			response = "<p style='color:red'> Not a Valid Card Number ....!</p>";
		}

		return response;
	}

	@DELETE
	@Path("/deletePaymet")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDoctor(String payData) {
		String output = "";
		String response = "";
		Payment payment = new Payment();
		JsonObject paymentObject = new JsonParser().parse(payData).getAsJsonObject();

		String t_payment_no = paymentObject.get("t_payment_no").getAsString();

		output = payment.deletePayment(t_payment_no);

		if (output.equalsIgnoreCase("success")) {
			response = "<p style='color:green'> Payment Removed Successfully ....!</p>";
		} else {
			response = "<p style='color:red'> Error While removing the Payment ....!</p>";
		}
		return response;
	}
}
