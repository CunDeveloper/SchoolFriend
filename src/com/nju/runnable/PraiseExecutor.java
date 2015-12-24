package com.nju.runnable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class PraiseExecutor {

	private static Executor executor;
	private static PraiseExecutor instance;
	private static BlockingQueue<Runnable> queue;
	private PraiseExecutor() {
		if(executor == null){
			executor = Executors.newFixedThreadPool(10);
			queue = new LinkedBlockingQueue<Runnable>();
		}
	}
	
	public static PraiseExecutor newInstance() {
		if(instance == null) {
			instance = new PraiseExecutor();
		}
		return instance;
	}
	
	public void addToQueue(Runnable runnable) {
		queue.add(runnable);
		if(queue.size()==1) {
			 exeRequest();
		}
	}
	 
	public void exeRequest() {
		while(!queue.isEmpty()) {
			Runnable command = queue.poll();
			if(command != null) {
				executor.execute(command);
			}
		}
	}
}
