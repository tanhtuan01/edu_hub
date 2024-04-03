package tat.com.eduhub.dto;


public class SyllabusDTO {

	private Long id;
	
	private String name;

	private Long idModule;
	
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public Long getIdModule() {
		return idModule;
	}

	public void setIdModule(Long idModule) {
		this.idModule = idModule;
	}

	public SyllabusDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "SyllabusDTO [id=" + id + ", name=" + name + ", idModule=" + idModule + "]";
	}
	
	
}
