package com.util;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.model.SessionDTO;

public class Utils {
	
  public static final String[] DEFAULT_ENTITLEMENTS = {"dmars.banners.browse",
		  "dmars.animation.defined.browse",
		  "dmars.export.img",
		  "dmars.icons.browse"};
  public static final String[] ADMIN_ENTITLEMENTS = {
		  "dmars.animation.defined.browse",
		  "dmars.animation.defined.create",
		  "dmars.animation.defined.delete",
		  "dmars.animation.defined.update",
		  "dmars.banners.browse",
		  "dmars.banners.create",
		  "dmars.banners.delete",
		  "dmars.banners.update",
		  "dmars.export.img",
		  "dmars.icons.browse",
		  "dmars.icons.create",
		  "dmars.icons.delete",
		  "dmars.icons.update"
  };
  
  public static SessionDTO getOpenUserSession() {
      SessionDTO sessionDto = new SessionDTO();
      sessionDto.setEntitlements(new ArrayList<String>(Arrays.asList(DEFAULT_ENTITLEMENTS)));
	  return sessionDto;
  }
  
  public static boolean checkAuth(HttpServletRequest request, String[] entitlements) throws DmarsAuthException {
	  HttpSession session = request.getSession();
	  Object sessionData = session.getAttribute("sessionData");
	  SessionDTO sessionDto = null;
	  if(sessionData != null) {
		  sessionDto = (SessionDTO)(sessionData);  
	  } else {
		  sessionDto = getOpenUserSession();
	  }
	  for(int i=0;i<entitlements.length;i++) {
		  if(sessionDto.getEntitlements().indexOf(entitlements[i]) != -1) {
			  return true;
		  }
	  }
	  throw new DmarsAuthException("Unauthorized access");
  }
  
  public static String getFileName(final Part part) {
      for (String content : part.getHeader("content-disposition").split(";")) {
         if (content.trim().startsWith("filename")) {
            return content.substring(
                    content.indexOf('=') + 1).trim().replace("\"", "");
         }
      }
      return null;
  }
  
  public static String getFileExtension(String fileName) {
	  return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
  }
}
