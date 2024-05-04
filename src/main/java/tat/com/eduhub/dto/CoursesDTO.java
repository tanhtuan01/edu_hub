package tat.com.eduhub.dto;

public class CoursesDTO {
	
	private Long id;

	private String name;
	
	private String description;
	
	private String type;
	
	private Long oldPrice;
	
	private int discount;
	
	private Long newPrice;
	
	private String shortDescription;
	
	private String status;
	
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CoursesDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CoursesDTO [id=" + id + ", name=" + name + ", description=" + description + ", type=" + type
				+ ", oldPrice=" + oldPrice + ", discount=" + discount + ", newPrice=" + newPrice + ", shortDescription="
				+ shortDescription + ", status=" + status + "]";
	}

	public String parseType() {
		return (this.type.equals("free")) ? "Miễn phí" : "Trả phí";
	}
	
	public String parseStatus() {
		switch (this.status) {
		case "on_ready":
			return "Sẵn sàng";
		case "in_progress":
			return "Đang dạy";
		case "not_ready":
			return "Chưa sẵn sàng";
		default:
			break;
		}
		return this.status;
	}
	
}
