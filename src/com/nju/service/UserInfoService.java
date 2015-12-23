package com.nju.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.nju.authorization.UserInfo;
import com.nju.dao.impl.BaseDaoImpl;
 
import com.nju.util.C3PODataSource;
 

public class UserInfoService {

	private BaseDaoImpl<UserInfo> daoImpl;
	public UserInfoService(BaseDaoImpl<UserInfo> baseDaoImpl){
		this.daoImpl = baseDaoImpl;
	}
	
	public int save(UserInfo userInfo,String label_id) {
		 return daoImpl.save(userInfo, label_id);
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
