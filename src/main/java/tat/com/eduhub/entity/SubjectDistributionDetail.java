package tat.com.eduhub.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_subject_distribution_detail")
public class SubjectDistributionDetail extends BaseEntity{

	// chi tiết phân phối môn học: gồm tài liệu, đề cương
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_subdis")
	private SubjectDistribution subjectDistribution;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_document")
	private Document document;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_syllabus")
	private Syllabus syllabus;

	public SubjectDistribution getSubjectDistribution() {
		return subjectDistribution;
	}

	public void setSubjectDistribution(SubjectDistribution subjectDistribution) {
		this.subjectDistribution = subjectDistribution;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Syllabus getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}

	public SubjectDistributionDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
