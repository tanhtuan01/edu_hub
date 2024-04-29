package tat.com.eduhub.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "tb_user")
public class User extends BaseEntity{
	
	@NotNull
	@Column(name = "username")
	private String userName;
	
	@NotNull
	@Size(max = 60)
	private String email;
	
	@NotNull
	@Size(max = 80)
	private String passwords;
	
	@Column(name = "receive_mail", length = 60)
	private String receiveMail;
	
	private String avt;
	
	@Size(max = 20)
	private String type;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "tb_users_role",
			joinColumns = @JoinColumn(referencedColumnName = "id", name = "id_user"),
			inverseJoinColumns = @JoinColumn(referencedColumnName = "id", name = "id_role")
	)
	private List<Role> roles;
	
	@OneToOne(mappedBy = "user")
	private TeacherOfSchool teacherOfSchool;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SubjectDistribution> subjectDistributions;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Courses> courses;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentCourses> studentCourses;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentLessons> studentLessons;
	
	@Size(max = 60)
	private String major;
	
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public List<StudentLessons> getStudentLessons() {
		return studentLessons;
	}

	public void setStudentLessons(List<StudentLessons> studentLessons) {
		this.studentLessons = studentLessons;
	}

	public List<StudentCourses> getStudentCourses() {
		return studentCourses;
	}

	public void setStudentCourses(List<StudentCourses> studentCourses) {
		this.studentCourses = studentCourses;
	}

	public List<Courses> getCourses() {
		return courses;
	}

	public void setCourses(List<Courses> courses) {
		this.courses = courses;
	}

	@Size(max = 60)
	private String diploma;
	
	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	public List<SubjectDistribution> getSubjectDistributions() {
		return subjectDistributions;
	}

	public void setSubjectDistributions(List<SubjectDistribution> subjectDistributions) {
		this.subjectDistributions = subjectDistributions;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswords() {
		return passwords;
	}

	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}

	public String getAvt() {
		return avt;
	}

	public void setAvt(String avt) {
		this.avt = avt;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public User(String userName, String email, String passwords, List<Role> roles) {
		super();
		this.userName = userName;
		this.email = email;
		this.passwords = passwords;
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", email=" + email + ", passwords=" + passwords + ", avt=" + avt
				+ ", getId()=" + getId() + "]";
	}

	public String getReceiveMail() {
		return receiveMail;
	}

	public void setReceiveMail(String receiveMail) {
		this.receiveMail = receiveMail;
	}

	public TeacherOfSchool getTeacherOfSchool() {
		return teacherOfSchool;
	}

	public void setTeacherOfSchool(TeacherOfSchool teacherOfSchool) {
		this.teacherOfSchool = teacherOfSchool;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
