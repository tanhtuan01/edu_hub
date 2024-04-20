package tat.com.eduhub.dto;

import javax.servlet.http.HttpSession;

public class UserDataInfo {

	private String email;
	
	private String name;
	
	private String loginMethod;
	
	private String role;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginMethod() {
		return loginMethod;
	}

	public void setLoginMethod(String loginMethod) {
		this.loginMethod = loginMethod;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UserDataInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDataInfo(String email, String name, String loginMethod, String role) {
		super();
		this.email = email;
		this.name = name;
		this.loginMethod = loginMethod;
		this.role = role;
	}
	
}
