package com.nju.exception;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.control.BaseServlet;

@WebServlet("/ServletExceptionHandler")
public class ServletExceptionHandler extends BaseServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("服务器出错,稍后再试");
		 
	}
}
