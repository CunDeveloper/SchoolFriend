package com.nju.runnable;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.nju.util.SchoolFriendGson;

public abstract class BaseRunnable  implements Runnable{
	protected Logger logger;
	protected SchoolFriendGson gson;
	protected abstract void exeRequest(PrintWriter out) throws IOException;
	
	public BaseRunnable() {
		super();
		logger = Logger.getLogger(this.getClass());
		gson = SchoolFriendGson.newInstance();
	}

	@Override
	public void run() {
		PrintWriter out = null;
		 try {
			exeRequest(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}
}
