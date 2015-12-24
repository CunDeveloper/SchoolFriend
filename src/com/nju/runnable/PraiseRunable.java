package com.nju.runnable;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.model.Praise;
import com.nju.service.PraiseService;
import com.nju.util.SchoolFriendGson;

public class PraiseRunable implements Runnable {

	private AsyncContext asyncContext;
	public PraiseRunable(AsyncContext context) {
		this.asyncContext = context;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpServletRequest request =(HttpServletRequest) asyncContext.getRequest();
		HttpServletResponse  response = (HttpServletResponse) asyncContext.getResponse();
		try {
			PrintWriter out = response.getWriter();
			String strConId = request.getParameter("con_id");
			String strUserId = request.getParameter("user_id");
			String praiceUserName = request.getParameter("praiceUserName");
			SchoolFriendGson gson = SchoolFriendGson.newInstance();
			Praise praise = new Praise();
			int con_id = Integer.valueOf(strConId);
			int user_id = Integer.valueOf(strUserId);
			praise.setCon_id(con_id);praise.setUser_id(user_id);
			praise.setPriceUserName("test");
			out.append(gson.toJson(new PraiseService().save(praise)));
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
