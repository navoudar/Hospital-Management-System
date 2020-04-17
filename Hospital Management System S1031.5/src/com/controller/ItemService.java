package com.controller;

import com.model.Item;


//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//For JSON
import com.google.gson.*;

import org.glassfish.jersey.client.ClientConfig;
//For XML
import org.jsoup.*;

import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Items")
public class ItemService {

	Item itemObj = new Item();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {

		return itemObj.readItems();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(@FormParam("itemCode") String itemCode, @FormParam("itemName") String itemName,
			@FormParam("itemPrice") String itemPrice, @FormParam("itemDesc") String itemDesc) {
		String output = itemObj.insertItem(itemCode, itemName, itemPrice, itemDesc);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String itemData) {

		// Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();

		// Read the values from the JSON object
		String itemID = itemObject.get("itemID").getAsString();
		String itemCode = itemObject.get("itemCode").getAsString();
		String itemName = itemObject.get("itemName").getAsString();
		String itemPrice = itemObject.get("itemPrice").getAsString();
		String itemDesc = itemObject.get("itemDesc").getAsString();

		String output = itemObj.updateItem(itemID, itemCode, itemName, itemPrice, itemDesc);

		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData) {

		// Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String itemID = doc.select("itemID").text();

		String output = itemObj.deleteItem(itemID);

		return output;
	}
	
	@GET
	@Path("/lab")
	@Produces(MediaType.TEXT_PLAIN)
	public String testApi() {
		
		Client client = ClientBuilder.newClient();
			
		WebTarget target = client.target("http://localhost:8080/Lab6Rest/ItemService/Items/test");
		
		String output = target.request(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println(
				target.request(MediaType.TEXT_PLAIN).get(String.class)
				);
		
		return output;
	}
	
	
	@POST
	@Path("/invoker")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String postcheck(String temp) {
		
		JsonObject itemObject = new JsonParser().parse(temp).getAsJsonObject();
		
		Client client = ClientBuilder.newClient();
		
		WebTarget target = client.target("http://localhost:8080/Lab6Rest/ItemService/Items/insert");
		
		Item item = new Item();
		
		item.setItemName(itemObject.get("itemName").getAsString());
		item.setItemCode(itemObject.get("itemCode").getAsString());
		item.setItemPrice(itemObject.get("itemPrice").getAsString());
		item.setItemDesc(itemObject.get("itemDesc").getAsString());
		
		
		String response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN_TYPE).post(Entity.json(item),String.class);
		

		return response;
	}
	
}
