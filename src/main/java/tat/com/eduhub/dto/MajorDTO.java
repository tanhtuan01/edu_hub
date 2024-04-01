package tat.com.eduhub.dto;

public class MajorDTO {

	private Long id;
	
	private String majorCode;
	
	private String majorName;
	
	private Long idIndustry;
	
	private String industryName;
	
	

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMajorCode() {
		return majorCode;
	}

	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName.trim();
	}

	public Long getIdIndustry() {
		return idIndustry;
	}

	public void setIdIndustry(Long idIndustry) {
		this.idIndustry = idIndustry;
	}

	public MajorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "MajorDTO [id=" + id + ", majorCode=" + majorCode + ", majorName=" + majorName + ", idIndustry="
				+ idIndustry + "]";
	}

	
}
