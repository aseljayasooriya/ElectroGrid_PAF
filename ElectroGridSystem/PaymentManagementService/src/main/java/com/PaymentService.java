package com;

import model.Payment;
import org.json.simple.JSONArray;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//For XML
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

@Path("/Payments")
public class PaymentService {

	Payment paymentObj = new Payment();

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBreakdown(@FormParam("accountNo") String accountNo,
			 						@FormParam("paymentAmount") String paymentAmount,
			 						@FormParam("paymentMethod") String paymentMethod,
			 						@FormParam("cardNo") String cardNo,
			 						@FormParam("email") String email) {

		String output = paymentObj.insertPayment(accountNo, paymentAmount, paymentMethod, cardNo, email);
		return output;
	}
	// get due payments
	@GET
	@Path("/account/{accountNo}/due")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getDuePayments(@PathParam("accountNo") String accountNo,
								 @QueryParam("asOfDate") String asOfDate) {
		return Response.status(200).entity(String.valueOf(paymentObj.getDueAmount(accountNo, asOfDate))).build();
	}


	//Read Payment

		@GET
		@Path("/")
		@Produces(MediaType.APPLICATION_JSON)
		public Response readPayment(@QueryParam("accountNo") String accountNo) {
			JSONArray response = paymentObj.readPayment(accountNo);
			return Response.status(200).entity(response).build();
		}

		//update payments
		@PUT
		@Path("/")
		//to specify the input type as form data
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		//produce a status message as an output
		@Produces(MediaType.TEXT_PLAIN)
		public String updatePayment(@FormParam("paymentID") String paymentID,@FormParam("accountNo") String accountNo,
				                 @FormParam("paymentAmount") String paymentAmount,@FormParam("paymentMethod") String paymentMethod,
				                 @FormParam("cardNo") String cardNo, @FormParam("email") String email)
		{
			String output = paymentObj.updatePayment(paymentID,accountNo, paymentAmount, paymentMethod, cardNo, email);
			return output;

		}

		//delete payments---------------------------------------------------------
		@DELETE
		@Path("/")
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deletePayment(String paymentData)
		{
		//Convert the input string to an XML document
		 org.jsoup.nodes.Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());

		//Read the value from the element <itemID>
		 String paymentID1 = doc.select("paymentID").text();
		 String output = paymentObj.deletePayment(paymentID1);
		return output;
		}



}
