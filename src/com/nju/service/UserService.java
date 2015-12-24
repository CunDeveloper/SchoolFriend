package com.nju.service;

import com.nju.dao.impl.BaseDaoImpl;
import com.nju.dao.impl.UserDaoImpl;
import com.nju.model.User;
import com.nju.util.Constant;

public class UserService {
	private BaseDaoImpl<User> baseDao;
	
	public UserService() {
		baseDao = new UserDaoImpl();
	}
	
	public int save(User user) {
		return baseDao.save(user);
	}
	
	public User query(int userId) {
		 return baseDao.query(Constant.QUERY_USER_BY_ID,userId+"");
	}
	 
	public User query(String username,String password) {
		 return baseDao.query(Constant.QUERY_USER_BY_NAME_PASS,username,password);
	}
	
	public String queryIcon(int id) {
		 User user = baseDao.query(Constant.QUERY_USER_BY_ID,id+"");
		 return user.getHeadIcon();
	}
	public int updateUser(User user){
		 return baseDao.update(user);
	}
	public boolean isRegisted(String username) {
		User user = baseDao.query(Constant.QUERY_USER_BY_NAME,username);
		return user!=null?true:false;
	}
}
