package com.nju.dao.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nju.model.Content;

public class UploadContentDaoImpl extends BaseDaoImpl<Content> {

	@Override
	protected void privateSave(Connection conn, PreparedStatement stmt, Content content, String... params)
			throws SQLException {
		String sql ="insert into content(cont,user_id,pic_urls,date,user_location) values(?,?,?,now(),?)";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1,content.getContent());
		stmt.setInt(2,content.getUser_id());
		stmt.setString(3,content.listToString());
		System.out.println(content.listToString()+"++++++++++++++++++++++++++");
		stmt.setString(4,content.getUserLocation());
	    stmt.executeUpdate();
	}

	@Override
	protected Content privateQuery(Connection conn, PreparedStatement stmt, ResultSet set, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	 

	@Override
	protected void privateUpdate(Connection conn, PreparedStatement stmt, Content t, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void privateDelete(Connection conn, PreparedStatement stmt, Content content, String... params) {
			try {
				conn.setAutoCommit(false);
				String sql ="DELETE FROM content where id =?";
				stmt = conn.prepareStatement(sql);
				int con_id = content.getId();
				stmt.setInt(1,con_id);
				stmt.addBatch();
				stmt.addBatch("DELETE FROM comment WHERE con_id ="+con_id);
				stmt.addBatch("DELETE FROM praise WHERE con_id ="+con_id);
				stmt.executeBatch();
				conn.commit();
				conn.setAutoCommit(true);
				File file = null;
				for (String str:content.getImageList()) {
				    file = new File(params[0],str);
				    file.delete();
			    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				if(conn != null) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				e.printStackTrace();
			}
	}

}
