package tat.com.eduhub.dto;

public class IndustryDTO {

	private Long id;
	
	private String industryCode;
	
	private String industryName;
	
	private Long idSchool;

	public IndustryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public Long getIdSchool() {
		return idSchool;
	}

	public void setIdSchool(Long idSchool) {
		this.idSchool = idSchool;
	}

	@Override
	public String toString() {
		return "IndustryDTO [id=" + id + ", industryCode=" + industryCode + ", industryName=" + industryName
				+ ", idSchool=" + idSchool + "]";
	}
	
	

}
