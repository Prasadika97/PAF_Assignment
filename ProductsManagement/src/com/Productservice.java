package com;

import model.Product;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.*;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Product")
public class Productservice {
	Product ProductObj = new Product();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readProduct() {
		return ProductObj.readProduct();
	}

	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertProduct(@FormParam("name") String pName, 
			@FormParam("price") String pPrice,
			@FormParam("desct") String pDes) {
		String output = ProductObj.insertProduct(pName, pPrice, pDes);
		return output;

	}

	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateProduct(String productData) {
		// Convert the input string to a JSON object
		JsonObject ProObject = new JsonParser().parse(productData).getAsJsonObject();

		// Read the values from the JSON object
		String pId = ProObject.get("id").getAsString();
		String pname = ProObject.get("name").getAsString();
		String price = ProObject.get("price").getAsString();
		String pdes = ProObject.get("desct").getAsString();

		String output = ProductObj.updateProduct(pId, pname, price, pdes);
		return output;
	}

	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProduct(String productData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(productData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String pId = doc.select("id").text();
		String output = ProductObj.deleteProduct(pId);
		return output;
	}
}
