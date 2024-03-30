package tat.com.eduhub.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_school")
public class School extends BaseEntity{

	@Size(max = 60)
	private String name;
	
	@Size(max = 30)
	private String domain;
	
	@Size(max = 60)
	private String email;
	
	@Size(max = 15)
	private String hotline;
	
	private String logo;

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
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public School() {
		super();
		// TODO Auto-generated constructor stub
	}

	public School(String name, String domain, String email,
			String hotline, String logo) {
		super();
		this.name = name;
		this.domain = domain;
		this.email = email;
		this.hotline = hotline;
		this.logo = logo;
	}
	
	
	
}
