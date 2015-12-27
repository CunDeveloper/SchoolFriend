package com.nju.runnable;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.model.User;
import com.nju.service.UserService;

public class UserRunnable extends BaseRunnable{

	private AsyncContext asyncContext;
	public UserRunnable(AsyncContext context) {
		this.asyncContext = context;
	}
	
	@Override
	protected void exeRequest() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request =(HttpServletRequest) asyncContext.getRequest();
		HttpServletResponse  response = (HttpServletResponse) asyncContext.getResponse();
		PrintWriter out = response.getWriter();
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		//String diviceID = request.getParameter("divice_id");
		User user = new UserService().query(userName, password);
		if (user!=null) {
			request.getSession().setAttribute("USER_ID",user.getId());
			out.append("登录成功");
		}else{
			out.append("登录失败");
		}
		out.close();
	}

}
