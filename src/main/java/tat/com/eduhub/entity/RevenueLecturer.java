package tat.com.eduhub.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_revenue_lecturer")
public class RevenueLecturer extends BaseEntity{

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_payment", referencedColumnName = "id")
	private Payment payment;
	
	@Column(name = "total_price")
	private Long totalPrice;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_lecturer")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public RevenueLecturer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
