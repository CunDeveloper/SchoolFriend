package com.nju.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.model.User;
import com.nju.service.UserService;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String diviceID = request.getParameter("divice_id");
		 User user = new UserService().query(userName, password);
		 if (user!=null) {
			 request.getSession().setAttribute("USER_ID",user.getId());
			 out.print("登录成功");
		 }else{
			 out.print("登录失败");
		 }
	}

}
