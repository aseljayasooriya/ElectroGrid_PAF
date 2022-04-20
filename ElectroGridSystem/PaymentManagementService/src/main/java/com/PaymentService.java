package com;

import model.Payment;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

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

}
