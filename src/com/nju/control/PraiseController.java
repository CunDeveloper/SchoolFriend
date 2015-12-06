package com.nju.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.nju.model.Praise;
import com.nju.service.PraiseService;
import com.nju.util.Constant;
import com.nju.util.SchoolFriendGson;
import com.nju.util.Validate;

/**
 * Servlet implementation class PraiseController
 */
@WebServlet("/PraiseController")
public class PraiseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(PraiseController.class);  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PraiseController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String strConId = request.getParameter("con_id");
		String strUserId = request.getParameter("user_id");
		String praiceUserName = request.getParameter("praiceUserName");
		SchoolFriendGson gson = SchoolFriendGson.newInstance();
		if(Validate.isNumber(strConId)&&Validate.isNumber(strUserId)){
			Praise praise = new Praise();
			int con_id = Integer.valueOf(strConId);
			int user_id = Integer.valueOf(strUserId);
			praise.setCon_id(con_id);praise.setUser_id(user_id);
			praise.setPriceUserName(praiceUserName);
			out.print(gson.toJson(new PraiseService().save(praise)));
		}
		else{
			logger.error("输入的con_id,user_id不是数字");
			out.print(gson.toJson(Constant.PARAMER_ERROR));
		}
		
	}

}
