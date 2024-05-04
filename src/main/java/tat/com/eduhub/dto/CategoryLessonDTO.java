package tat.com.eduhub.dto;

public class CategoryLessonDTO {

	private Long id;
	
	private String name;
	
	private int stt;
	
	private Long idCourses;
	
	private Long idLesson;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}

	public Long getIdCourses() {
		return idCourses;
	}

	public void setIdCourses(Long idCourses) {
		this.idCourses = idCourses;
	}

	public Long getIdLesson() {
		return idLesson;
	}

	public void setIdLesson(Long idLesson) {
		this.idLesson = idLesson;
	}

	public CategoryLessonDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
