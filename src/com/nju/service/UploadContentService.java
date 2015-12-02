package com.nju.service;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nju.model.Comment;
import com.nju.model.Content;
import com.nju.model.Praise;
import com.nju.model.ViewAUserContent;
import com.nju.util.C3PODataSource;
import com.nju.util.Constant;

public class UploadContentService {
	/**
	 * 
	 * @param user
	 * @return 0表示用户名已被注册，1表示注册ok,-1表示failure
	 * 
	 */
	public int save(Content content) {
		 
		Connection conn = null;
		int result = Constant.SQL_EXE_FALIURE;
		String sql ="insert into content(cont,user_id,is_contain_image,date,user_location) values(?,?,?,now(),?)";
		PreparedStatement stmt = null;
		try {
			conn = C3PODataSource.getConn();
			stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1,content.getContent());
			stmt.setInt(2,content.getUser_id());
			stmt.setInt(3,content.getImageList().size());
			stmt.setString(4,content.getUserLocation());
		    stmt.executeUpdate();
		    ResultSet set = stmt.getGeneratedKeys();
		    if(set.next()) {
		    	PreparedStatement imageStmt = null;
		    	int con_id = set.getInt(1);
		    	try{
			    	String imgSql = "insert into images(url,con_id) values(?,?)";
			    	imageStmt = conn.prepareStatement(imgSql);;
			    	conn.setAutoCommit(false);
			    	for(String str:content.getImageList()) {
			    		 imageStmt.setString(1,str);
			    		 imageStmt.setInt(2, con_id);
			    		 imageStmt.addBatch();
			    	}
			    	imageStmt.executeBatch();
		    	}
		    	catch(SQLException e){
		    		String deleteSql ="DELETE FROM content WHERE id ="+con_id;
		    		PreparedStatement deleStmt = conn.prepareStatement(deleteSql);
		    		deleStmt.executeUpdate();
		    	}
		    	finally{
		    		conn.setAutoCommit(true);
		    		try {
		    			if(imageStmt!=null){
		    				imageStmt.close();
		    			}if(conn != null){
		    				conn.close();
		    			}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    }
		    result = Constant.SQL_EXE_OK;   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				if(stmt!=null){
					stmt.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void delete(int con_id,int is_contain_img,List<String> imgNames,String path){
		Connection conn = null; 
		try {
			conn = C3PODataSource.getConn();
			conn.setAutoCommit(false);
			String sql ="DELETE FROM content where id =?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,con_id);
			stmt.addBatch();
			if(is_contain_img == 1) {
				stmt.addBatch("DELETE FROM images WHERE con_id ="+con_id);
			}
			stmt.addBatch("DELETE FROM images WHERE con_id ="+con_id);
			stmt.addBatch("DELETE FROM comment WHERE con_id ="+con_id);
			stmt.addBatch("DELETE FROM praise WHERE con_id ="+con_id);
			stmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			File file = null;
			for (String str:imgNames) {
				file = new File(path,str);
				file.delete();
			}
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		 
	}

	/**
	 * 别的用户浏览别的用户的
	 * @param user_id 自己的user_id
	 * @return
	 */
	public List<Content> query(int user_id) {
		Connection conn = null; 
		List<Content> contents = new ArrayList<Content>();
		try {
			conn = C3PODataSource.getConn();
			Map<Integer,String> pMap = queryContentPraise(conn,user_id);
			String sql ="SELECT * FROM content";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet set = stmt.executeQuery();
			Content content = null;
			List<String> urls = null;
			while (set.next()) {
				content = new Content();
				int con_id = set.getInt(1);
				content.setId(con_id);
				if(pMap.containsKey(con_id)){
					content.setPraiceUserName(pMap.get(con_id));
				}
				content.setContent(set.getString(2));
				int c_user_id = set.getInt(3);
				content.setUser_id(c_user_id);
				content.setIs_contain_image(set.getInt(4));
				int is_contain_img = set.getInt(4);
				urls = new ArrayList<String>();
				if (is_contain_img == 1){
					urls = queryContentPics(conn,con_id);
				}
				List<Comment> comments = queryComment(conn,user_id,c_user_id,con_id);
				content.setCommentList(comments);
				content.setImageList(urls);
				contents.add(content);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return contents;
	}

	/**
	 * 
	 * @param visit_user_id 自己的ID
	 * @param user_id 查询的用户的ID;
	 * @return
	 */
	public List<ViewAUserContent> queryAnotherUserContent(int visit_user_id,int user_id) {
		Connection conn = null; 
		List<ViewAUserContent> contents = new ArrayList<ViewAUserContent>();
		try {
			conn = C3PODataSource.getConn();
			String sql ="SELECT id,cont,is_contain_image FROM content WHERE user_id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,user_id);
			ResultSet set = stmt.executeQuery();
			ViewAUserContent content = null;
			List<String> urls = null;
			while (set.next()) {
				content = new ViewAUserContent();
				int con_id = set.getInt(1);
				content.setId(con_id);
				content.setContent(set.getString(2));
				content.setUser_id(user_id);
				Praise praise = queryContentPraiseAnotherUser(conn,visit_user_id,con_id);
			    content.setVisitUserPraise(praise);
				int is_contain_img = set.getInt(3);
				content.setIs_contain_image(is_contain_img);
				urls = new ArrayList<String>();
				if (is_contain_img == 1){
					urls = queryContentPics(conn,con_id);
				}
				List<Comment> comments = queryComment(conn,visit_user_id,user_id,con_id);
				content.setCommentList(comments);
				content.setImageList(urls);
				contents.add(content);
			}
			return contents;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return contents;
		
	}

	/**
	 * 自己查询自己发表过的内容
	 * @param user_id 用户自己的user_id
	 * @return
	 */
	public List<Content> queryOwnContent(int own_id) {
		Connection conn = null; 
		List<Content> contents = new ArrayList<Content>();
		try {
			conn = C3PODataSource.getConn();
			String sql ="SELECT * FROM content where user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,own_id);
			ResultSet set = stmt.executeQuery();
			Content content = null;
			List<String> urls = null;
			while (set.next()) {
				content = new Content();
				int con_id = set.getInt(1);
				content.setId(con_id);
				content.setContent(set.getString(2));
				int c_user_id = set.getInt(3);
				content.setUser_id(c_user_id);
				content.setIs_contain_image(set.getInt(4));
				int is_contain_img = set.getInt(4);
				urls = new ArrayList<String>();
				if (is_contain_img == 1){
					urls = queryContentPics(conn,con_id);
				}
				List<Comment> comments = queryOwnContentComment(conn,con_id);
				List<Praise> praises = queryOwnPraice(conn,con_id);
				content.setPraiseList(praises);
				content.setCommentList(comments);
				content.setImageList(urls);
				contents.add(content);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return contents;
	}
	
	/**
	 * 查询发表内容的图片
	 * @param conn
	 * @param con_id
	 * @return
	 * @throws SQLException
	 */
	private List<String> queryContentPics(Connection conn,int con_id) throws SQLException{
		List<String> pics = new ArrayList<String>(9);
		String sql = "SELECT url FROM images WHERE con_id=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,con_id);
		ResultSet set = stmt.executeQuery();
		while(set.next()){
			pics.add(set.getString(1));
		}
		return pics;
	}
	
	/**
	 * 查看此用户ID下的点赞
	 * @param conn
	 * @param user_id
	 * @return
	 * @throws SQLException
	 */
	private Map<Integer,String> queryContentPraise(Connection conn,int user_id) throws SQLException{
		String priceSql = "SELECT con_id,praiceUserName FROM praise WHERE user_id =?";
		PreparedStatement priceStmt = conn.prepareStatement(priceSql);
		priceStmt.setInt(1,user_id);
		ResultSet priceSet = priceStmt.executeQuery();
		Map<Integer,String> pMap = new HashMap<Integer,String>();
		while(priceSet.next()){
			 pMap.put(priceSet.getInt(1),priceSet.getString(2));
		}
		return pMap;
	}
	/**
	 * 用户浏览某一用户时自己对某一用户的点赞
	 * @param conn
	 * @param visit_user_id
	 * @param con_id
	 * @return 点赞返回praise,没点赞返回Null
	 * @throws SQLException
	 */
	private Praise queryContentPraiseAnotherUser(Connection conn,int visit_user_id,int con_id) throws SQLException{
		String priceSql = "SELECT id,praiceUserName FROM praise WHERE user_id =? AND con_id=?";
		PreparedStatement priceStmt = conn.prepareStatement(priceSql);
		priceStmt.setInt(1,visit_user_id);
		priceStmt.setInt(2,con_id);
		ResultSet priceSet = priceStmt.executeQuery();
		Praise praise = null;
		if(priceSet.next()){
			 praise = new Praise();
			 praise.setId(priceSet.getInt(1));
			 praise.setPriceUserName(priceSet.getString(2));
			 praise.setUser_id(visit_user_id);
			 praise.setCon_id(con_id);
		}
		return praise;
	}
	/**
	 * 查看此用户下的评论数
	 * @param conn
	 * @param user_id
	 * @param re_user_id
	 * @param con_id
	 * @return
	 * @throws SQLException
	 */
	private List<Comment> queryComment(Connection conn,int user_id,int re_user_id,int con_id) throws SQLException{
		List<Comment> comments = new ArrayList<Comment>();
		String sql = "SELECT * FROM comment where (user_id=? AND re_user_id =?) OR (user_id =? AND re_user_id=?) AND con_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,user_id);
		stmt.setInt(2,re_user_id);
		stmt.setInt(3,re_user_id);
		stmt.setInt(4,user_id);
		stmt.setInt(5,con_id);
		ResultSet set = stmt.executeQuery();
		Comment comment = null;
		while(set.next()){
			comment = new Comment();
			comment.setId(set.getInt(1));
			comment.setComment_content(set.getString(2));
			comment.setCon_id(set.getInt(3));
			comment.setUser_id(set.getInt(4));
			comment.setRe_user_id(set.getInt(5));
			comments.add(comment);
		}
		return comments;
	}
	
	/**
	 * 用户自己查看自己的评论
	 * @param conn
	 * @param user_id
	 * @param re_user_id
	 * @param con_id
	 * @return
	 * @throws SQLException
	 */
	private List<Comment> queryOwnContentComment(Connection conn,int con_id) throws SQLException{
		List<Comment> comments = new ArrayList<Comment>();
		String sql = "SELECT * FROM comment where con_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,con_id);
		ResultSet set = stmt.executeQuery();
		Comment comment = null;
		while(set.next()){
			comment = new Comment();
			comment.setId(set.getInt(1));
			comment.setComment_content(set.getString(2));
			comment.setCon_id(set.getInt(3));
			comment.setUser_id(set.getInt(4));
			comment.setRe_user_id(set.getInt(5));
			comments.add(comment);
		}
		return comments;
	}
	
	/**
	 * 用户自己查看自己的点赞
	 * @param conn
	 * @param user_id
	 * @param re_user_id
	 * @param con_id
	 * @return
	 * @throws SQLException
	 */
	private List<Praise> queryOwnPraice(Connection conn,int con_id) throws SQLException{
		List<Praise> praises = new ArrayList<Praise>();
		String sql = "SELECT id,user_id,praiceUserName FROM praise where con_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1,con_id);
		ResultSet set = stmt.executeQuery();
		Praise praise = null;
		while(set.next()){
			praise = new Praise();
			praise.setId(set.getInt(1));
			praise.setCon_id(con_id);
			praise.setUser_id(set.getInt(2));
			praise.setPriceUserName(set.getString(3));
			praises.add(praise);
		}
		return praises;
	}
}
