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
@Table(name = "tb_module")
public class Modules extends BaseEntity{
// Học phần
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_school")
	private School school;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_major")
	private Major major;
	
	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Syllabus> syllabus;
	
	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Document> documents;
	
	@Column(name = "name", length = 60)
	private String name;
	
	@Column(name = "code", length = 20)
	private String code;
	
	private int credits;
	
	private int theory;
	
	private int practise;
	
	private int exercise;
	
	@Column(name = "self_study")
	private int selfStudy;
	
	@OneToMany(mappedBy = "modules", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<KnowledgeModule> knowledgeModules;

	public List<KnowledgeModule> getKnowledgeModules() {
		return knowledgeModules;
	}

	public void setKnowledgeModules(List<KnowledgeModule> knowledgeModules) {
		this.knowledgeModules = knowledgeModules;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getTheory() {
		return theory;
	}

	public void setTheory(int theory) {
		this.theory = theory;
	}

	public int getPractise() {
		return practise;
	}

	public void setPractise(int practise) {
		this.practise = practise;
	}

	public int getExercise() {
		return exercise;
	}

	public void setExercise(int exercise) {
		this.exercise = exercise;
	}

	public int getSelfStudy() {
		return selfStudy;
	}

	public void setSelfStudy(int selfStudy) {
		this.selfStudy = selfStudy;
	}

	public Modules() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Syllabus> getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(List<Syllabus> syllabus) {
		this.syllabus = syllabus;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	@Override
	public String toString() {
		return "Modules [major=" + major + ", name=" + name + ", code=" + code + ", credits=" + credits + ", theory="
				+ theory + ", practise=" + practise + ", exercise=" + exercise + ", selfStudy=" + selfStudy + "]";
	}
	
	
}
