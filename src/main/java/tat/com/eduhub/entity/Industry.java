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
@Table(name = "tb_industry")
public class Industry extends BaseEntity{

	@Column(name = "industry_code", length = 20)
	private String industryCode;
	
	@Column(name = "industry_name", length = 60)
	private String industryName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_school")
	private School school;
	
	@OneToMany(mappedBy = "industry", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Major> majors;

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

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Industry() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Major> getMajors() {
		return majors;
	}

	public void setMajors(List<Major> majors) {
		this.majors = majors;
	}

	@Override
	public String toString() {
		return "Industry [industryCode=" + industryCode + ", industryName=" + industryName + "]";
	}
	
	
}
