package com.nju.runnable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.AsyncContext;

public abstract class BaseExecutor implements Runnable {
	 protected static BlockingQueue<AsyncContext> asyncQueue;
	 protected static Executor executor;
	 
	 public BaseExecutor() {
		 if(asyncQueue == null) {
			 executor = Executors.newFixedThreadPool(5);
			 asyncQueue = new LinkedBlockingQueue<AsyncContext>();
		 }
	 }
	 
	 public static void addAsyncRequest(AsyncContext context) {
		 asyncQueue.add(context);
	 }
}
