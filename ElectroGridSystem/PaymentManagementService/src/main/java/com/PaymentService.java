package com;

import javax.swing.text.Document;
//For REST Service
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//For XML
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

import model.Payment; 

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
	
	
	//Read Payment
	
		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		public String readPayment() {
			return paymentObj.readPayment();
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
