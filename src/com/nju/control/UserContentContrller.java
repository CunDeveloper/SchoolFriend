package com.nju.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nju.model.Content;
import com.nju.model.ViewAUserContent;
import com.nju.service.UploadContentService;
import com.nju.util.Constant;
import com.nju.util.SchoolFriendGson;
import com.nju.util.Validate;

/**
 * Servlet implementation class UserContentContrller
 */
@WebServlet("/UserContentContrller")
public class UserContentContrller extends BaseServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String label = request.getParameter("lable");
		String str_user_id = request.getParameter("user_id");
		SchoolFriendGson gson = SchoolFriendGson.newInstance();
		int user_id = Integer.valueOf(str_user_id);
		if(label.equals("delete"))
			delete(request,response);
		else if(label.equals(Constant.QUERY_ALL)){
			List<Content> contents = new UploadContentService().query(user_id);
			out.println(gson.toJson(contents));
		} else if(label.equals(Constant.QUERY_OWN)) {
			List<Content> contents = new UploadContentService().queryOwnContent(user_id);
			out.append(gson.toJson(contents));
		} else if (label.equals(Constant.QUERY_ANOTHER)){
			String str_visit_user_id = request.getParameter("visit_id");
			if(Validate.isNumber(str_visit_user_id)){
				int visit_user_id  = Integer.valueOf(str_visit_user_id);
				List<ViewAUserContent> contents = new UploadContentService().queryAnotherUserContent(visit_user_id, user_id);
				out.append(gson.toJson(contents));
			}
		}
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String school = request.getParameter(Constant.SCHOOL);
		String strConId = request.getParameter("con_id");
		String strContainImage = request.getParameter("is_contain_image");
		List<String> imgNames = new ArrayList<String>();
		String img1 = request.getParameter("img_name1");
		if(img1 != null)
			imgNames.add(img1);
		String img2 = request.getParameter("img_name2");
		if(img2 != null)
			imgNames.add(img2);
		String img3 = request.getParameter("img_name3");
		if(img3 != null)
			imgNames.add(img3);
		String img4 = request.getParameter("img_name4");
		if(img4 != null)
			imgNames.add(img4);
		String img5 = request.getParameter("img_name5");
		if(img5 != null)
			imgNames.add(img5);
		String img6 = request.getParameter("img_name6");
		if(img6 != null)
			imgNames.add(img6);
		String img7 = request.getParameter("img_name7");
		if(img7 != null)
			imgNames.add(img7);
		String img8 = request.getParameter("img_name8");
		if(img8 != null)
			imgNames.add(img8);
		String img9 = request.getParameter("img_name9");
		if(img9 != null)
			imgNames.add(img9);
		int con_id = Integer.valueOf(strConId);
		int is_contain_image = Integer.valueOf(strContainImage);
		String path = Constant.getImgPath(this) +school;
		new UploadContentService().delete(con_id, is_contain_image,imgNames,path);
	}

}
