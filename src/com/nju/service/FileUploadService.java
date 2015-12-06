package com.nju.service;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadService {
	private HttpServletRequest request;
	private HttpServletResponse response;
	public FileUploadService(HttpServletRequest request,HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	public List<FileItem> getFileItem() throws FileUploadException {
		 DiskFileItemFactory factory = new DiskFileItemFactory();
		 ServletContext servletContext = request.getServletContext();
		 File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		 factory.setRepository(repository);
		 ServletFileUpload upload = new ServletFileUpload(factory);
		 upload.setHeaderEncoding("ISO8859_1");
		 List<FileItem> items = upload.parseRequest(request);
		 return items;
	}
}
