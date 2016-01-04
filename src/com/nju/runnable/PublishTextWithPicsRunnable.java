package com.nju.runnable;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;

import com.nju.model.Content;
import com.nju.service.UploadContentService;
import com.nju.util.Constant;

public class PublishTextWithPicsRunnable extends BaseRunnable{

	private AsyncContext asyncContext;
	public PublishTextWithPicsRunnable(AsyncContext context) {
		this.asyncContext = context;
	}
	
	@Override
	protected void exeRequest() throws IOException {
		// TODO Auto-generated method stub
		
		HttpServletRequest request =(HttpServletRequest) asyncContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
		PrintWriter out = response.getWriter();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletContext servletContext = request.getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		ServletFileUpload upload = new ServletFileUpload(factory);
		String school = request.getParameter(Constant.SCHOOL);
		 try {
				List<FileItem> items = upload.parseRequest(request);
				boolean isMultipart = ServletFileUpload.isMultipartContent(request);
				Content content = new Content();
				List<String> imageList = new ArrayList<String>();
				if(isMultipart) {
					String path = Constant.getImgPath(request) +new String(school.getBytes("ISO8859_1"),"utf-8");
					//logger.info(path);
					Iterator<FileItem> iter = items.iterator();
					while (iter.hasNext()) {
					    FileItem item = iter.next();
					    if (item.isFormField()) {
					    	processFormField(item,content);
					    } else {
					    	processUploadedFile(item,path,content,imageList);
					    }
					}
				}
				content.setImageList(imageList);
			   out.append(gson.toJson(new UploadContentService().save(content)));
			 } catch (FileUploadException e) {
				// TODO Auto-generated catch block
				 out.append(gson.toJson(Constant.PUBLISH_WEIBO_ERROR));
				 e.printStackTrace();
		     }
		 out.close();
	}
	 
	public void processUploadedFile(FileItem item,String path,Content content,List<String> imageList) {
		// TODO Auto-generated method stub
		if (item.getName() != null && !item.getName().equals("")) { 
            UUID uuid = UUID.randomUUID();
            File dir = new File(path);
            logger.info(path);
            if ( !dir.exists()) {
            	dir.mkdir();
            }
            StringBuilder fileName = new StringBuilder();
            for(String str:(uuid+"").split("-")){
            	fileName.append(str);
            }
            fileName.append(".jpg");
            File file = new File(dir,fileName.toString());
            try {
				file.createNewFile();
				BufferedImage image = ImageIO.read(item.getInputStream());
				BufferedImage aimg = Scalr.resize(image,50);
				ImageIO.write(aimg,"png",new File(path+"/hehe.png"));
	            item.write(file);
	            imageList.add(fileName.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 
				e.printStackTrace();
			}finally {
				 
			}   
        } else {
            System.out.println("文件为空");
        }
	}
		
	public void processFormField(FileItem item,Content content) {
		String name = item.getFieldName();
		String value = item.getString();	 
	    try {
			value = new String(item.getString().getBytes("ISO8859_1"),"utf-8");
			if (name != null ) {
		    	if(name.equals("content")){
		    		 content.setContent(value);
		    	}else if(name.equals("user_id")) {
		    		 content.setUser_id(Integer.valueOf(value));
		    	} 
		    }
			} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
	}
	
}
