package com.resources;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.dao.AnimaticonBannerDAO;
import com.json.JsonGenerator;
import com.model.AnimaticonBannerDTO;
import com.util.S3Util;
import com.util.Utils;

@Path("animaticonBanner")
public class AnimaticonBannerResource {
	public static String[] browse_entitlements = {"dmars.banners.browse"};
	public static String[] post_entitlements = {"dmars.banners.create"};
	public static String[] put_entitlements = {"dmars.banners.update"};
	public static String[] delete_entitlements = {"dmars.banners.delete"};
	
	@GET
	@Path("banners")
	@Produces("application/json")
	public String getControls(@Context HttpServletRequest request,
			@QueryParam("uuid") String uuid){
		String result = null;
		try {
			Utils.checkAuth(request, browse_entitlements);
			if (uuid == null) {
				throw new Exception("Invalid parameters");
			} else {
				ArrayList<AnimaticonBannerDTO> animationControlSets = AnimaticonBannerDAO.getAnimaticonBanner(uuid);
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
			ArrayList<AnimaticonBannerDTO> animationControlSets = AnimaticonBannerDAO.filterAnimaticonBanner(q);
			result = JsonGenerator.generateJson(animationControlSets);
		} catch (Exception e) {
			e.printStackTrace();
			result = JsonGenerator.generateErrorJson(e);
		}
		return result;
	}
	
	
	@POST
	@Path("banners")
	@Produces({MediaType.APPLICATION_JSON})
	public String postControls(@Context HttpServletRequest request, String body) {
		String result = null;
		try {
			Utils.checkAuth(request, post_entitlements);
			AnimaticonBannerDTO aset = (AnimaticonBannerDTO)JsonGenerator.generateTOfromJson(body, AnimaticonBannerDTO.class);
			AnimaticonBannerDTO res = AnimaticonBannerDAO.addAnimaticonObjSet(aset);
			result=JsonGenerator.generateJson(res);
		} catch (Exception e) {
			e.printStackTrace();
			result = JsonGenerator.generateErrorJson(e);
		}
		return result;
	}
	
	@PUT
	@Path("banners")
	@Produces({MediaType.APPLICATION_JSON})
	public String putControls(@Context HttpServletRequest request, String body) {
		String result = null;
		try {
			Utils.checkAuth(request, put_entitlements);
			AnimaticonBannerDTO aset = (AnimaticonBannerDTO)JsonGenerator.generateTOfromJson(body, AnimaticonBannerDTO.class);
			AnimaticonBannerDAO.updateAnimaticonObjSet(aset);
			result=JsonGenerator.generateSuccessJson(aset+" parsed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			result = JsonGenerator.generateErrorJson(e);
		}
		return result;
	}
	
	@DELETE
	@Path("banners")
	@Produces("application/json")
	public String updateControls(@Context HttpServletRequest request, String body) {
		String result = null;
		try {
			Utils.checkAuth(request, delete_entitlements);
			AnimaticonBannerDTO aset = (AnimaticonBannerDTO)JsonGenerator.generateTOfromJson(body, AnimaticonBannerDTO.class);
			AnimaticonBannerDAO.deleteAnimaticonObjSet(aset);
			S3Util.deleteImage("banners/"+aset.getUuid()+".png");
			result=JsonGenerator.generateSuccessJson(aset+" parsed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			result = JsonGenerator.generateErrorJson(e);
		}
		System.out.println("maintain "+result);
		return result;
	}

}
