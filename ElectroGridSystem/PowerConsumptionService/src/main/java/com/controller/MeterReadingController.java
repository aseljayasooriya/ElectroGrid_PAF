package main.java.com.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//For XML
import main.java.com.model.MeterReading;

import main.java.com.service.MeterReadingService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("/readings")
public class MeterReadingController {

	MeterReadingService meterReadingServiceObj = new MeterReadingService();

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMeterReading(MeterReading meterReadingObj) {
		JSONObject response = meterReadingServiceObj.insertMeterReading(meterReadingObj);
		if (response.get("status").equals("success")) {
			response.put("data", meterReadingObj);
			return Response.status(201).entity(response).build();
		} else {
			return Response.status(500).entity(response).build();
		}
	}

	// get all meter readings
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMeterReadings() {
		JSONArray response = meterReadingServiceObj.readAllMeterReadings();
		return Response.status(200).entity(response).build();
	}

	// get all meter readings by account number, year and month
	@GET
	@Path("/account/{accountNo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMeterReadings(@PathParam("accountNo") String accountNo,
										@QueryParam("year") int year,
										@QueryParam("month") int month) {
		JSONArray response = meterReadingServiceObj.readMeterReadingsByAccountNo(accountNo, year, month);
		return Response.status(201).entity(response).build();
	}

	// delete meter reading by account number, year and month
	@DELETE
	@Path("/account/{accountNo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMeterReading(@PathParam("accountNo") String accountNo,
									   @QueryParam("year") int year,
									   @QueryParam("month") int month) {
		JSONObject response = meterReadingServiceObj.deleteMeterReading(accountNo, year, month);
		if (response.get("status").equals("success")) {
			return Response.status(201).entity(response).build();
		} else {
			return Response.status(500).entity(response.get("message")).build();
		}
	}

	// update meter reading by account number, year and month
	@PUT
	@Path("/account/{accountNo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMeterReading(@PathParam("accountNo") String accountNo,
									   @QueryParam("year") int year,
									   @QueryParam("month") int month,
									   MeterReading meterReadingObj) {
		JSONObject response = meterReadingServiceObj.updateMeterReading(accountNo, year, month, meterReadingObj);
		if (response.get("status").equals("success")) {
			return Response.status(201).entity(response).build();
		} else {
			return Response.status(500).entity(response.get("message")).build();
		}
	}
}
