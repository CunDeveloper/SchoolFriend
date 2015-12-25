package com.nju.runnable;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.model.Content;
import com.nju.model.ViewAUserContent;
import com.nju.service.UploadContentService;
import com.nju.util.Constant;
import com.nju.util.SchoolFriendGson;

public class UserContentRunnable implements Runnable {

	private AsyncContext asyncContext;
	public UserContentRunnable(AsyncContext context) {
		this.asyncContext = context;
	}
	@Override
	public void run() {
		HttpServletRequest request =(HttpServletRequest) asyncContext.getRequest();
		HttpServletResponse  response = (HttpServletResponse) asyncContext.getResponse();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String label = request.getParameter(Constant.LABLE);
			String str_user_id = request.getParameter(Constant.USER_ID);
			SchoolFriendGson gson = SchoolFriendGson.newInstance();
			int user_id = Integer.valueOf(str_user_id);
			switch(label){ 
			case Constant.DELETE:{
				delete(request,response);
				break;
			}
			case Constant.QUERY_ALL:{
				List<Content> contents = new UploadContentService().query(user_id);
				out.append(gson.toJson(contents));
				break;
			}
			case Constant.QUERY_OWN:{
				List<Content> contents = new UploadContentService().queryOwnContent(user_id);
				out.append(gson.toJson(contents));
				break;
			}
			case Constant.QUERY_ANOTHER:{
				int visit_user_id  = Integer.valueOf(request.getParameter("visit_id"));
				List<ViewAUserContent> contents = new UploadContentService().queryAnotherUserContent(visit_user_id, user_id);
				out.append(gson.toJson(contents));
				break;
			  }
		   }
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null) {
				out.close();
			}
		}
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String school = request.getParameter(Constant.SCHOOL);
		String strConId = request.getParameter("con_id");
		String strContainImage = request.getParameter("is_contain_image");
		List<String> imgNames = new ArrayList<String>();
		Enumeration<String> paramsName = request.getParameterNames();
		while(paramsName.hasMoreElements()) {
			String name = paramsName.nextElement();
			String img = request.getParameter(name);
			if (img != null) {
				imgNames.add(img);
			}
		}
		int con_id = Integer.valueOf(strConId);
		int is_contain_image = Integer.valueOf(strContainImage);
		//String path = Constant.getImgPath(request.getServletContext()) +school;
		//new UploadContentService().delete(con_id, is_contain_image,imgNames,path);
	}

}
