package com;

import model.Bill;

import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType;


import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;

@Path("/Bills")
public class BillService {
	
	//Object creation
	Bill billObj = new Bill();
	
	@POST
	@Path("/")
	//to specify the input type as form data
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//produce a status message as an output
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBill(@FormParam("billCode") String billCode,@FormParam("accountNo") String accountNo,
            				 @FormParam("billMonth") String billMonth, @FormParam("units") String units, 
            				 @FormParam("meterReader_name") String meterReader_name) 
	{ 
		String output = billObj.insertBill(billCode, accountNo, billMonth, units, meterReader_name);
		return output;
	
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String raedBills()
	{
		return billObj.readBills();
	}
	
	@PUT
	@Path("/")
	//to specify the input type as form data
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//produce a status message as an output
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBill(@FormParam("billID") String billID,@FormParam("billCode") String billCode,
			                 @FormParam("accountNo") String accountNo,@FormParam("billMonth") String billMonth, 
			                 @FormParam("units") String units, @FormParam("meterReader_name") String meterReader_name) 
	{ 
		String output = billObj.updateBill(billID,billCode, accountNo, billMonth, units, meterReader_name);
		return output;
	
	}
	
	@DELETE
	@Path("/")
	//to specify the input type as form data
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//produce a status message as an output
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBill(@FormParam("billID") String billID) 
	{ 
		String output = billObj.deleteBill(billID);
		return output;
	
	}

}
