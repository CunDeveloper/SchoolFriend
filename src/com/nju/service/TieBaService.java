package com.nju.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.nju.model.TieBaSchoolInfo;
import com.nju.util.C3PODataSource;
import com.nju.util.Constant;

public class TieBaService {

	public int save(List<TieBaSchoolInfo> schoolInfos) {
		int result = Constant.SQL_EXE_FALIURE;
		Connection conn = null;
		String sql ="insert into schoolUrls(firstUrl,name) values(?,?)";
		try {
			conn = C3PODataSource.getConn();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(sql);
			for(TieBaSchoolInfo schoolInfo:schoolInfos){
				stmt.setString(1,schoolInfo.getFirstUrl());
				stmt.setString(2,schoolInfo.getName());
				stmt.addBatch();
			}
			stmt.executeBatch();
			conn.setAutoCommit(true); 
			result = Constant.SQL_EXE_OK;
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
		return result; 
	}
	
	public int queryId(String name) {
		int result = -1;  
		Connection conn = null;
		String sql ="select id from schoolUrls where name=?";
		try {
			conn = C3PODataSource.getConn();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,name);
			ResultSet set =stmt.executeQuery();
			if(set.next()){
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
		return result; 
	}
	
	public int saveAllUrls(List<String> urlInfos,int id) {
		int result = Constant.SQL_EXE_FALIURE;
		Connection conn = null;
		String sql ="insert into schoolsUrl(url,school_id) values(?,?)";
		try {
			conn = C3PODataSource.getConn();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(sql);
			for(String str:urlInfos){
				stmt.setString(1,str);
				stmt.setInt(2,id);
				stmt.addBatch();
			}
			stmt.executeBatch();
			conn.setAutoCommit(true); 
			result = Constant.SQL_EXE_OK;
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
		return result; 
	}
}
