package com.nju.dao;

import com.nju.authorization.UserInfo;

public interface UserInfoDao {
	public boolean save(UserInfo userInfo,String label_id);
	public String queryRealName(String label);
}
