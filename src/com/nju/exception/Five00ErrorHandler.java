package com.nju.exception;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.control.BaseServlet;

 
@WebServlet("/Five00ErrorHandler")
public class Five00ErrorHandler extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("服务器内部出现严重的错误,稍后重试");
	}
}
