package com.nju.model;

public class User {

	private int id;
	private String username;
	private String password;
	private String headIcon;
	private String sign;
	private String realName;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	 
	public String getHeadIcon() {
		return headIcon;
	}
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString(){
		return "["+this.id+","+this.username+","+this.headIcon+","+this.sign+"]";
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
}
