package com.nju.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nju.util.C3PODataSource;
import com.nju.util.Constant;

public abstract class BaseDaoImpl<T> {

	protected abstract void privateSave(Connection conn,PreparedStatement stmt,T t,String...params) throws SQLException;
	protected abstract T privateQuery(Connection conn,PreparedStatement stmt,ResultSet set,String...params) throws SQLException;
	protected abstract void privateDelete(Connection conn,PreparedStatement stmt,String...params) throws SQLException;
	protected abstract void privateUpdate(Connection conn,PreparedStatement stmt,T t,String...params) throws SQLException;
	
	public int save(T t,String...params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = Constant.SQL_EXE_FALIURE;
		try {
			conn = C3PODataSource.getConn();
			privateSave(conn,stmt,t);
			result = Constant.SQL_EXE_OK;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				if (stmt != null)
					stmt.close();
				if (conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return result;
	 }
	
	public int delete(String...params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = Constant.SQL_EXE_FALIURE;
		try {
			conn = C3PODataSource.getConn();
			privateDelete(conn,stmt,params);
			result = Constant.SQL_EXE_OK;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				if (stmt != null)
					stmt.close();
				if (conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return result;
	 }
	
	public int update(T t,String...params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = Constant.SQL_EXE_FALIURE;
		try {
			conn = C3PODataSource.getConn();
			privateUpdate(conn,stmt,t,params);
			result = Constant.SQL_EXE_OK;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				if (stmt != null)
					stmt.close();
				if (conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return result;
	 }
	
	public T query(String...params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		T t = null;
		try {
			conn = C3PODataSource.getConn();
			t = privateQuery(conn,stmt,resultSet,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				if(resultSet != null)
					resultSet.close();
				if (stmt != null)
					stmt.close();
				if (conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return t;
	 }
}
