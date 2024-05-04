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
@Table(name = "tb_category_lesson")
public class CategoryLesson extends BaseEntity{

	@Size(max = 120)
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_courses")
	private Courses courses;
	
	@OneToMany(mappedBy = "categoryLesson", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Lesson> lessons;
	
	@Column(columnDefinition = "TINYINT")
	private int stt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Courses getCourses() {
		return courses;
	}

	public void setCourses(Courses courses) {
		this.courses = courses;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}

	public CategoryLesson() {
		super();
		// TODO Auto-generated constructor stub
	}
}
