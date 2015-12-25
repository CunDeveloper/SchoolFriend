package com.nju.runnable;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.model.Content;
import com.nju.service.UploadContentService;
import com.nju.util.Constant;

public class PublishTextRunnable implements Runnable {

	private AsyncContext asyncContext;
	public PublishTextRunnable(AsyncContext context) {
		this.asyncContext = context;
	}
	@Override
	public void run() {
		HttpServletRequest request =(HttpServletRequest) asyncContext.getRequest();
		HttpServletResponse  response = (HttpServletResponse) asyncContext.getResponse();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String user_id = request.getParameter(Constant.USER_ID);
			String text = request.getParameter(Constant.PUBLISH_TEXT);
			String location = request.getParameter(Constant.USER_LOCATION);
			Content content = new Content();
			content.setUser_id(Integer.valueOf(user_id));
			content.setContent(text);
			content.setUserLocation(location);
			int result =new UploadContentService().save(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(out!=null) {
				out.close();
			}
		}
	}

}
