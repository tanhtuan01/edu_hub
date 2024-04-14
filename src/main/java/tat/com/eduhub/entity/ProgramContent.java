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
@Table(name = "tb_program_content")
public class ProgramContent extends BaseEntity{

	@Column(name = "knowledge_area", length = 120)
	private String knowledgeArea;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tp")
	private TrainingProgram trainingProgram;
	
	@OneToMany(mappedBy = "programContent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<KnowledgeModule> knowledgeModules;

	public List<KnowledgeModule> getKnowledgeModules() {
		return knowledgeModules;
	}

	public void setKnowledgeModules(List<KnowledgeModule> knowledgeModules) {
		this.knowledgeModules = knowledgeModules;
	}

	public String getKnowledgeArea() {
		return knowledgeArea;
	}

	public void setKnowledgeArea(String knowledgeArea) {
		this.knowledgeArea = knowledgeArea;
	}

	public TrainingProgram getTrainingProgram() {
		return trainingProgram;
	}

	public void setTrainingProgram(TrainingProgram trainingProgram) {
		this.trainingProgram = trainingProgram;
	}

	public ProgramContent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProgramContent(String knowledgeArea) {
		super();
		this.knowledgeArea = knowledgeArea;
	}
	
	
}
