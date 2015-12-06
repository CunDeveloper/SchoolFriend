package com.nju.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.nju.model.User;
import com.nju.service.FileUploadService;
import com.nju.service.UserService;
import com.nju.util.Validate;

/**
 * Servlet implementation class FileUploadUserUpdateController
 */
@WebServlet("/FileUploadUserUpdateController")
public class FileUploadUserUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadUserUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 PrintWriter out = response.getWriter();
		 String id = request.getParameter("user_id");
		 if(id !=null && Validate.isNumber(id) ){
			 int userId = Integer.valueOf(id);
			 try {
					User user = new User();
					user.setId(userId);
					FileUploadService fileUpload = new FileUploadService(request,response);  
					List<FileItem> items = fileUpload.getFileItem();
					boolean isMultipart = ServletFileUpload.isMultipartContent(request);
					if(isMultipart) {
						String path = getServletContext().getRealPath("/") +getServletContext().getInitParameter("user");
						Iterator<FileItem> iter = items.iterator();
						while (iter.hasNext()) {
						    FileItem item = iter.next();
						    if (item.isFormField()) {
						    	processFormField(item,user);
						    } else {
								processUploadedFile(item, path,user);
						    }
						}
					}
					 new UserService().updateUser(user);
				 } catch (FileUploadException e) {
					// TODO Auto-generated catch block
					out.print("用户注册信息失败");
					e.printStackTrace();
				}
		 }
	 out.flush();
	 out.close();
	}
	
	public void processUploadedFile(FileItem item,String path,User user) {
		// TODO Auto-generated method stub
		if (item.getName() != null && !item.getName().equals("")) { 
            UUID uuid = UUID.randomUUID();
            File tempFile = new File(item.getName()); 
            File dir = new File(path);
            if ( !dir.exists()) {
            	dir.mkdir();
            }
            String uploadFileName = tempFile.getName();
            UserService service = new UserService();
            String icon = service.queryIcon(user.getId());
            if (icon !=null && !icon.equals(uploadFileName)) {
            	System.out.println(uploadFileName+"=="+icon);
            	 File file = new File(dir,icon);
            	 file.delete();
            	 String fileName = uuid+tempFile.getName();
                 File file1 = new File(dir,fileName);
                 try {
     				file1.createNewFile();
     	            item.write(file1);
     	            user.setHeadIcon(fileName);
     	           
     			} catch (IOException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			} catch (Exception e) {
     				// TODO Auto-generated catch block
     				 
     				e.printStackTrace();
     			}finally {
     				 
     			}   
            }
        } else {
            
        }
	}
	
	public void processFormField(FileItem item,User user) {
		String name = item.getFieldName();
		String value = item.getString();	 
	    try {
			value = new String(item.getString().getBytes("ISO8859_1"),"GBK");
			if (name != null ) {
		    	if(name.equals("name")){
		    		user.setUsername(value);
		    	}else if(name.equals("sign")) {
		    		user.setSign(value);
		    	} 
		    	else if(name.equals("password")){
		    		user.setPassword(value);
		    	}
		    }
		    System.out.println(name+"==="+value);
		   
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
	   
	}

}
