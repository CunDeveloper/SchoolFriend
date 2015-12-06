package com.nju.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nju.dao.CommentDao;
import com.nju.model.Comment;
import com.nju.util.C3PODataSource;
import com.nju.util.Constant;
import com.nju.util.MD5;

public class CommentDaoImpl implements CommentDao {

	@Override
	public int save(Comment comment) {
		// TODO Auto-generated method stub
		Connection conn = null;
		int result = Constant.SQL_EXE_FALIURE;
		String sql ="insert into comment(content,con_id,user_id,re_user_id,date) values(?,?,?,?,now())";
		System.out.println(sql);
		try {
			conn = C3PODataSource.getConn();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,comment.getComment_content());
			stmt.setInt(2,comment.getCon_id());
			stmt.setInt(3,comment.getUser_id());
			stmt.setInt(4,comment.getRe_user_id());
			stmt.execute();
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
