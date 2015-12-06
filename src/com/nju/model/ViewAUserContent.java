package com.nju.model;

import java.util.List;

public class ViewAUserContent {

	private int id;
	private int user_id;
	private String content;
	private List<String> imageList;
	private int is_contain_image;
	private Praise visitUserPraise;
	private List<Comment> commentList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getImageList() {
		return imageList;
	}
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
	public int getIs_contain_image() {
		return is_contain_image;
	}
	public void setIs_contain_image(int is_contain_image) {
		this.is_contain_image = is_contain_image;
	}
	public Praise getVisitUserPraise() {
		return visitUserPraise;
	}
	public void setVisitUserPraise(Praise visitUserPraise) {
		this.visitUserPraise = visitUserPraise;
	}
	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	
	
}
