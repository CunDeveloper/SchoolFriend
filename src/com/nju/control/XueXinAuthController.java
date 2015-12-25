package com.nju.control;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.runnable.XueXinAuthRunnable;

/**
 * Servlet implementation class XueXinAuthController
 */
@WebServlet(urlPatterns={"/XueXinAuthController"},asyncSupported=true)
public class XueXinAuthController extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AsyncContext context = request.startAsync(request, response);
		addToQueue(new XueXinAuthRunnable(context));
	}
}
