package tat.com.eduhub.dto;

public class LessonDTO {
	
	private Long id;

	private String name;
	
	private String description;
	
	private String fileName;
	
	private boolean preview;
	
	private String content;
	
	private Long idCourses;

	public Long getIdCourses() {
		return idCourses;
	}

	public void setIdCourses(Long idCourses) {
		this.idCourses = idCourses;
	}

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

	public LessonDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String parsePreview() {
		return (this.preview) ? "Cho phép xem trước" : "Không xem trước";
	}

	@Override
	public String toString() {
		return "LessonDTO [id=" + id + ", name=" + name + ", description=" + description + ", fileName=" + fileName
				+ ", preview=" + preview + ", content=" + content + ", idCourses=" + idCourses + "]";
	}
	
}
