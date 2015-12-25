package com.nju.runnable;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.model.Praise;
import com.nju.service.PraiseService;
import com.nju.util.Constant;

public class PraiseRunable extends BaseRunnable {

	private AsyncContext asyncContext;
	public PraiseRunable(AsyncContext context) {
		super();
		this.asyncContext = context;
	}
	
	@Override
	protected void exeRequest(PrintWriter out) throws IOException {
		HttpServletRequest request =(HttpServletRequest) asyncContext.getRequest();
		HttpServletResponse  response = (HttpServletResponse) asyncContext.getResponse();
		out = response.getWriter();
		String strConId = request.getParameter("con_id");
		String strUserId = request.getParameter(Constant.USER_ID);
		String praiceUserName = request.getParameter("praiceUserName");
		Praise praise = new Praise();
		int con_id = Integer.valueOf(strConId);
		int user_id = Integer.valueOf(strUserId);
		praise.setCon_id(con_id);praise.setUser_id(user_id);
		praise.setPriceUserName(praiceUserName);
		out.append(gson.toJson(new PraiseService().save(praise)));
	}

}
