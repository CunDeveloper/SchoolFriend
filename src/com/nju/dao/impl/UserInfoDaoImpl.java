package com.nju.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nju.authorization.UserInfo;
 


public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> {
	
	@Override
	protected void privateSave(Connection conn, PreparedStatement stmt, UserInfo userInfo, String... params) throws SQLException {
		// TODO Auto-generated method stub
		String sql ="insert into userInfo(name,sex,school,xueyuan,major,label,start_date,label_id) values(?,?,?,?,?,?,?,?)";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1,userInfo.getName());
		stmt.setString(2,userInfo.getSex());
		stmt.setString(3,userInfo.getYuanXiaoName());
		stmt.setString(4,userInfo.getFenYuan());
		stmt.setString(5,userInfo.getMajor());
		stmt.setString(6,userInfo.getLabel());
		stmt.setString(7,userInfo.getDate());
		stmt.setString(8,params[0]);
		stmt.execute();
	}
 
	@Override
	protected UserInfo privateQuery(Connection conn, PreparedStatement stmt, ResultSet set, String... params)
			throws SQLException {
		UserInfo userInfo = null;
		String sql ="SELECT name FROM userInfo WHERE label_id=?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1,params[0]);
	    set = stmt.executeQuery();
		if(set.next()) {
			userInfo = new UserInfo();
			userInfo.setName(set.getString(1));
		}
		return userInfo;
	}

	 

	@Override
	protected void privateUpdate(Connection conn, PreparedStatement stmt, UserInfo t, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void privateDelete(Connection conn, PreparedStatement stmt, UserInfo t, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
