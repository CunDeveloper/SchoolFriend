package com.nju.util;

import javax.servlet.http.HttpServlet;

public class Constant {
	public static final String OK = "ok";
	public static final String ERROR = "error";
	public static final String USERNAME_OR_PASS_ERROR = "用户名或者密码错误";
	public static final String USER_ICON = "user_icon";
	public static final String SCHOOL = "school";
	public static final String UPLAOD_FIEL_FAILURE = "upload_file_failure";
	public static final int SQL_EXE_OK = 1;
	public static final int SQL_EXE_FALIURE = -1;
	public static final String LABEL_ID = "label_id";
	public static final String PARAMER_ERROR = "输入的参数错误";
	public static final String PUBLISH_WEIBO_ERROR = "发布校友圈失败";
	public static final String QUERY_ALL = "query_all";
	public static final String QUERY_OWN = "query_own";
	public static final String QUERY_ANOTHER = "query_another";
	public static final String XUE_AUTH ="auth";
	public static final String XUE_XIN_CAPTCHA ="captcha";
	public static final String XUE_XIN_IT = "xue_xin_it";
	
	public static final String getImgPath(HttpServlet servelt){
		return servelt.getServletContext().getRealPath("/");
	}

}
