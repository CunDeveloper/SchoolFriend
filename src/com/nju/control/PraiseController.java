package com.nju.control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nju.model.Praise;
import com.nju.service.PraiseService;
import com.nju.util.SchoolFriendGson;

/**
 * Servlet implementation class PraiseController
 */
@WebServlet("/PraiseController")
public class PraiseController extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String strConId = request.getParameter("con_id");
		String strUserId = request.getParameter("user_id");
		String praiceUserName = request.getParameter("praiceUserName");
		SchoolFriendGson gson = SchoolFriendGson.newInstance();
		Praise praise = new Praise();
		int con_id = Integer.valueOf(strConId);
		int user_id = Integer.valueOf(strUserId);
		praise.setCon_id(con_id);praise.setUser_id(user_id);
		praise.setPriceUserName(praiceUserName);
		out.print(gson.toJson(new PraiseService().save(praise)));
	}

}
