package tat.com.eduhub.dto;

public class SchoolDTO {
	
	private Long id;

	private String name;
	
	private String domain;
	
	private String email;
	
	private String hotline;
	
	private String logo;

	private String defaultLogo = "defaultLogoSchool.png";
	
	private String status;
	
	private String address;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDefaultLogo() {
		return defaultLogo;
	}

	public void setDefaultLogo(String defaultLogo) {
		this.defaultLogo = defaultLogo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SchoolDTO [id=" + id + ", name=" + name + ", domain=" + domain + ", email=" + email + ", hotline="
				+ hotline + ", logo=" + logo + ", status=" + status + ", address=" + address + "]";
	}
	
	
}
