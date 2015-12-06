package com.nju.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.nju.model.User;
import com.nju.util.C3PODataSource;
import com.nju.util.MD5;

public class UserService {

	/**
	 * 
	 * @param user
	 * @return 0表示用户名已被注册，1>表示注册ok,-1表示failure
	 * 
	 */
	public int save(User user) {
		 
		Connection conn = null;
		int result = -1;
		if(isRegisted(user.getUsername())){
			result = 0;
		}
		else{
			String sql ="insert into user(username,password,icon,sign_name,real_name) values(?,?,?,?,?)";
			System.out.println(sql);
			try {
				conn = C3PODataSource.getConn();
				PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				stmt.setString(1,user.getUsername());
				String password = MD5.encripty(MD5.encripty(MD5.encripty(MD5.encripty(user.getPassword()))));
				stmt.setString(2,password);
				stmt.setString(3,user.getHeadIcon());
				stmt.setString(4, user.getSign());
				stmt.setString(5,user.getRealName());
				stmt.execute();
			    ResultSet set = stmt.getGeneratedKeys();
				if (set.next()) {
					result = set.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			finally{
				try {
					if (conn!=null)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public User query(int userId) {
		Connection conn = null;
		User user = null;
		String sql ="SELECT username,icon,sign_name FROM user where id =?";
		try {
			conn = C3PODataSource.getConn();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,userId);
			ResultSet set =stmt.executeQuery();
			if (set.next()) {
				user = new User();
				user.setId(userId);
				user.setUsername(set.getString(1));
				user.setHeadIcon(set.getString(2));
				user.setSign(set.getString(3));
				set.close();
				stmt.close();
			}
			else{
				System.out.println("用户名或者密码不正确");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return user;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return 0 表示成功，-1表示
	 */
	
	public User query(String username,String password) {
		Connection conn = null;
		User user = null;
		String sql ="SELECT id,username,icon,sign_name FROM user where username= ? and password=?";
		try {
			conn = C3PODataSource.getConn();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,username);
			String epassword = MD5.encripty(MD5.encripty(MD5.encripty(MD5.encripty(password))));
			stmt.setString(2,epassword);
			ResultSet set =stmt.executeQuery();
			if (set.next()) {
				user = new User();
				user.setId(set.getInt(1));
				user.setUsername(set.getString(2));
				user.setHeadIcon(set.getString(3));
				user.setSign(set.getString(4));
				set.close();
				stmt.close();
			}
			else{
				System.out.println("用户名或者密码不正确");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return user;
	}
	
	public String queryIcon(int id) {
		Connection conn = null;
		String sql ="SELECT icon FROM user where id=?";
		String result =null;
		try {
			conn = C3PODataSource.getConn();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,id);
			ResultSet set =stmt.executeQuery();
			if (set.next()) {
				result = set.getString(1);
				set.close();
				stmt.close();
			}
			else{
				System.out.println("获取用户头像失败");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return result;
	}
	public int updateUser(User user){
		Connection conn = null;
		int result = 0;
		String sql ="UPDATE user SET password=?,icon=?,sign_name=? where id=?";
		try {
			conn = C3PODataSource.getConn();
			PreparedStatement stmt = conn.prepareStatement(sql);
			String password = MD5.encripty(MD5.encripty(MD5.encripty(MD5.encripty(user.getPassword()))));
			stmt.setString(1,password);
			stmt.setString(2,user.getHeadIcon());
			stmt.setString(3,user.getSign());
			stmt.setInt(4, user.getId());
			result =stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return result;
	}
	public boolean isRegisted(String username) {
		Connection conn = null;
		boolean result = false;
		String sql ="SELECT username FROM user where username= ?";
		try {
			conn = C3PODataSource.getConn();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,username);
			ResultSet set =stmt.executeQuery();
			if (set.next()) {
				result = true; 
			}
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return result;
	}
}
