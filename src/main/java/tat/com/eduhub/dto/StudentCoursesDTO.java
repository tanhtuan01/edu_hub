package tat.com.eduhub.dto;

import java.util.Date;

public class StudentCoursesDTO {

	private String name;
	
	private String coursesName;
	
	private Date date;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoursesName() {
		return coursesName;
	}

	public void setCoursesName(String coursesName) {
		this.coursesName = coursesName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public StudentCoursesDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
