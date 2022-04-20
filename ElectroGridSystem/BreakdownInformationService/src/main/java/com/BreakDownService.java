package com;

import model.BreakDown;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/Breakdowns")
public class BreakDownService {
	
	BreakDown breakdownObj = new BreakDown();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readBreakdowns()
	{
		return breakdownObj.readBreakdowns();
	} 
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBreakdown(@FormParam("breakdownSector") String breakdownSector,
			 						@FormParam("breakdownDate") String breakdownDate,
			 						@FormParam("startTime") String startTime,
			 						@FormParam("endTime") String endTime,
			 						@FormParam("breakdownType") String breakdownType) {
		
		String output = breakdownObj.insertBreakdown(breakdownSector, breakdownDate, startTime, endTime, breakdownType);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBreakdown(@FormParam("breakdownID") String breakdownID,
									@FormParam("breakdownSector") String breakdownSector,
			 						@FormParam("breakdownDate") String breakdownDate,
			 						@FormParam("startTime") String startTime,
			 						@FormParam("endTime") String endTime,
			 						@FormParam("breakdownType") String breakdownType) {
		String output = breakdownObj.updateBreakdown(breakdownID, breakdownSector, breakdownDate, startTime, endTime, breakdownType);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(@FormParam("breakdownID") String breakdownID)
	{
		String output = breakdownObj.deleteBreakdown(breakdownID);
		return output;
	}

}
