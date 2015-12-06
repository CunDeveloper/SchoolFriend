package com.nju.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.model.Comment;
import com.nju.service.CommentService;
import com.nju.util.SchoolFriendGson;

/**
 * Servlet implementation class UserCommentController
 */
@WebServlet("/UserCommentController")
public class UserCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserCommentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		out.print(SchoolFriendGson.newInstance().toJson(new CommentService().save(comment)));
	}

}
