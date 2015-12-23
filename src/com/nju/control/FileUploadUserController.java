package com.nju.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.nju.authorization.UserInfo;
import com.nju.dao.UserInfoDao;
import com.nju.dao.impl.BaseDaoImpl;
import com.nju.dao.impl.UserInfoDaoImpl;
import com.nju.model.User;
import com.nju.service.FileUploadContent;
import com.nju.service.FileUploadService;
import com.nju.service.UserService;
import com.nju.service.impl.FileUpload;
import com.nju.util.Constant;
import com.nju.util.SchoolFriendGson;
@WebServlet("/FileUploadUserController")
public class FileUploadUserController extends BaseServlet {

	private static final long serialVersionUID = 1L;
 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 String school = request.getParameter(Constant.SCHOOL);
		 String label_id = request.getParameter(Constant.LABEL_ID);
		 PrintWriter out = response.getWriter();
		 SchoolFriendGson gson = SchoolFriendGson.newInstance();
		 try {
			User user = new User();
			FileUploadService fileUpload = new FileUploadService(request);  
			List<FileItem> items = fileUpload.getFileItem();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart) {
				String path = Constant.getImgPath(this) + school + "/" + Constant.USER_ICON;
				logger.info("userHeadIcon path"+path);
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					 
				    FileItem item = iter.next();
				    if (item.isFormField()) {
				    	processFormField(item,user);
				    	logger.info("处理用户注册信息的表单字段");
				    } else {
						processUploadedFile(item, path,user);
						logger.info("处理用户上传的头像");
				    }
				}
			}
			BaseDaoImpl<UserInfo> infoDao = new UserInfoDaoImpl();
			UserInfo userInfo = infoDao.query(label_id);
			user.setRealName(userInfo.getName());
			out.println(gson.toJson(new UserService().save(user)));
		 } catch (FileUploadException e) {
			// TODO Auto-generated catch block
			out.print(Constant.UPLAOD_FIEL_FAILURE);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	 out.flush();
	 out.close();
	}

	public void processUploadedFile(FileItem item,String path,User user) {
		// TODO Auto-generated method stub
		if (item.getName() != null && !item.getName().equals("")) { 
            UUID uuid = UUID.randomUUID();
            File dir = new File(path);
            if ( !dir.exists()) {
            	dir.mkdirs();
            }
            StringBuilder fileName = new StringBuilder();
            for(String str:(uuid+"").split("-")){
            	fileName.append(str);
            }
            fileName.append(".jpg");
            File file = new File(dir,fileName.toString());
            try {
				file.createNewFile();
	            item.write(file);
	            user.setHeadIcon(fileName.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				 
			}   
        } else {
            
        }
	}
	
	public void processFormField(FileItem item,User user) {
		String name = item.getFieldName();
		String value = item.getString();	 
	    value = item.getString();
 			//value = item.getString();
			try {
				value = new String(item.getString().getBytes("ISO8859_1"),"utf-8");
				if (name != null ) {
					if(name.equals("name")){
						user.setUsername(value);
					}else if(name.equals("sign")) {
						user.setSign(value);
						logger.info(value);
					}else if(name.equals("password")) {
						user.setPassword(value);
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
