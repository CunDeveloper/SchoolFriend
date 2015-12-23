package com.nju.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Test
 */
@WebServlet(urlPatterns={"/Test"},asyncSupported=true)
public class Test extends BaseServlet {
	 
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
		final AsyncContext aCtx =request.startAsync(request, response);
		Executor executor = Executors.newCachedThreadPool();
		executor.execute(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpServletResponse response = (HttpServletResponse) aCtx.getResponse();
				try {
					PrintWriter out = response.getWriter();
					out.append("hello");
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			
		});
		
	}
}
