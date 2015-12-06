package com.nju.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.nju.service.FileUploadContent;
import com.nju.service.impl.FileUpload;

public class FileUploadController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public FileUploadController() {
		super();
	}
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 response.setContentType("text/html");
		 response.setCharacterEncoding("utf-8");
		 request.setCharacterEncoding("utf-8");
		 PrintWriter out = response.getWriter();
		 String label = request.getParameter("type");
		 
		 if(label== null || label.equals("")) {
			 out.print("文件上传错误");
		 }else{
			 DiskFileItemFactory factory = new DiskFileItemFactory();
			 ServletContext servletContext = this.getServletContext();
			 File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			 factory.setRepository(repository);
			 ServletFileUpload upload = new ServletFileUpload(factory);
			 // Parse the request
			 try {
				List<FileItem> items = upload.parseRequest(request);
				boolean isMultipart = ServletFileUpload.isMultipartContent(request);
				if(isMultipart) {
					 String path = getServletContext().getRealPath("/") +getServletContext().getInitParameter("image_upload_default_path");
					 if(label.equals("content")){
						 path = getServletContext().getRealPath("/") +getServletContext().getInitParameter("image_upload_path");
					 }
					FileUpload fileUpload = FileUploadContent.newInstance();
					Iterator<FileItem> iter = items.iterator();
					while (iter.hasNext()) {
					    FileItem item = iter.next();
					    if (item.isFormField()) {
					    	fileUpload.processFormField(item);
					    } else {
							fileUpload.processUploadedFile(item, path);
					    }
					}
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		out.flush();
		out.close();
	}

}
