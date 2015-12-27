package com.nju.runnable;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.nju.authorization.UserInfo;
import com.nju.dao.impl.BaseDaoImpl;
import com.nju.dao.impl.UserInfoDaoImpl;
import com.nju.model.User;
import com.nju.service.FileUploadService;
import com.nju.service.UserService;
import com.nju.util.Constant;

public class UserIconUploadRunnable extends BaseRunnable{
	private AsyncContext asyncContext;
	
	public UserIconUploadRunnable(AsyncContext context) {
		super();
		this.asyncContext = context;
	}
	
	@Override
	protected void exeRequest() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request =(HttpServletRequest) asyncContext.getRequest();
		HttpServletResponse  response = (HttpServletResponse) asyncContext.getResponse();
		PrintWriter out = response.getWriter();
		String school = request.getParameter(Constant.SCHOOL);
		String label_id = request.getParameter(Constant.LABEL_ID);
		User user = new User();
		FileUploadService fileUpload = new FileUploadService(request);  
		List<FileItem> items = null;
		try {
			items = fileUpload.getFileItem();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart) {
				String path = Constant.getImgPath(request) + school + "/" + Constant.USER_ICON;
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
			out.append(gson.toJson(new UserService().save(user)));
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
