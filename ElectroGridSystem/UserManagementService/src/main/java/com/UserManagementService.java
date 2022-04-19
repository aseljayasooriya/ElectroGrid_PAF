package com;

import model.UserManagement;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Users")
public class UserManagementService {
	UserManagement userObj = new UserManagement();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readUsers()
	 {
		return userObj.readUsers();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertUser(@FormParam("userNIC") String userNIC, 
			@FormParam("userName") String userName,
			@FormParam("userAddress") String userAddress, 
			@FormParam("userType") String userType,
			@FormParam("userSector") String userSector) {
		String output = userObj.insertUser(userNIC, userName, userAddress, userType,userSector);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUser(String userData)
	{
	//Convert the input string to a JSON object
	 JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject();
	//Read the values from the JSON object
	 String userID = userObject.get("userID").getAsString();
	 String userNIC = userObject.get("userNIC").getAsString();
	 String userName = userObject.get("userName").getAsString();
	 String userAddress = userObject.get("userAddress").getAsString();
	 String userType = userObject.get("userType").getAsString();
	 String userSector = userObject.get("userSector").getAsString();
	 String output = userObj.updateUser(userID, userNIC, userName, userAddress, userType, userSector);
	return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteUser(String userData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(userData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String userID = doc.select("userID").text();
	 String output = userObj.deleteUser(userID);
	return output;
	}

}
