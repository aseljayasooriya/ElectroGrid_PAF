package com;

import model.BreakDown;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document; 

@Path("/Breakdowns")
public class BreakDownService {
	
	BreakDown breakdownObj = new BreakDown();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readBreakdowns()
	{
		return "Hello";
	} 
}
