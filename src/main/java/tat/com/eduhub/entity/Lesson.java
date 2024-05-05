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
@Table(name = "tb_lesson")
public class Lesson extends BaseEntity{

	@Size(max = 120)
	private String name;
	
	@Column(columnDefinition = "LONGTEXT")
	private String description;
	
	@Column(name = "file_name", length = 255)
	private String fileName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_courses")
	private Courses courses;
	
	@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentLessons> studentLessons;
	
	private boolean preview;
	
	private String content;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_category_lesson")
	private CategoryLesson categoryLesson;

	public CategoryLesson getCategoryLesson() {
		return categoryLesson;
	}

	public void setCategoryLesson(CategoryLesson categoryLesson) {
		this.categoryLesson = categoryLesson;
	}

	public boolean isPreview() {
		return preview;
	}

	public void setPreview(boolean preview) {
		this.preview = preview;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Courses getCourses() {
		return courses;
	}

	public void setCourses(Courses courses) {
		this.courses = courses;
	}

	public List<StudentLessons> getStudentLessons() {
		return studentLessons;
	}

	public void setStudentLessons(List<StudentLessons> studentLessons) {
		this.studentLessons = studentLessons;
	}
	
	
}
