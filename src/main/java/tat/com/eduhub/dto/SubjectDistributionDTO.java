package tat.com.eduhub.dto;

public class SubjectDistributionDTO {

	private Long id;
	
	private String tpName;
	
	private String status;
	
	private String moduleName;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTpName() {
		return tpName;
	}

	public void setTpName(String tpName) {
		this.tpName = tpName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(int sizeSyllabus, int sizeDocument) {
		if(sizeSyllabus > 0 && sizeDocument > 0) {
			this.status = "Hoàn thành";
		}else {
			this.status = "Chưa hoàn thành";
		}
	}

	public SubjectDistributionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
