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
@Table(name = "tb_courses")
public class Courses extends BaseEntity{

	@Size(max = 120)
	private String name;
	
	@Column(columnDefinition = "LONGTEXT")
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_author")
	private User user;
	
	@OneToMany(mappedBy = "courses", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Lesson> lessons;
	
	@OneToMany(mappedBy = "courses", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentCourses> studentCourses;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	public List<StudentCourses> getStudentCourses() {
		return studentCourses;
	}

	public void setStudentCourses(List<StudentCourses> studentCourses) {
		this.studentCourses = studentCourses;
	}
	
	
}
