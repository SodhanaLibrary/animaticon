package com.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.json.JsonGenerator;
import com.util.GlobalConstants;
import com.util.S3Util;
import com.util.Utils;

/**
 * Servlet implementation class UploadService
 */

@MultipartConfig
@WebServlet("/service/UploadService")
public class UploadService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    final Part filePart = request.getPart(GlobalConstants.PARAM_FILE);
	    InputStream filecontent = null;	  
	    PrintWriter pw = response.getWriter();
	    try {
	        filecontent = filePart.getInputStream();
	        String type = request.getParameter(GlobalConstants.PARAM_TYPE);
	        if(request.getParameter(GlobalConstants.PARAM_TYPE) == null) {
	        	type = GlobalConstants.TYPE_ICON;
	        }
	        if(type.equals(GlobalConstants.TYPE_ICON)) {
	        	S3Util.uploadImage(filecontent, "icons/"+request.getParameter("fileName"));
	        } else if(type.equals(GlobalConstants.TYPE_BANNER)) {
	        	S3Util.uploadImage(filecontent, "banners/"+request.getParameter("fileName"));
	        }
	        pw.write(JsonGenerator.generateSuccessJson("Uploaded successfully"));
	    } catch (Exception fne) {
	    	pw.write(JsonGenerator.generateErrorJson(fne));
	    } finally {
	        if (filecontent != null) {
	            filecontent.close();
	        }
	    }
	    
	}

}
