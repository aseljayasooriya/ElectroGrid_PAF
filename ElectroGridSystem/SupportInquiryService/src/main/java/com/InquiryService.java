package com;

import model.Inquiry;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document; 

@Path("/Inquiry")
public class InquiryService {


//create object
	Inquiry inqObj = new Inquiry();
	
	//Insert Inquiry
	@POST
	@Path("/")
	
	//consumes annotation used to the input type as form data
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	
	//Create status message and output in plain text
	@Produces(MediaType.TEXT_PLAIN)
	
	//specifying the form elements as the parameters of the insertInquiry()
	public String insertInquiry(@FormParam("inquiryTitle") String inquiryTitle,
								@FormParam("inquiryDesc") String inquiryDesc,
								@FormParam("contactNum") String contactNum) {
		
		String output=inqObj.insertInquiry(inquiryTitle, inquiryDesc, contactNum);
		return output;
	}
	
	//Read Inquiry
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readInquiry() {
		return inqObj.readInquiry();
	}
	
	
	//Update Inquiry
	
	@PUT
	@Path("/")
	//consumes annotation used to the input type as form data
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	
	//Create status message and output in plain text
	@Produces(MediaType.TEXT_PLAIN)
	
	public String updateInquiry(@FormParam("inquiryID") int inquiryID,
								@FormParam("inquiryTitle") String inquiryTitle,
								@FormParam("inquiryDesc") String inquiryDesc,
								@FormParam("contactNum") int contactNum) {
		String output =inqObj.updateInquiry(inquiryID, inquiryTitle, inquiryDesc, contactNum);
		return output;
	}
	
	//Delete Inquiry
	@DELETE
	@Path("/")
	
	//consumes annotation used to the input type as form data
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	
	//Create status message and output in plain text
	@Produces(MediaType.TEXT_PLAIN)
	
	public String deletInquiry(@FormParam("inquiryID") int inquiryID) {
		String output =inqObj.deletInquiry(inquiryID);
		return output;
	}
	
	
}

