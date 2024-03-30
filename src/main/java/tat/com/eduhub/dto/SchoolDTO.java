package tat.com.eduhub.dto;

public class SchoolDTO {

	private String name;
	
	private String domain;
	
	private String email;
	
	private String hotline;
	
	private String logo;

	private String defaultLogo = "defaultLogoSchool.png";
	
	public SchoolDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHotline() {
		return hotline;
	}

	public void setHotline(String hotline) {
		this.hotline = hotline;
	}

	public String getLogo() {
		if( this.logo == null || this.logo.trim().length() == 0) {
			return this.defaultLogo;
		}
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

}
