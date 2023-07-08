package com.fullstack.backendjwt.model;

public class AuthenticationReq {
	
	String userName;
	String password;
	
	public AuthenticationReq() {}
	
	public AuthenticationReq(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
