package tat.com.eduhub.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_student_lesson")
public class StudentLessons extends BaseEntity{

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_student")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_lesson")
	private Lesson lesson;
	
	@Size(max = 20)
	private String progess;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_courses")
	private Courses courses;

	public Courses getCourses() {
		return courses;
	}

	public void setCourses(Courses courses) {
		this.courses = courses;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public String getProgess() {
		return progess;
	}

	public void setProgess(String progess) {
		this.progess = progess;
	}
	
	
}
