package com.nju.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nju.model.Praise;

public class PraiseDaoImpl extends BaseDaoImpl<Praise> {

	@Override
	protected void privateSave(Connection conn, PreparedStatement stmt, Praise praise, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		String sql ="INSERT INTO praise(con_id,user_id,praiceUserName,date) values(?,?,?,now())";
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1,praise.getCon_id());
		stmt.setInt(2,praise.getUser_id());
		stmt.setString(3,praise.getPriceUserName());
		stmt.execute();
		
	}

	@Override
	protected Praise privateQuery(Connection conn, PreparedStatement stmt, ResultSet set, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected  void privateDelete(Connection conn, PreparedStatement stmt, String... params) throws SQLException {
	 
	}

	@Override
	protected void privateUpdate(Connection conn, PreparedStatement stmt, Praise t, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}
	

}
