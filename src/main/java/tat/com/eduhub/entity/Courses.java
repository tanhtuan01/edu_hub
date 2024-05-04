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
	
	@Size(max = 15)
	private String type;
	
	@Column(name = "old_price")
	private Long oldPrice;
	
	@Column(columnDefinition = "TINYINT")
	private int discount;
	
	@Column(name = "new_price")
	private Long newPrice;
	
	@Size(max = 255)
	private String image;
	
	@Column(name = "short_description")
	private String shortDescription;
	
	@Size(max = 20)
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(Long oldPrice) {
		this.oldPrice = oldPrice;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public Long getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(Long newPrice) {
		this.newPrice = newPrice;
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

	@Override
	public String toString() {
		return "Courses [name=" + name + ", description=" + description + ", user=" + user + ", lessons=" + lessons
				+ ", studentCourses=" + studentCourses + ", type=" + type + ", oldPrice=" + oldPrice + ", discount="
				+ discount + ", newPrice=" + newPrice + ", image=" + image + ", shortDescription=" + shortDescription
				+ ", status=" + status + "]";
	}
	
	
}
