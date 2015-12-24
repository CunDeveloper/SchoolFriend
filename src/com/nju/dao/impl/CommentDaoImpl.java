package com.nju.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.nju.model.Comment;
public class CommentDaoImpl extends BaseDaoImpl<Comment> {

	@Override
	protected void privateSave(Connection conn, PreparedStatement stmt, Comment comment, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		String sql ="insert into comment(content,con_id,user_id,re_user_id,date) values(?,?,?,?,now())";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1,comment.getComment_content());
		stmt.setInt(2,comment.getCon_id());
		stmt.setInt(3,comment.getUser_id());
	    stmt.setInt(4,comment.getRe_user_id());
		stmt.execute();
		
	}

	@Override
	protected Comment privateQuery(Connection conn, PreparedStatement stmt, ResultSet set, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	 
	@Override
	protected void privateUpdate(Connection conn, PreparedStatement stmt, Comment t, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void privateDelete(Connection conn, PreparedStatement stmt, Comment t, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}
 
}
 
