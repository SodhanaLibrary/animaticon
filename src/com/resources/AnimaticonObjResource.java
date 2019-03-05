package com.resources;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.dao.AnimaticonObjSetsDAO;
import com.json.JsonGenerator;
import com.model.AnimaticonObjSetsDTO;
import com.util.S3Util;
import com.util.Utils;

@Path("animaticon")
public class AnimaticonObjResource {
	public static String[] browse_entitlements = {"dmars.icons.browse"};
	public static String[] post_entitlements = {"dmars.icons.create"};
	public static String[] put_entitlements = {"dmars.icons.update"};
	public static String[] delete_entitlements = {"dmars.icons.delete"};
	
	@GET
	@Path("objs")
	@Produces("application/json")
	public String getControls(@Context HttpServletRequest request,
			@QueryParam("uuid") String uuid){
		String result = null;
		try {
			Utils.checkAuth(request, browse_entitlements);
			if (uuid == null) {
				throw new Exception("Invalid parameters");
			} else {
				ArrayList<AnimaticonObjSetsDTO> animationControlSets = AnimaticonObjSetsDAO.getAnimaticonObjSets(uuid);
				result = JsonGenerator.generateJson(animationControlSets);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = JsonGenerator.generateErrorJson(e);
		}
		return result;
	}
	
	@GET
	@Path("search")
	@Produces("application/json")
	public String getSearchResults(@Context HttpServletRequest request,
			@QueryParam("q") String q){
		String result = null;
		try {
			Utils.checkAuth(request, browse_entitlements);
			ArrayList<AnimaticonObjSetsDTO> animationControlSets = AnimaticonObjSetsDAO.filterAnimaticonObjSets(q);
			result = JsonGenerator.generateJson(animationControlSets);
		} catch (Exception e) {
			e.printStackTrace();
			result = JsonGenerator.generateErrorJson(e);
		}
		return result;
	}
	
	
	@POST
	@Path("objs")
	@Produces({MediaType.APPLICATION_JSON})
	public String postControls(@Context HttpServletRequest request, String body) {
		String result = null;
		try {
			Utils.checkAuth(request, post_entitlements);
			AnimaticonObjSetsDTO aset = (AnimaticonObjSetsDTO)JsonGenerator.generateTOfromJson(body, AnimaticonObjSetsDTO.class);
			AnimaticonObjSetsDTO res = AnimaticonObjSetsDAO.addAnimaticonObjSet(aset);
			result=JsonGenerator.generateJson(res);
		} catch (Exception e) {
			e.printStackTrace();
			result = JsonGenerator.generateErrorJson(e);
		}
		return result;
	}
	
	@PUT
	@Path("objs")
	@Produces({MediaType.APPLICATION_JSON})
	public String putControls(@Context HttpServletRequest request, String body) {
		String result = null;
		try {
			Utils.checkAuth(request, put_entitlements);
			AnimaticonObjSetsDTO aset = (AnimaticonObjSetsDTO)JsonGenerator.generateTOfromJson(body, AnimaticonObjSetsDTO.class);
			AnimaticonObjSetsDAO.updateAnimaticonObjSet(aset);
			result=JsonGenerator.generateSuccessJson(aset+" parsed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			result = JsonGenerator.generateErrorJson(e);
		}
		return result;
	}
	
	@DELETE
	@Path("objs")
	@Produces("application/json")
	public String updateControls(@Context HttpServletRequest request, String body) {
		String result = null;
		try {
			Utils.checkAuth(request, delete_entitlements);
			AnimaticonObjSetsDTO aset = (AnimaticonObjSetsDTO)JsonGenerator.generateTOfromJson(body, AnimaticonObjSetsDTO.class);
			AnimaticonObjSetsDAO.deleteAnimaticonObjSet(aset);
			S3Util.deleteImage("icons/icon-"+aset.getUuid()+".png");
			result=JsonGenerator.generateSuccessJson(aset+" parsed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			result = JsonGenerator.generateErrorJson(e);
		}
		System.out.println("maintain "+result);
		return result;
	}
}
