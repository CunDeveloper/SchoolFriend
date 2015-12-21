package com.nju.model;

import java.util.ArrayList;
import java.util.List;

public class Content {

	private int id;
	private int user_id;
	private String content;
	private List<String> imageList = new ArrayList<String>();
	private int is_contain_image;
	private String praiceUserName;/*要废掉*/
	private List<Praise> praiseList;
	private List<Comment> commentList;
	private String userLocation;
	private String date;
	private String location;
	
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
	public String getPraiceUserName() {
		return praiceUserName;
	}
	public void setPraiceUserName(String praiceUserName) {
		this.praiceUserName = praiceUserName;
	}
	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	public List<Praise> getPraiseList() {
		return praiseList;
	}
	public void setPraiseList(List<Praise> praiseList) {
		this.praiseList = praiseList;
	}
	public String getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
}
