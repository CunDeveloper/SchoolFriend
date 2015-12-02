package com.nju.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.model.Content;
import com.nju.service.UploadContentService;
import com.nju.util.Constant;

/**
 * Servlet implementation class PublishTextController
 */
@WebServlet("/PublishTextController")
public class PublishTextController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublishTextController() {
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
		String user_id = request.getParameter(Constant.USER_ID);
		String text = request.getParameter(Constant.PUBLISH_TEXT);
		String location = request.getParameter(Constant.USER_LOCATION);
		Content content = new Content();
		content.setUser_id(Integer.valueOf(user_id));
		content.setContent(text);
		content.setUserLocation(location);
		new UploadContentService().save(content);
		
	}

}
