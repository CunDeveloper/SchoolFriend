package com.nju.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Logger logger;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseServlet() {
        super();
        logger =Logger.getLogger(this.getClass());
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 doGet(req,resp);
	}
    
    

}
