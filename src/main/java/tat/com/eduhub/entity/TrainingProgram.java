package tat.com.eduhub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_training_program")
public class TrainingProgram extends BaseEntity{

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_school")
	private School school;
	
	@Column(name = "name", length = 120)
	private String name; // ten chuong trinh
	
	@Column(name = "level", length = 30)
	private String level;// trinh do dao tao
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_major")
	private Major major;
	
	@Column(name = "type")
	private String type; // loai hinh dao tao
	
	@Column(name = "cohort", length = 30)
	private String cohort;
	
	@Column(columnDefinition = "LONGTEXT", name = "general_objective")
	private String generalObjective;
	
	@Column(columnDefinition = "LONGTEXT", name = "specific_objective")
	private String specificObjective;
	
	@Column(columnDefinition = "LONGTEXT", name = "output_standards")
	private String outputStandards;
	
	@Column(columnDefinition = "LONGTEXT", name = "job_prospects")
	private String jobProspects;
	
	@Column(name = "duration")
	private int duration;
	
	@Column(name = "number_of_semesters")
	private int numberOfSemesters;
	
	@Column(name = "total_credits")
	private int totalCredits;
	
	@Column(name = "target_applicants", columnDefinition = "LONGTEXT")
	private String targetApplicants;
	
	@Column(name = "process", columnDefinition = "LONGTEXT")
	private String process;
	
	@Column(name = "graduating_cohort", columnDefinition = "LONGTEXT")
	private String graduatingCohort;

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCohort() {
		return cohort;
	}

	public void setCohort(String cohort) {
		this.cohort = cohort;
	}

	public String getGeneralObjective() {
		return generalObjective;
	}

	public void setGeneralObjective(String generalObjective) {
		this.generalObjective = generalObjective;
	}

	public String getSpecificObjective() {
		return specificObjective;
	}

	public void setSpecificObjective(String specificObjective) {
		this.specificObjective = specificObjective;
	}

	public String getOutputStandards() {
		return outputStandards;
	}

	public void setOutputStandards(String outputStandards) {
		this.outputStandards = outputStandards;
	}

	public String getJobProspects() {
		return jobProspects;
	}

	public void setJobProspects(String jobProspects) {
		this.jobProspects = jobProspects;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getNumberOfSemesters() {
		return numberOfSemesters;
	}

	public void setNumberOfSemesters(int numberOfSemesters) {
		this.numberOfSemesters = numberOfSemesters;
	}

	public int getTotalCredits() {
		return totalCredits;
	}

	public void setTotalCredits(int totalCredits) {
		this.totalCredits = totalCredits;
	}

	public String getTargetApplicants() {
		return targetApplicants;
	}

	public void setTargetApplicants(String targetApplicants) {
		this.targetApplicants = targetApplicants;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getGraduatingCohort() {
		return graduatingCohort;
	}

	public void setGraduatingCohort(String graduatingCohort) {
		this.graduatingCohort = graduatingCohort;
	}
	
	
}
