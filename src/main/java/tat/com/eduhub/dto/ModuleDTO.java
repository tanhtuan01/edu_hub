package tat.com.eduhub.dto;


public class ModuleDTO {

	private Long id;
	
	private Long idMajor;
	
	private String name;
	
	private String code;
	
	private int credits;
	
	private int theory;
	
	private int practise;
	
	private int exercise;
	
	private int selfStudy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdMajor() {
		return idMajor;
	}

	public void setIdMajor(Long idMajor) {
		this.idMajor = idMajor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getTheory() {
		return theory;
	}

	public void setTheory(int theory) {
		this.theory = theory;
	}

	public int getPractise() {
		return practise;
	}

	public void setPractise(int practise) {
		this.practise = practise;
	}

	public int getExercise() {
		return exercise;
	}

	public void setExercise(int exercise) {
		this.exercise = exercise;
	}

	public int getSelfStudy() {
		return selfStudy;
	}

	public void setSelfStudy(int selfStudy) {
		this.selfStudy = selfStudy;
	}

	public ModuleDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ModuleDTO [id=" + id + ", idMajor=" + idMajor + ", name=" + name + ", code=" + code + ", credits="
				+ credits + ", theory=" + theory + ", practise=" + practise + ", exercise=" + exercise + ", selfStudy="
				+ selfStudy + "]";
	}
	
	
	
}
