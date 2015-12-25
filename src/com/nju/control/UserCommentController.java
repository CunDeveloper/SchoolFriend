package com.nju.control;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.runnable.UserCommentRunnable;

/**
 * Servlet implementation class UserCommentController
 */
@WebServlet(urlPatterns={"/UserCommentController"},asyncSupported=true)
public class UserCommentController extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AsyncContext context = request.startAsync(request, response);
		addToQueue(new UserCommentRunnable(context));
	}

}
