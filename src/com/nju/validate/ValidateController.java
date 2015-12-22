package com.nju.validate;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.servlet.ServletException;
import com.nju.control.BaseServlet;
import com.nju.util.SchoolFriendGson;

public class ValidateController extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static UserContentRules userContentRules;
	private static final String USER_CONTENT_RELES_PATH = "UserContentValidate.json";
	
	@Override
	public void init() throws ServletException {
		super.init();
		SchoolFriendGson gson = SchoolFriendGson.newInstance();
		this.getClass().getResource("UserContentValidate.json");
		InputStream inputStream = this.getClass().getResourceAsStream(USER_CONTENT_RELES_PATH);
		Reader reader = new InputStreamReader(inputStream);
		userContentRules = gson.fromJsonToObject(reader,UserContentRules.class);
	}
	
	public static UserContentRules getUserContentRules() {
		return userContentRules;
	}
}
