package com.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/payment")
public class PaymentService {

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {

		return "From  Payment";
	}
	
}
