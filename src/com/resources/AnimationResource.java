package com.resources;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.dao.AnimationControlsSetDAO;
import com.json.JsonGenerator;
import com.model.AnimationControlsSetDTO;
import com.util.Utils;

@Path("animation")
public class AnimationResource {
	public static String[] browse_entitlements = {"dmars.animation.defined.browse"};
	public static String[] post_entitlements = {"dmars.animation.defined.create"};
	public static String[] put_entitlements = {"dmars.animation.defined.update"};
	public static String[] delete_entitlements = {"dmars.animation.defined.delete"};
	
	@GET
	@Path("controls")
	@Produces("application/json")
	public String getControls(@Context HttpServletRequest request, 
			@QueryParam("animaticonObjName") String animaticonObjName){
		String result = null;
		try {
			Utils.checkAuth(request, browse_entitlements);
			if (animaticonObjName == null) {
				throw new Exception("Invalid parameters");
			} else {
				ArrayList<AnimationControlsSetDTO> animationControlSets = AnimationControlsSetDAO.getAnimationControlsSets(animaticonObjName);
				result = JsonGenerator.generateJson(animationControlSets);
			}
		} catch (Exception e) {
			result = JsonGenerator.generateErrorJson(e);
		}
		return result;
	}
	
	
	@POST
	@Path("controls")
	@Produces({MediaType.APPLICATION_JSON})
	public String postControls(@Context HttpServletRequest request,String body) {
		String result = null;
		try {
			Utils.checkAuth(request, post_entitlements);
			AnimationControlsSetDTO aset = (AnimationControlsSetDTO)JsonGenerator.generateTOfromJson(body, AnimationControlsSetDTO.class);
			AnimationControlsSetDAO.addAnimationControlSet(aset);
			result=JsonGenerator.generateSuccessJson(aset+" parsed successfully");
		} catch (Exception e) {
			result = JsonGenerator.generateErrorJson(e);
		}
		return result;
	}
	
	@DELETE
	@Path("controls")
	@Produces("application/json")
	public String updateControls(@Context HttpServletRequest request,String body) {
		String result = null;
		try {
			Utils.checkAuth(request, delete_entitlements);
			AnimationControlsSetDTO aset = (AnimationControlsSetDTO)JsonGenerator.generateTOfromJson(body, AnimationControlsSetDTO.class);
			AnimationControlsSetDAO.deleteAnimationControlSet(aset);
			result=JsonGenerator.generateSuccessJson(aset+" parsed successfully");
		} catch (Exception e) {
			result = JsonGenerator.generateErrorJson(e);
		}
		System.out.println("maintain "+result);
		return result;
	}

}
