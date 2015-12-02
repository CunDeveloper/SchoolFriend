package com.nju.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Authorization authorization;
    private static Logger logger = Logger.getLogger(XueXinAuthController.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XueXinAuthController() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void init(){
    	authorization = new Authorization();
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String captcha = request.getParameter("captcha");
		String label_id = request.getParameter("label_id");
		
		XueXinService service = new XueXinService(authorization);
		SchoolFriendGson gson = SchoolFriendGson.newInstance();
		if(captcha ==null || captcha.equals("")){ 
			String result = service.login(username, password,label_id);
			if(result.equals(Constant.HTTP_ERROR) || result.equals(Constant.HTTP_URL_ERROR)) {
				out.print(result);
			} else{
				loginExe(gson,result,request,response,out);
			}
			
		}
		else{
			String result = service.loginWithCaptcha(request.getSession().getAttribute(Constant.XUE_XIN_IT).toString(), username, password, captcha, label_id);
			if(result.equals(Constant.HTTP_ERROR) || result.equals(Constant.HTTP_URL_ERROR)) {
				out.print(result);
			} else{
				loginExe(gson,result,request,response,out);
			}
//			String htmlf = authorization.postFormWithCaptcha(request.getSession().getAttribute(Constant.XUE_XIN_IT).toString(), username, password,captcha);
//			System.out.println(htmlf);
//			Document doc = authorization.getXueXinDoc(htmlf);
//			if (doc != null) {
//				ArrayList<UserInfo> lists = authorization.getUserInfo2(doc);
//				for (UserInfo info : lists) {
//					System.out.println(info.getFenYuan()+"=="+info.getYuanXiaoName()
//					+"=="+info.getMajor()+"=="+info.getDate());
//				}
//			}
//			else{
//				logger.info("用户名或者密码错误");
//			}
		}
		out.flush();
		out.close();
	}
	
	private void loginExe(SchoolFriendGson gson,String result,HttpServletRequest request,HttpServletResponse response, PrintWriter out) throws IOException, ServletException {
		Map<Object, Object> resultMap =gson.fromJsonToMap(result);
	 
		if(resultMap.containsKey(Constant.XUE_XIN_CAPTCHA)) {
			String IT = authorization.getItT(resultMap.get(Constant.XUE_XIN_CAPTCHA).toString());
			 
			request.getSession().setAttribute(Constant.XUE_XIN_IT,IT);
			//byte[] captchaBytes = authorization.getCaptcha("https://account.chsi.com.cn/passport/captcha.image?id=1114.4807375967503");
			//Map<String,byte[]> infoMap = new HashMap<String,byte[]>();
			//infoMap.put(Constant.XUE_XIN_CAPTCHA,captchaBytes);
			//out.print(result);
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++"+IT);
			request.getRequestDispatcher("auth.jsp?img=https://account.chsi.com.cn/passport/captcha.image"+Constant.XUE_XIN_SERVICE+"&id="+Math.random()*1000000000).forward(request, response);
		} else {
			out.print(result);
		}
	}

}
