package tat.com.eduhub.dto;

public class UserDTO {

	private String userName;
	
	private String email;
	
	private String passwords;
	
	private String avt;
	
	private String sendToEmail;
	
	private String receiveMail;

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

	public String getSendToEmail() {
		return sendToEmail;
	}

	public void setSendToEmail(String sendToEmail) {
		this.sendToEmail = sendToEmail;
	}

	public String getReceiveMail() {
		return receiveMail;
	}

	public void setReceiveMail(String receiveMail) {
		this.receiveMail = receiveMail;
	}

	@Override
	public String toString() {
		return "UserDTO [userName=" + userName + ", email=" + email + ", passwords=" + passwords + ", avt=" + avt
				+ ", sendToEmail=" + sendToEmail + ", receiveMail=" + receiveMail + "]";
	}
	
	
}
