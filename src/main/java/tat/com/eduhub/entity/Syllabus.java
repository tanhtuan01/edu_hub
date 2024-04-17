package tat.com.eduhub.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_syllabus")
public class Syllabus extends BaseEntity{

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_module")
	private Modules module;

	private String name;
	
	@Column(name = "file_name")
	private String fileName;
	
	@OneToMany(mappedBy = "syllabus", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SubjectDistributionDetail> subjectDistributionDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_school")
	private School school;

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public List<SubjectDistributionDetail> getSubjectDistributionDetails() {
		return subjectDistributionDetails;
	}

	public void setSubjectDistributionDetails(List<SubjectDistributionDetail> subjectDistributionDetails) {
		this.subjectDistributionDetails = subjectDistributionDetails;
	}

	public Modules getModule() {
		return module;
	}

	public void setModule(Modules module) {
		this.module = module;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Syllabus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "Syllabus [name=" + name + ", fileName=" + fileName + ", getId()=" + getId() + "]";
	}
	
	
	
}
