package com.nju.control;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.runnable.UserRunnable;

/**
 * Servlet implementation class UserController
 */
@WebServlet(urlPatterns={"/UserController"},asyncSupported=true)
public class UserController extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException   {
		AsyncContext context = request.startAsync(request, response);
		addToQueue(new UserRunnable(context));
	}

}
