package com.nju.control;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected Logger logger;
	protected BlockingQueue<Runnable> reqQueue;
	protected Executor executor;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseServlet() {
        super();
        logger =Logger.getLogger(this.getClass());
        reqQueue = new LinkedBlockingQueue<Runnable>();
        executor = Executors.newFixedThreadPool(20);
    }

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	public void addToQueue(Runnable runnable) {
		reqQueue.add(runnable);
		if(reqQueue.size()==1) {
			 exeRequest();
		}
	}
	 
	private void exeRequest() {
		while(!reqQueue.isEmpty()) {
			Runnable command = reqQueue.poll();
			if(command != null) {
				executor.execute(command);
			}
		}
	}
   
}
