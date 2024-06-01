package tat.com.eduhub.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Table
@Entity(name = "tb_revenue")
public class Revenue extends BaseEntity{

	@Column(name = "total_price")
	private Long totalPrice;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_payment", referencedColumnName = "id")
	private Payment payment;

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Revenue() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
