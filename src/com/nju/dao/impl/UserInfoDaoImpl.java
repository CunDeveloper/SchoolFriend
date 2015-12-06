package com.nju.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nju.authorization.UserInfo;
import com.nju.dao.UserInfoDao;
import com.nju.util.C3PODataSource;

public class UserInfoDaoImpl implements UserInfoDao {

	@Override
	public boolean save(UserInfo userInfo, String label_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean result = false;
		String sql ="insert into userInfo(name,sex,school,xueyuan,major,label,start_date,label_id) values(?,?,?,?,?,?,?,?)";
		try {
			conn = C3PODataSource.getConn();
			stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1,userInfo.getName());
			stmt.setString(2,userInfo.getSex());
			stmt.setString(3,userInfo.getYuanXiaoName());
			stmt.setString(4,userInfo.getFenYuan());
			stmt.setString(5,userInfo.getMajor());
			stmt.setString(6,userInfo.getLabel());
			stmt.setString(7,userInfo.getDate());
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
	public String queryRealName(String label) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String result = null;
		String sql ="SELECT name FROM userInfo WHERE label_id=?";
		try {
			conn = C3PODataSource.getConn();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,label);
			ResultSet set = stmt.executeQuery();
			if(set.next()) {
				result = set.getString(1);
			}
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
}
