package tat.com.eduhub.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
	
	@Size(max = 120)
	private String address;
	
	@Size(max = 20)
	private String status;
	
	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TeacherOfSchool> teacherOfSchools;
	
	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Industry> industries;
	
	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TrainingProgram> trainingPrograms;
	
	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Modules> modules;
	
	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Document> documents;
	
	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Syllabus> syllabus;

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

	public List<TeacherOfSchool> getTeacherOfSchools() {
		return teacherOfSchools;
	}

	public void setTeacherOfSchools(List<TeacherOfSchool> teacherOfSchools) {
		this.teacherOfSchools = teacherOfSchools;
	}

	public List<Industry> getIndustries() {
		return industries;
	}

	public void setIndustries(List<Industry> industries) {
		this.industries = industries;
	}

	public List<TrainingProgram> getTrainingPrograms() {
		return trainingPrograms;
	}

	public void setTrainingPrograms(List<TrainingProgram> trainingPrograms) {
		this.trainingPrograms = trainingPrograms;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public School(String name, String domain, String email,
			String hotline, String logo, String status) {
		super();
		this.name = name;
		this.domain = domain;
		this.email = email;
		this.hotline = hotline;
		this.logo = logo;
		this.status = status;
	}

	public List<Modules> getModules() {
		return modules;
	}

	public void setModules(List<Modules> modules) {
		this.modules = modules;
	}

	@Override
	public String toString() {
		return "School [name=" + name + ", domain=" + domain + ", email=" + email + ", hotline=" + hotline + ", logo="
				+ logo + ", status=" + status + ", getId()=" + getId() + "]";
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	
	
	
}
