package com.resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.dao.UsersDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.json.JsonGenerator;
import com.model.GoogleDTO;
import com.model.SessionDTO;
import com.util.Setup;
import com.util.Utils;

@Path("auth")
public class AuthResource {
	
	@GET
	@Path("oauth2callback")
	public String getSession(@Context HttpServletRequest request, @Context HttpServletResponse response, @QueryParam("code") String code){
		String result = null;
		try {
			// format parameters to post
            String urlParameters = "code="
                    + code
                    + "&client_id="+Setup.GOOGLE_CLIENT_ID 
                    + "&client_secret="+Setup.GOOGLE_CLIENT_SECRET
                    + "&redirect_uri="+Setup.GOOGLE_CLIENT_REDIRECT_URL
                    + "&grant_type=authorization_code";
            
            //post parameters
            URL url = new URL("https://accounts.google.com/o/oauth2/token");
            URLConnection urlConn = url.openConnection();
            urlConn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(
                    urlConn.getOutputStream());
            writer.write(urlParameters);
            writer.flush();
            
            //get output in outputString 
            String line, outputString = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
            System.out.println(outputString);
            
            //get Access Token 
            JsonObject json = (JsonObject)new JsonParser().parse(outputString);
            String access_token = json.get("access_token").getAsString();
            System.out.println(access_token);

            //get User Info 
            url = new URL(
                    "https://www.googleapis.com/oauth2/v1/userinfo?access_token="
                            + access_token);
            urlConn = url.openConnection();
            outputString = "";
            reader = new BufferedReader(new InputStreamReader(
                    urlConn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
            System.out.println(outputString);
            
            // Convert JSON response into Pojo class
            GoogleDTO data = new Gson().fromJson(outputString, GoogleDTO.class);
            SessionDTO sessionDto = UsersDAO.getSessionData(data.getEmail());
            sessionDto.setEmail(data.getEmail());
            sessionDto.setName(data.getName());
            if(sessionDto.getEntitlements().size() == 0) {
            	sessionDto.setEntitlements(new ArrayList<String>(Arrays.asList(Utils.DEFAULT_ENTITLEMENTS)));
            }
            request.getSession().setAttribute("sessionData", sessionDto);
            writer.close();
            reader.close();
            result = outputString;
            response.sendRedirect("http://www.doodlemars.com");
		} catch (Exception e) {
			e.printStackTrace();
			result = JsonGenerator.generateErrorJson(e);
		}
		return result;
	}
	
	
	@GET
	@Path("session")
	@Produces({MediaType.APPLICATION_JSON})
	public String getSession(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		String result = null;
		try {
			HttpSession session = request.getSession(false);
			if(session == null) {
				session = request.getSession(true);
			}
			Object sessionData = session.getAttribute("sessionData");		
			if(sessionData == null) {
				result = JsonGenerator.generateJson(Utils.getOpenUserSession());
			} else {
				result = JsonGenerator.generateJson(sessionData);
			}
			session.setMaxInactiveInterval(30*60);
		} catch (Exception e) {
			result = JsonGenerator.generateErrorJson(e);
		}
		return result;
	}
	
	@GET
	@Path("logout")
	@Produces({MediaType.APPLICATION_JSON})
	public String logout(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		String result = null;
		try {
			HttpSession session = request.getSession(false);
			session.setAttribute("sessionData", null);
			result = JsonGenerator.generateSuccessJson("Logged out successfully");
		} catch (Exception e) {
			result = JsonGenerator.generateErrorJson(e);
		}
		return result;
	}

}
