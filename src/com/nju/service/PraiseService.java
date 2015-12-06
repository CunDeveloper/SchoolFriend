package com.nju.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.nju.model.Praise;
import com.nju.util.C3PODataSource;
import com.nju.util.Constant;

public class PraiseService {

	public int save(Praise praise) {
		Connection conn = null;
		int result = Constant.SQL_EXE_FALIURE;
		String sql ="INSERT INTO praise(con_id,user_id,praiceUserName,date) values(?,?,?,now())";
		PreparedStatement stmt = null;
		try {
			conn = C3PODataSource.getConn();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,praise.getCon_id());
			stmt.setInt(2,praise.getUser_id());
			stmt.setString(3,praise.getPriceUserName());
			stmt.execute();
			result = Constant.SQL_EXE_OK;
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
