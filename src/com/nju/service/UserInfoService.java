package com.nju.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.nju.model.User;
import com.nju.model.UserInfo;
import com.nju.util.C3PODataSource;
import com.nju.util.MD5;

public class UserInfoService {

	public boolean save(UserInfo userInfo,String label_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean result = false;
		String sql ="insert into userInfo(name,sex,school,xueyuan,major,degree,start_date,label_id) values(?,?,?,?,?,?,?,?)";
		try {
			conn = C3PODataSource.getConn();
			stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1,userInfo.getName());
			stmt.setString(2,userInfo.getSex());
			stmt.setString(3,userInfo.getSchool());
			stmt.setString(4,userInfo.getXueyuan());
			stmt.setString(5,userInfo.getMajor());
			stmt.setString(6,userInfo.getDegree());
			stmt.setString(7,userInfo.getStartDate());
			stmt.setString(8,label_id);
			result = stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				if(stmt!=null)
					stmt.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int update(int user_id,String label_id){
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = 0;
		String sql ="UPDATE userInfo SET user_id=? where label_id=?";
		try {
			conn = C3PODataSource.getConn();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,user_id);
			stmt.setString(2,label_id);
			result =stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return result;
	}
}
