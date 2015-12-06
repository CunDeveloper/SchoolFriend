package com.nju.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.nju.authorization.Authorization;
import com.nju.authorization.UserInfo;
import com.nju.dao.UserInfoDao;
import com.nju.dao.impl.UserInfoDaoImpl;
import com.nju.util.Constant;
import com.nju.util.SchoolFriendGson;

public class XueXinService {

	private Authorization authorization;
	public XueXinService(Authorization mauthoriztion) {
		this.authorization = mauthoriztion;
	}
	private static Logger logger = Logger.getLogger(XueXinService.class);
	
	public String login(String username,String password,String label_id) throws IOException{
		String result ="";
		String it = authorization.getIt();
		String html = authorization.postForm(it, username, password);
		Document doc = authorization.getDocument(html);
		Element captchaEle = doc.getElementById("captcha");
		SchoolFriendGson gson = SchoolFriendGson.newInstance();
		if(captchaEle !=null){
			Map<String,String> info = new HashMap<String,String>();
			info.put(Constant.XUE_XIN_CAPTCHA,html);
			return gson.toJson(info);
		} else{
			
			UserInfoDao userInfo = new UserInfoDaoImpl();
			if (doc != null && authorization.validate(html) == 0) {
				System.out.println(html);
				ArrayList<UserInfo> lists = authorization.getUserInfo(doc);
				for (UserInfo info:lists) {
					userInfo.save(info, label_id);
				}
				Map<String,List<UserInfo>> infoMap = new HashMap<String,List<UserInfo>>();
				infoMap.put(Constant.XUE_AUTH, lists);
				result = gson.toJson(infoMap);
				logger.info(result);
			}
			else{
				Map<String,String> infoMap = new HashMap<String,String>();
				infoMap.put(Constant.XUE_AUTH,Constant.USERNAME_OR_PASS_ERROR);
				result = gson.toJson(infoMap);
			}
		}
		return result;
	}
}
