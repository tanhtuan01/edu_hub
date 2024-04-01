package tat.com.eduhub.dto;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import tat.com.eduhub.entity.Major;
import tat.com.eduhub.entity.School;

public class TrainingProgramDTO {

	private Long id;
	
	private Long idSchool;
	
	private String name; // ten chuong trinh
	
	private String level;// trinh do dao tao
	
	private Long idMajor;
	
	private String type; // loai hinh dao tao
	
	private String cohort;
	
	private String generalObjective;
	
	private String specificObjective;
	
	private String outputStandards;
	
	private String jobProspects;
	
	private int duration;
	
	private int numberOfSemesters;
	
	private int totalCredits;
	
	private String targetApplicants;
	
	private String process;
	
	private String graduatingCohort;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdSchool() {
		return idSchool;
	}

	public void setIdSchool(Long idSchool) {
		this.idSchool = idSchool;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Long getIdMajor() {
		return idMajor;
	}

	public void setIdMajor(Long idMajor) {
		this.idMajor = idMajor;
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

	public TrainingProgramDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
