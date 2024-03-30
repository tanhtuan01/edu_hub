package tat.com.eduhub.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_major")
public class Major extends BaseEntity{

	@Column(name = "major_code", length = 20)
	private String majorCode;
	
	@Column(name = "major_name", length = 60)
	private String majorName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_industry")
	private Industry industry;

	@OneToMany(mappedBy = "major")
	private List<TrainingProgram> trainingPrograms;
	
	@OneToMany(mappedBy = "major")
	private List<Module> modules;
	
	public String getMajorCode() {
		return majorCode;
	}

	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public List<TrainingProgram> getTrainingPrograms() {
		return trainingPrograms;
	}

	public void setTrainingPrograms(List<TrainingProgram> trainingPrograms) {
		this.trainingPrograms = trainingPrograms;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	
	
}
