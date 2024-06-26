package tat.com.eduhub.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_subject_distribution")
public class SubjectDistribution extends BaseEntity{

	private int semester;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tp")
	private TrainingProgram trainingProgram;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_module")
	private Modules module;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	private User user;
	
	@OneToMany(mappedBy = "subjectDistribution", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SubjectDistributionDetail> subjectDistributionDetails;

	public List<SubjectDistributionDetail> getSubjectDistributionDetails() {
		return subjectDistributionDetails;
	}

	public void setSubjectDistributionDetails(List<SubjectDistributionDetail> subjectDistributionDetails) {
		this.subjectDistributionDetails = subjectDistributionDetails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public TrainingProgram getTrainingProgram() {
		return trainingProgram;
	}

	public void setTrainingProgram(TrainingProgram trainingProgram) {
		this.trainingProgram = trainingProgram;
	}

	public Modules getModule() {
		return module;
	}

	public void setModule(Modules module) {
		this.module = module;
	}

	@Override
	public String toString() {
		return "SubjectDistribution [semester=" + semester + ", trainingProgram=" + trainingProgram + ", module="
				+ module + ", user=" + user + "]";
	}


}
