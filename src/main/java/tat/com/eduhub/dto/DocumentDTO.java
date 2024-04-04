package tat.com.eduhub.dto;

public class DocumentDTO {

	private Long id;
	
	private String fileName;
	
	private String name;
	
	private String type;
	
	private String share;
	
	private Long idModule;

	public Long getIdModule() {
		return idModule;
	}

	public void setIdModule(Long idModule) {
		this.idModule = idModule;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public DocumentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DocumentDTO [id=" + id + ", fileName=" + fileName + ", name=" + name + ", type=" + type + ", share="
				+ share + ", idModule=" + idModule + "]";
	}

	
	
}
