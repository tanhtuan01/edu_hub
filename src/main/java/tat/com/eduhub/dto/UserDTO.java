package tat.com.eduhub.dto;

public class UserDTO {

	private String userName;
	
	private String email;
	
	private String passwords;
	
	private String avt;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswords() {
		return passwords;
	}

	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}

	public String getAvt() {
		return avt;
	}

	public void setAvt(String avt) {
		this.avt = avt;
	}

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
