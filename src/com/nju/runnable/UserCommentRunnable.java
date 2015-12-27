package com.nju.runnable;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.model.Comment;
import com.nju.service.CommentService;

public class UserCommentRunnable extends BaseRunnable {

	private AsyncContext asyncContext;
	
	public UserCommentRunnable(AsyncContext context) {
		this.asyncContext = context;
	}

	@Override
	protected void exeRequest() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request =(HttpServletRequest) asyncContext.getRequest();
		HttpServletResponse  response = (HttpServletResponse) asyncContext.getResponse();
		PrintWriter out = response.getWriter();
		String content = request.getParameter("content");
		String str_con_id = request.getParameter("con_id");
		String str_user_id = request.getParameter("user_id");
		String str_re_user_id = request.getParameter("re_user_id");
		int con_id = Integer.valueOf(str_con_id);
		int user_id = Integer.valueOf(str_user_id);
		int re_user_id = Integer.valueOf(str_re_user_id);
		Comment comment = new Comment();
		comment.setComment_content(content);comment.setCon_id(con_id);
		comment.setUser_id(user_id);
		comment.setRe_user_id(re_user_id);
		out.append(gson.toJson(new CommentService().save(comment)));
		out.close();
	}

}
