package com.jialu.sawa.vo;

import java.security.Principal;

import com.jialu.sawa.base.model.MiniUserModel;


public class OperatorRole implements Principal{
	private MiniUserModel user;
	private String name;
	private String role;
	
	public  OperatorRole(String name){
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public MiniUserModel getUser() {
		return user;
	}

	public void setUser(MiniUserModel user) {
		this.user = user;
	}

}
