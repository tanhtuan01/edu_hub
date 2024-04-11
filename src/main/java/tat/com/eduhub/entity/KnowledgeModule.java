package tat.com.eduhub.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_knowledge_module")
public class KnowledgeModule extends BaseEntity{

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_program_content")
	private ProgramContent programContent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_module")
	private Modules modules;

	public KnowledgeModule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProgramContent getProgramContent() {
		return programContent;
	}

	public void setProgramContent(ProgramContent programContent) {
		this.programContent = programContent;
	}

	public Modules getModules() {
		return modules;
	}

	public void setModules(Modules modules) {
		this.modules = modules;
	}
	
}
