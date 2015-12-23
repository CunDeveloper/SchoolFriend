package com.nju.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nju.model.User;
import com.nju.util.MD5;

public class UserDaoImpl extends BaseDaoImpl<User> {

	@Override
	protected void privateSave(Connection conn, PreparedStatement stmt, User user, String... params) throws SQLException {
		// TODO Auto-generated method stub
		String sql ="insert into user(username,password,icon,sign_name,real_name) values(?,?,?,?,?)";
		stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		stmt.setString(1,user.getUsername());
		String password = MD5.encripty(MD5.encripty(MD5.encripty(MD5.encripty(user.getPassword()))));
		stmt.setString(2,password);
		stmt.setString(3,user.getHeadIcon());
		stmt.setString(4, user.getSign());
		stmt.setString(5,user.getRealName());
		stmt.execute();
		
	}

	@Override
	protected User privateQuery(Connection conn, PreparedStatement stmt, ResultSet set, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		User user = null;
		String sql ="SELECT username,icon,sign_name FROM user where id =?";
		stmt = conn.prepareStatement(sql);
		int user_id = Integer.valueOf(params[0]);
		stmt.setInt(1,user_id);
		set =stmt.executeQuery();
		if (set.next()) {
			user = new User();
			user.setId(user_id);
			user.setUsername(set.getString(1));
			user.setHeadIcon(set.getString(2));
			user.setSign(set.getString(3));
		}
		return user;
	}

	@Override
	protected void privateDelete(Connection conn, PreparedStatement stmt, String... params) throws SQLException {
		 
	}

	@Override
	protected void privateUpdate(Connection conn, PreparedStatement stmt, User user, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		String sql ="UPDATE user SET password=?,icon=?,sign_name=? where id=?";
		stmt = conn.prepareStatement(sql);
		String password = MD5.encripty(MD5.encripty(MD5.encripty(MD5.encripty(user.getPassword()))));
		stmt.setString(1,password);
		stmt.setString(2,user.getHeadIcon());
		stmt.setString(3,user.getSign());
		stmt.setInt(4, user.getId());
		stmt.executeUpdate();
	}

}
