package com.nju.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.nju.authorization.Authorization;
import com.nju.authorization.UserInfo;
import com.nju.service.XueXinService;
import com.nju.util.Constant;
import com.nju.util.SchoolFriendGson;

/**
 * Servlet implementation class XueXinAuthController
 */
@WebServlet("/XueXinAuthController")
public class XueXinAuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
    private static Logger logger = Logger.getLogger(XueXinAuthController.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XueXinAuthController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Authorization authorization = null;
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String captcha = request.getParameter("captcha");
		String label_id = request.getParameter("label_id");
		
		if((authorization=(Authorization) request.getSession().getAttribute(Constant.AUTH_OBJECT)) == null) {
			 authorization = new Authorization();
			 request.getSession().setAttribute(Constant.AUTH_OBJECT,authorization);
		}
		
		XueXinService service = new XueXinService(authorization);
		SchoolFriendGson gson = SchoolFriendGson.newInstance();
		if(captcha ==null || captcha.equals("")){ 
			String result = service.login(username, password,label_id);
			if(result.equals(Constant.HTTP_ERROR) || result.equals(Constant.HTTP_URL_ERROR)) {
				out.print(result);
			} else{
				loginExe(authorization,gson,result,request,response,out);
			}
			
		}
		else{
			String result = service.loginWithCaptcha(request.getSession().getAttribute(Constant.XUE_XIN_IT).toString(), username, password, captcha, label_id);
			if(result.equals(Constant.HTTP_ERROR) || result.equals(Constant.HTTP_URL_ERROR)) {
				out.print(result);
			} else{
				loginExe(authorization,gson,result,request,response,out);
			}
		}
		out.flush();
		out.close();
	}
	
	private void loginExe(Authorization authorization,SchoolFriendGson gson,String result,HttpServletRequest request,HttpServletResponse response, PrintWriter out) throws IOException, ServletException {
		Map<Object, Object> resultMap =gson.fromJsonToMap(result);
	 
		if(resultMap.containsKey(Constant.XUE_XIN_CAPTCHA)) {
			String IT = authorization.getItT(resultMap.get(Constant.XUE_XIN_CAPTCHA).toString());
			request.getSession().setAttribute(Constant.XUE_XIN_IT,IT);
			byte[] buffer =authorization.getCaptcha();
			System.out.println("buffer=="+buffer.length);
			String fileName = Constant.getImgPath(this)+UUID.randomUUID()+".jpg";
			System.out.println(fileName);
			FileOutputStream outFile = new FileOutputStream(new File(fileName));
			outFile.write(buffer);
			outFile.close();
			request.getRequestDispatcher("auth.jsp?img="+fileName).forward(request, response);
		} else {
			out.print(result);
		}
	}

}
