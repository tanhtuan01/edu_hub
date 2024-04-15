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
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_document")
public class Document extends BaseEntity{

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_module")
	private Modules module;
	
	@Size(max = 20)
	private String type;
	
	@Size(max = 20)
	private String share;

	private String name;
	
	@Column(name = "file_name")
	private String fileName;
	
	@OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SubjectDistributionDetail> subjectDistributionDetails;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
}
