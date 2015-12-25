package com.nju.runnable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nju.authorization.Authorization;
import com.nju.service.XueXinService;
import com.nju.util.Constant;
import com.nju.util.SchoolFriendGson;

public class XueXinAuthRunnable implements Runnable {
	private static final String UTF_8 = "utf-8";
	private AsyncContext asyncContext;
	public XueXinAuthRunnable(AsyncContext context) {
		this.asyncContext = context;
	}
	@Override
	public void run() {
		HttpServletRequest request =(HttpServletRequest) asyncContext.getRequest();
		HttpServletResponse  response = (HttpServletResponse) asyncContext.getResponse();
		try {
			Authorization authorization = null;
			OutputStream out = response.getOutputStream();
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
					out.write(result.getBytes(UTF_8));
				} else{
					loginExe(authorization,gson,result,request,response,out);
				}
				
			}
			else{
	 			String result = service.loginWithCaptcha(request.getSession().getAttribute(Constant.XUE_XIN_IT).toString(), username, password, captcha, label_id);
				if(result.equals(Constant.HTTP_ERROR) || result.equals(Constant.HTTP_URL_ERROR)) {
					//out.print(result);
					out.write(result.getBytes(UTF_8));
				} else{
					loginExe(authorization,gson,result,request,response,out);
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loginExe(Authorization authorization,SchoolFriendGson gson,String result,HttpServletRequest request,HttpServletResponse response, OutputStream out) throws IOException {
		Map<Object, Object> resultMap =gson.fromJsonToMap(result);
	 
		if(resultMap.containsKey(Constant.XUE_XIN_CAPTCHA)) {
			String IT = authorization.getItT(resultMap.get(Constant.XUE_XIN_CAPTCHA).toString());
			request.getSession().setAttribute(Constant.XUE_XIN_IT,IT);
			 
			byte[] buffer =authorization.getCaptcha();
			int length = buffer.length;
			byte[] finBuffer = new byte[length+1];
			System.arraycopy(buffer, 0, finBuffer, 0, length);
			finBuffer[length]='#';
			out.write(finBuffer);
		} else {
			out.write(result.getBytes(UTF_8));
		}
	}


}
