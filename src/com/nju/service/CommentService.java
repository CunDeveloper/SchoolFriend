package com.nju.service;

import com.nju.dao.CommentDao;
import com.nju.dao.impl.CommentDaoImpl;
import com.nju.model.Comment;

public class CommentService {

	private CommentDao commentDao;
	
	public CommentService() {
		commentDao = new CommentDaoImpl();
	}
	
	public int save(Comment comment) {
		return commentDao.save(comment);
	}
}
