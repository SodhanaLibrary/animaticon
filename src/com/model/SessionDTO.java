package com.model;

import java.util.ArrayList;

public class SessionDTO {
	String user_id;
	String email;
	String name;
	ArrayList<String> entitlements = new ArrayList<String>();
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ArrayList<String> getEntitlements() {
		return entitlements;
	}
	public void setEntitlements(ArrayList<String> defaultEntitlements) {
		this.entitlements = defaultEntitlements;
	}
	public void addEntitlement(String ent) {
		this.entitlements.add(ent);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
